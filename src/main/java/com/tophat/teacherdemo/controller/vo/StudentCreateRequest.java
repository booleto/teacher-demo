package com.tophat.teacherdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCreateRequest {
    private String username;
    private String displayName;
}
