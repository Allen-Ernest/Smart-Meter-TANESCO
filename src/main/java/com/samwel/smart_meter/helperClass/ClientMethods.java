package com.samwel.smart_meter.helperClass;

import com.samwel.smart_meter.repository.PowerTransactionRepository;
import com.samwel.smart_meter.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor
public class ClientMethods {
    PowerTransactionRepository transactionRepository;
    private final ClientRepository userRepository;

    public long generateUniqueMeterNum() {
        Random random = new Random();
        long meterNum;
        do {
            meterNum = 1_000_000_0000L + (long) (random.nextDouble() * 9_000_000_0000L);
        } while (userRepository.existsByMeterNum(meterNum));
        return meterNum;
    }

    public String generateUniqueToken() {
        String token;
        do {
            token = String.valueOf(1_000_000_000_000_000L + new Random().nextLong(9_000_000_000_000_000L));
        } while (transactionRepository.existsByToken(token));
        return token;
    }

}
