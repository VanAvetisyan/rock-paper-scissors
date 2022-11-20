package com.van.rps;

import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.RpsControllerRestSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpsControllerApiRestImpl {
    private static final Logger logger = LoggerFactory.getLogger(RpsControllerApiRestImpl.class);
    @Autowired
    private RpsControllerRestSupport rpsControllerRestSupport;

    @PostMapping("/api/rest/rps/userpick")
    public ResponseEntity<UserPickResponse> userPickedObjectWinnerCalculator(@RequestBody UserPickRequest request) {
        logger.info("User {} asked for response with {} pick", request.getUserName(), request.getUserPick());
        UserPickResponse userPickResponse = rpsControllerRestSupport.calculatePicksAndWinner(request);
        logger.info("Returning data");
        return new ResponseEntity<>(userPickResponse, HttpStatus.OK);
    }
}
