package com.samwel.smart_meter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Admins")
public class Admin extends User{
    @Column(nullable = false)
    private String station;

    @Column(nullable = false)
    private String email;
}
