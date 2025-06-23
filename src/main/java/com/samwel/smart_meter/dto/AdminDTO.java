package com.samwel.smart_meter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {
    private String firstName;
    private String lastName;
    private String station;
    private String email;
    private String password;
    private String confirmPassword;
}
