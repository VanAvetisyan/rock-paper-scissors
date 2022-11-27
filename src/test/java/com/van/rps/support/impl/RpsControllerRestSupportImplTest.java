package com.van.rps.support.impl;

import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.utils.RandomPicker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.van.rps.model.PickElements.ROCK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RpsControllerRestSupportImplTest {

    private static final String USER = "user1234";

    @InjectMocks
    private RpsControllerRestSupportImpl rpsControllerRestSupportImpl;

    @Mock
    private RandomPicker randomPicker;

    @Before
    public void setUp() {
        when(randomPicker.calculateRandomComputerPick()).thenReturn(ROCK);
    }

    @Test
    public void shouldReturnFalseForBothWhenBothPicksAreTheSame() {
        winnerTest("rock", false, false);
    }

    @Test
    public void shouldReturnTrueForUserWhenUserWins() {
        winnerTest("paper", true, false);
    }

    @Test
    public void shouldReturnTrueForEnemyWhenEnemyWins() {
        winnerTest("scissors", false, true);
    }

    private void winnerTest(String userPick, Boolean userWins, Boolean enemyWins) {
        // before
        UserPickRequest rockRequest = createMockRequest(userPick);

        // when
        UserPickResponse response = rpsControllerRestSupportImpl.calculatePicksAndWinner(rockRequest);

        // then
        assertNotNull(response);
        assertEquals(ROCK, randomPicker.calculateRandomComputerPick());
        assertEquals(response.getUserWins(), userWins);
        assertEquals(response.getEnemyWins(), enemyWins);
    }

    static UserPickRequest createMockRequest(String pick) {
        return UserPickRequest.builder().userPick(pick).userId(USER).build();
    }
}