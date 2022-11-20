package com.van.rps;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // supplies user count
    public Supplier<Number> fetchUserCount() {
        return () -> 5;
    }

    // constructor injector for exposing metrics at Actuator /prometheus
    public UserController(MeterRegistry registry) {

        Gauge.builder("usercontroller.usercount", fetchUserCount()).
                tag("version", "v1").
                description("usercontroller descrip").
                register(registry);
    }
}