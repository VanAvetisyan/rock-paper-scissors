package com.van.rps.support.impl;

import com.van.rps.model.PickElements;
import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.RpsControllerRestSupport;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RpsControllerRestSupportImpl implements RpsControllerRestSupport {

    private static final Random RANDOM = new Random();

    @Override
    public UserPickResponse calculatePicksAndWinner(UserPickRequest userPickRequest) {
        PickElements userPickedElement = PickElements.valueOf(userPickRequest.getUserPick().toUpperCase());
        PickElements enemyPickedElement = calculateRandomComputerPick();

        int winner = calculateWinner(userPickedElement, enemyPickedElement);

        UserPickResponse userPickResponse = UserPickResponse.builder()
                .userPick(userPickedElement.getValue())
                .enemyPick(enemyPickedElement.getValue())
                .userName(userPickRequest.getUserName())
                .userWins(winner==1)
                .enemyWins(winner==2).build();
        addMetrics(userPickResponse);
        return userPickResponse;
    }

    private PickElements calculateRandomComputerPick() {
        int randomIndex = RANDOM.nextInt(3);
        return PickElements.values()[randomIndex];
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
                Tags.of(Tag.of("user", userPickResponse.getUserName()),
                        Tag.of("pick", userPickResponse.getUserPick())));
        Counter enemyPickCounter = Metrics.counter("RPS.Enemy.Pick.Counter",
                Tags.of(Tag.of("pick", userPickResponse.getEnemyPick())));
        Counter userWinsCounter = Metrics.counter("RPS.User.Wins.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserName())));
        Counter userLosesCounter = Metrics.counter("RPS.User.Loses.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserName())));
        Counter userTiesCounter = Metrics.counter("RPS.User.Ties.Counter",
                Tags.of(Tag.of("user", userPickResponse.getUserName())));

        userPickCounter.increment();
        enemyPickCounter.increment();

        if(Boolean.TRUE.equals(userPickResponse.getUserWins())) { userWinsCounter.increment(); }
        else if (Boolean.TRUE.equals(userPickResponse.getEnemyWins())) { userLosesCounter.increment(); }
        else { userTiesCounter.increment(); }
    }
}
