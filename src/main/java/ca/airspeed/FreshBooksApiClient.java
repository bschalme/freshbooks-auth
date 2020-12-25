package ca.airspeed;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;

@Header(name="User-Agent", value="Micronaut")
@Client("https://api.freshbooks.com/auth/api/v1")
public interface FreshBooksApiClient {

    @Get("/users/me")
    Flowable<FreshBooksUser> getUser(@Header("Authorization") String authorization);
}
