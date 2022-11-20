package com.van.rps;

import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.RpsControllerRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

import static java.util.Objects.isNull;

@RestController
public class RpsControllerApiRestImpl {
    @Autowired
    private RpsControllerRestSupport rpsControllerRestSupport;

    @PostMapping("/api/rest/rps/userpick")
    public ResponseEntity<UserPickResponse> userPickedObjectWinnerCalculator(@RequestBody UserPickRequest request) throws ServerException {
        UserPickResponse userPickResponse = rpsControllerRestSupport.calculatePicksAndWinner(request);
        if (isNull(request)) {
            throw new ServerException("Error when returning data");
        } else {
            return new ResponseEntity<>(userPickResponse, HttpStatus.OK);
        }
    }
}
