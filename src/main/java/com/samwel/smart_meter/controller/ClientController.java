package com.samwel.smart_meter.controller;

import com.samwel.smart_meter.dto.TransactionDTO;
import com.samwel.smart_meter.model.Client;
import com.samwel.smart_meter.model.PowerTransaction;
import com.samwel.smart_meter.service.PowerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
public class ClientController {
    private final PowerService powerService;

    @PostMapping("/client_purchase")
    public String processPurchase(@ModelAttribute TransactionDTO dto, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            return "redirect:/client_login?error=Unauthorized";
        }

        dto.setClientId(client.getUserId());
        dto.setDate(LocalDate.now());

        PowerTransaction transaction = powerService.addTransaction(dto);

        if (transaction != null) {
            return "redirect:/client_purchase?success=Power+successfully+purchased";
        } else {
            return "redirect:/client_purchase?error=Failed+to+process+purchase";
        }
    }

    @GetMapping("/client_transactions/export")
    public void exportTransactions(HttpServletResponse response, HttpSession session) throws IOException {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            response.sendRedirect("/client_login?error=Unauthorized");
            return;
        }

        List<PowerTransaction> transactions = powerService.getClientTransactions(client.getUserId());

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");

        PrintWriter writer = response.getWriter();
        writer.println("Token,Date,Amount,Units");

        for (PowerTransaction tx : transactions) {
            writer.printf("%s,%s,%.2f,%.2f\n",
                    String.valueOf(tx.getToken()),
                    String.valueOf(tx.getDate()),
                    tx.getAmount(),
                    tx.getUnitsPurchased()
            );
        }

        writer.flush();
        writer.close();
    }

    @GetMapping("/client_transactions/receipt/{token}")
    public String printReceipt(@PathVariable String token, Model model) {
        PowerTransaction tx = powerService.getTransactionByToken(token);
        if (tx == null) {
            return "redirect:/client_transactions?error=Transaction+not+found";
        }
        model.addAttribute("tx", tx);
        return "client/receipt";
    }


}
