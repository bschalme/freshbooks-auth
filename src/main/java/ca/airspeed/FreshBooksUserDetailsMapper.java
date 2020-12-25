package ca.airspeed;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.reactivestreams.Publisher;

import com.fasterxml.jackson.databind.JsonNode;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthUserDetailsMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Named("freshbooks")
@Singleton
@Slf4j
@RequiredArgsConstructor
public class FreshBooksUserDetailsMapper implements OauthUserDetailsMapper {

    private final FreshBooksApiClient apiClient;

    @Override
    public Publisher<UserDetails> createUserDetails(TokenResponse tokenResponse) {
        return Publishers.just(new UnsupportedOperationException());
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse,
            @Nullable State state) {
        log.debug("Access token is '{}'.", tokenResponse.getAccessToken());
        log.debug("Refresh token is '{}'.", tokenResponse.getRefreshToken());
        log.info("Access token expires in {}s.", tokenResponse.getExpiresIn());
        return apiClient.getUser(format("Bearer %s", tokenResponse.getAccessToken()))
                .map(user -> {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken());
                    attributes.put(REFRESH_TOKEN_KEY, tokenResponse.getRefreshToken());
                    JsonNode responseNode = user.getResponse();
                    String email = responseNode.path("email").asText();
                    attributes.put("email", email);
                    attributes.put("firstName", responseNode.path("first_name").asText());
                    attributes.put("lastName", responseNode.path("last_name").asText());
                    return new UserDetails(email, asList(tokenResponse.getScope().split(":")), attributes);
                });
    }
}
