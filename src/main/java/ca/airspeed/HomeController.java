package ca.airspeed;

import static io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.views.View;
import io.reactivex.Flowable;

@Controller
public class HomeController {

    @Secured(IS_ANONYMOUS)
    @View("home")
    @Get
    public Map<String, Object> index() {
        return new HashMap<>();
    }

}
