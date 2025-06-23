package com.samwel.smart_meter.service;

import com.samwel.smart_meter.dto.TransactionDTO;
import com.samwel.smart_meter.helperClass.ClientMethods;
import com.samwel.smart_meter.model.Client;
import com.samwel.smart_meter.model.PowerTransaction;
import com.samwel.smart_meter.repository.PowerTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PowerService {
    private PowerTransactionRepository transactionRepository;
    private ClientMethods methods;

    @Autowired
    private AuthService authService;

    public PowerTransaction addTransaction(TransactionDTO dto) {
        if (dto.getClientId() == 0 || dto.getAmount() <= 0 || dto.getDate() == null) {
            return null;
        }

        Client client = authService.getClientById(dto.getClientId());

        if (client != null) {
            final double UNIT_RATE = 295.0;
            double units = dto.getAmount() / UNIT_RATE;
            units = Math.round(units * 100.0) / 100.0;

            PowerTransaction transaction = new PowerTransaction();
            transaction.setAmount(dto.getAmount());
            transaction.setDate(dto.getDate());
            transaction.setClient(client);
            transaction.setToken(methods.generateUniqueToken());
            transaction.setUnitsPurchased(units);

            return transactionRepository.save(transaction);
        }

        return null;
    }


    public List<PowerTransaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public List<PowerTransaction> getClientTransactions(int id){
        return transactionRepository.findByClientUserId(id);
    }

    public PowerTransaction getTransactionById(int id){
        Optional<PowerTransaction> optionalPowerTransaction = transactionRepository.findById(id);
        return optionalPowerTransaction.orElse(null);
    }

    public long getTodaysTransactionCount() {
        LocalDate today = LocalDate.now();
        return transactionRepository.countByDate(today);
    }

    public List<PowerTransaction> getRecentTransactions(int count) {
        return transactionRepository.findAllByOrderByIdDesc(PageRequest.of(0, count));
    }

    public Page<PowerTransaction> getClientTransactionsPaginated(int clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return transactionRepository.findByClientUserId(clientId, pageable);
    }

    public PowerTransaction getTransactionByToken(String token) {
        Optional<PowerTransaction> txOptional = transactionRepository.findByToken(token);
        if (txOptional.isEmpty()) {
            throw new RuntimeException("Transaction with token " + token + " not found");
        }
        return txOptional.get();
    }

}
