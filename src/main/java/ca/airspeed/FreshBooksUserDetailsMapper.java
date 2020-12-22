package ca.airspeed;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.reactivestreams.Publisher;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthUserDetailsMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;

@Named("freshbooks")
@Singleton
@Slf4j
public class FreshBooksUserDetailsMapper implements OauthUserDetailsMapper {

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
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken());
        attributes.put(REFRESH_TOKEN_KEY, tokenResponse.getRefreshToken());
        UserDetails userDetails = new UserDetails("apiUser", asList(tokenResponse.getScope().split(":")), attributes);
        return Publishers.just(userDetails);
    }
}
