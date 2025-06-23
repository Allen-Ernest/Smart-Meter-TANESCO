package com.samwel.smart_meter.repository;

import com.samwel.smart_meter.model.PowerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PowerTransactionRepository extends JpaRepository<PowerTransaction, Integer> {
    boolean existsByToken(String token);
    Optional<PowerTransaction> findByToken(String token);
    List<PowerTransaction> findByClientUserId(int clientId);
    Page<PowerTransaction> findByClientUserId(int clientId, Pageable pageable);
    long countByDate(LocalDate date);

    List<PowerTransaction> findAllByOrderByIdDesc(Pageable pageable);

}
