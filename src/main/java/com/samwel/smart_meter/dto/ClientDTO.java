package com.samwel.smart_meter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private String firstName;
    private String lastName;
    private long meterNum;
    private String password;
    private String confirmPassword;
}
