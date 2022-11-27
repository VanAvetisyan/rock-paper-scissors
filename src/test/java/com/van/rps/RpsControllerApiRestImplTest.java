package com.van.rps;

import com.van.rps.model.UserPickRequest;
import com.van.rps.model.UserPickResponse;
import com.van.rps.support.RpsControllerRestSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.van.rps.model.PickElements.ROCK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RpsControllerApiRestImplTest {

    private static final String PICK = String.valueOf(ROCK);
    private static final String USER = "user1234";
    private static final UserPickRequest REQUEST = createMockRequest();
    private static final UserPickResponse RESPONSE = createMockResponse();

    @InjectMocks
    private RpsControllerApiRestImpl rpsControllerApiRestImpl;

    @Mock
    private RpsControllerRestSupport rpsControllerRestSupport;

    @Before
    public void setUp() {
        when(rpsControllerRestSupport.calculatePicksAndWinner(REQUEST)).thenReturn(RESPONSE);
    }

    @Test
    public void shouldCallRestWithProvidedRequestAndReturnResult() {
        // given
        // when
        UserPickResponse response = rpsControllerApiRestImpl.userPickedObjectWinnerCalculator(REQUEST).getBody();
        // then
        assertNotNull(response);
        assertEquals(RESPONSE, response);
        verify(rpsControllerRestSupport).calculatePicksAndWinner(REQUEST);
    }

    static UserPickRequest createMockRequest() {
        return UserPickRequest.builder().userPick(PICK).userId(USER).build();
    }

    static UserPickResponse createMockResponse() {
        return UserPickResponse.builder()
                .userId(USER)
                .userPick(PICK)
                .enemyPick(PICK)
                .userWins(false)
                .enemyWins(false).build();
    }
}