package com.van.rps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpsControllerApiRestImpl {
    @GetMapping("/userpick/{pick}")
    String one(@PathVariable String pick) {
        return ("User picked " + pick);
    }
}
