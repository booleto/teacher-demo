package com.tophat.teacherdemo.controller.vo;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class ProblemAccuracy {
    private ObjectId id;
    private Float accuracy;
}