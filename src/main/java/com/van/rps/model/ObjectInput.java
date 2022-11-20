package com.van.rps.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectInput {
    private String origin;
    private String destination;
}
