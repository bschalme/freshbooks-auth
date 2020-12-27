package ca.airspeed;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

@Header(name="User-Agent", value="Micronaut")
@Client("https://api.freshbooks.com")
public interface FreshBooksApiClient {

    @Get("/auth/api/v1/users/me")
    Flowable<FreshBooksUser> getUser(@Header("Authorization") String authorization);

    @Post("/auth/oauth/revoke")
    HttpResponse<String> revokeToken(@Body RevokePayload body);
}
