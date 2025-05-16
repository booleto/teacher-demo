package com.tophat.teacherdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCreateRequest {
    @NotNull(message = "username must not be null")
    @Max(value = 50, message = "username is too long")
    private String username;

    @NotNull(message = "username must not be null")
    @Max(value = 100, message = "displayName is too long")
    private String displayName;
}
