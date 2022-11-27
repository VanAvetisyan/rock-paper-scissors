package com.van.rps.support.impl;

import com.van.rps.model.PickElements;
import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.RpsControllerRestSupport;
import com.van.rps.support.utils.RandomPicker;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpsControllerRestSupportImpl implements RpsControllerRestSupport {

    @Autowired
    RandomPicker randomPicker;

    @Override
    public UserPickResponse calculatePicksAndWinner(UserPickRequest userPickRequest) {
        PickElements userPickedElement = PickElements.valueOf(userPickRequest.getUserPick().toUpperCase());
        PickElements enemyPickedElement = randomPicker.calculateRandomComputerPick();

        int winner = calculateWinner(userPickedElement, enemyPickedElement);

        UserPickResponse userPickResponse = UserPickResponse.builder()
                .userPick(userPickedElement.getValue())
                .enemyPick(enemyPickedElement.getValue())
                .userId(userPickRequest.getUserId())
                .userWins(winner==1)
                .enemyWins(winner==2).build();
        addMetrics(userPickResponse);
        return userPickResponse;
    }

    private int calculateWinner(PickElements userPick, PickElements randomPick) {
        if(userPick.equals(randomPick)) { return 0; }
        if ((userPick.equals(PickElements.ROCK) && randomPick.equals(PickElements.SCISSORS)) ||
                (userPick.equals(PickElements.PAPER) && randomPick.equals(PickElements.ROCK)) ||
                (userPick.equals(PickElements.SCISSORS) && randomPick.equals(PickElements.PAPER))) {
            return 1;
        }
        return 2;
    }

    private void addMetrics(UserPickResponse userPickResponse) {
        Counter userPickCounter = Metrics.counter("RPS.User.Pick.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserId()),
                        Tag.of("pick", userPickResponse.getUserPick())));
        Counter enemyPickCounter = Metrics.counter("RPS.Enemy.Pick.Counter",
                Tags.of(Tag.of("pick", userPickResponse.getEnemyPick())));
        Counter userWinsCounter = Metrics.counter("RPS.User.Wins.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserId())));
        Counter userLosesCounter = Metrics.counter("RPS.User.Loses.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserId())));
        Counter userTiesCounter = Metrics.counter("RPS.User.Ties.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserId())));

        userPickCounter.increment();
        enemyPickCounter.increment();

        if(Boolean.TRUE.equals(userPickResponse.getUserWins())) { userWinsCounter.increment(); }
        else if (Boolean.TRUE.equals(userPickResponse.getEnemyWins())) { userLosesCounter.increment(); }
        else { userTiesCounter.increment(); }
    }
}
