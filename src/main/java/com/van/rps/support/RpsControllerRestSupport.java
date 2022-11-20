package com.van.rps.support;

import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;

public interface RpsControllerRestSupport {
    /**
     * Searches a random pick and calculates who wins
     *
     * @param userPickRequest that contains user picked object
     * @return UserPickResponse
     */
    UserPickResponse calculatePicksAndWinner(UserPickRequest userPickRequest);
}
