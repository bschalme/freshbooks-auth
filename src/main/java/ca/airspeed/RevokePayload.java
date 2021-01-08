package ca.airspeed;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Introspected
public class RevokePayload {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    private String token;
}
