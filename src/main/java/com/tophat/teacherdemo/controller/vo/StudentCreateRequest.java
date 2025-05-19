package com.tophat.teacherdemo.controller.vo;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCreateRequest {
    @NotEmpty(message = "username must not be null")
    @Pattern(regexp = "^[\\w\\s]+$", message = "username contains invalid characters")
    @Size(max = 50, message = "username is too long")
    private String username;

    @NotEmpty(message = "username must not be null")
    @Pattern(regexp = "^[\\w\\s.,!?()]+$", message = "displayName contains invalid characters")
    @Size(max = 100, message = "displayName is too long")
    private String displayName;
}
