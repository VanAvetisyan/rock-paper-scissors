package com.van.rps.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPickRequest {
    private String userId;
    private String userPick;
}
