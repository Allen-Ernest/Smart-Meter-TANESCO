package com.samwel.smart_meter.controller;

import com.samwel.smart_meter.dto.TransactionDTO;
import com.samwel.smart_meter.model.PowerTransaction;
import com.samwel.smart_meter.service.PowerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Service
public class PowerTransactionController {

    private PowerService powerService;

    @PostMapping("/add-transaction")
    public String addTransaction(@ModelAttribute TransactionDTO transactionDTO){
        PowerTransaction transaction = powerService.addTransaction(transactionDTO);
        if (transaction == null){
            return "redirect:/error?error=Transaction+failed";
        }
        return "redirect:/dashboard";
    }
}
