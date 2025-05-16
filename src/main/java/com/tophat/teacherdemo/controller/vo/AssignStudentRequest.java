package com.tophat.teacherdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignStudentRequest {
    @NotNull(message = "studentIds must not be null")
    private List<ObjectId> studentIds;
}
