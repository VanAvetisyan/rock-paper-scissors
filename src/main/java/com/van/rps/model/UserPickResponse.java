package com.van.rps.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPickResponse {
    private String userName;
    private String userPick;
    private String enemyPick;
    private Boolean userWins;
    private Boolean enemyWins;
}
