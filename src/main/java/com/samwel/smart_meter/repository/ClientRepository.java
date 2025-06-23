package com.samwel.smart_meter.repository;

import com.samwel.smart_meter.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByMeterNum(int meterNum);
    boolean existsByMeterNum(long meterNum);
    Optional<Client> findByMeterNum(long meterNum);
    List<Client> findAllByOrderByUserIdDesc(Pageable pageable);
}
