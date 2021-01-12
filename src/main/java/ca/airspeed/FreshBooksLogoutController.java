package ca.airspeed;

import static io.micronaut.security.oauth2.endpoint.token.response.OauthUserDetailsMapper.ACCESS_TOKEN_KEY;
import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.handlers.LogoutHandler;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.oauth2.configuration.OauthConfiguration;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FreshBooksLogoutController {

    private final OauthClientConfiguration clientConfiguration;
    private final FreshBooksApiClient freshBooksApiClient;
    private final LogoutHandler logoutHandler;

    @Get("/oauth/logout")
    @Secured(IS_AUTHENTICATED)
    public Single<HttpResponse<?>> logout(HttpRequest<?> request,
                                          Authentication auth) throws URISyntaxException {
        Map<String, Object> attributes = auth.getAttributes();
        log.debug("Access Token is '{}'.", attributes.get(ACCESS_TOKEN_KEY));
        log.debug("Client ID is '{}'.", clientConfiguration.getClientId());
        log.debug("Client Secret is '{}'.", clientConfiguration.getClientSecret());
        log.debug("Revoking the FreshBooks access token.");
        RevokePayload body = RevokePayload.builder()
                .clientId(clientConfiguration.getClientId())
                .clientSecret(clientConfiguration.getClientSecret())
                .token((String) attributes.get(ACCESS_TOKEN_KEY))
                .build();
        return freshBooksApiClient.revokeToken(body)
                .map(httpResponse -> {
                    log.debug("Response {} - {}.", httpResponse.getStatus().getCode(), httpResponse.getStatus().getReason());
                    return logoutHandler.logout(request);
                });
    }
}
