package com.van.rps;

import com.van.rps.model.ObjectInput;
import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;

import static java.util.Objects.isNull;

@RestController
public class RpsControllerApiRestImpl {
    private final Counter counter = Metrics.counter("UI.Counter");

    @PostMapping("/api/rest/rps/userpick")
    public ResponseEntity<UserPickResponse> two(@RequestBody UserPickRequest request) throws ServerException {
        counter.increment();
        UserPickResponse userPickResponse = UserPickResponse.builder().build();
        userPickResponse.setUserPick("rock");
        userPickResponse.setEnemyPick("scissors");
        userPickResponse.setUserName(request.getUserName());
        userPickResponse.setUserWins(false);
        userPickResponse.setEnemyWins(true);
        if (isNull(request)) {
            throw new ServerException("Error when returning data");
        } else {
            return new ResponseEntity<>(userPickResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/api/rest/leadtimes/v1/search")
    public ResponseEntity<String> one(@RequestParam(value = "origin") String origin) {
        counter.increment();
        return ResponseEntity.ok(origin);
    }

    @PostMapping("/api/rest/leadtimes/v1/search")
    public ResponseEntity<String> two(@RequestBody ObjectInput request) throws ServerException {
        counter.increment();
        // return ("User picked " + request.getOrigin());
        if (isNull(request)) {
            throw new ServerException("Error when returning data");
        } else {
            return new ResponseEntity<>("user", HttpStatus.OK);
        }
    }
}
