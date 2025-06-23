package com.samwel.smart_meter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Clients")
public class Client extends User{
    @Column(unique = true, nullable = false)
    private long meterNum;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PowerTransaction> transactions;
}