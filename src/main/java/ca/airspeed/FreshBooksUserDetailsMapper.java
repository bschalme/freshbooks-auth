package ca.airspeed;

import static java.util.Arrays.asList;

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

@Named("freshbooks")
@Singleton
public class FreshBooksUserDetailsMapper implements OauthUserDetailsMapper {

    @Override
    public Publisher<UserDetails> createUserDetails(TokenResponse tokenResponse) {
        return Publishers.just(new UnsupportedOperationException());
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State state) { 
        return Publishers.just(new UserDetails("apiUser", asList(tokenResponse.getScope().split(":"))));
    }
}
