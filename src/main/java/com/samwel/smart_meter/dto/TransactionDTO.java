package com.samwel.smart_meter.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDTO {
    private double amount;
    private int clientId;
    private LocalDate date;
    private double unitsPurchased;
}
