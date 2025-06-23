package com.samwel.smart_meter.controller;

import com.samwel.smart_meter.model.Admin;
import com.samwel.smart_meter.model.Client;
import com.samwel.smart_meter.model.PowerTransaction;
import com.samwel.smart_meter.service.AuthService;
import com.samwel.smart_meter.service.PowerService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class NavigationController {
    @Autowired
    private PowerService powerService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/")
    public String getHomePage() {
        return "home";
    }

    @RequestMapping("/admin_login")
    public String getAdminLoginPage() {
        return "admin/login";
    }

    @RequestMapping("/admin_register")
    public String getAdminRegistrationPage(){
        return "admin/register";
    }

    @RequestMapping("/client_login")
    public String getClientLoginPage(){
        return "client/login";
    }

    @GetMapping("/client_dashboard")
    public String getClientDashboard(HttpSession session, Model model){
        Client client = (Client) session.getAttribute("client");
        if (session.getAttribute("client") == null){
            return "redirect:/client_login?error=unauthorized";
        }
        model.addAttribute("client", client);
        return "client/dashboard";
    }

    @GetMapping("/client_transactions")
    public String getClientTransactionsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session, Model model) {

        Client client = (Client) session.getAttribute("client");
        if (client == null) return "redirect:/client_login?error=Unauthorized";

        Page<PowerTransaction> transactions = powerService.getClientTransactionsPaginated(client.getUserId(), page, size);
        model.addAttribute("transactions", transactions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactions.getTotalPages());
        return "client/transactions";
    }



    @GetMapping("/client_purchase")
    public String getPurchasePage(HttpSession session, Model model){
        Client client = (Client) session.getAttribute("client");
        if (session.getAttribute("client") == null){
            return "redirect:/client_login?error=unauthorized";
        }
        model.addAttribute("client", client);
        return "client/purchase";
    }

    @PostMapping("/client_logout")
    public String clientLogout(HttpSession session, Model model){
        if (session.getAttribute("client") != null){
            session.invalidate();
            return "redirect:/client_login";
        }
        return "redirect:/client_dashboard?error=Logout+failed";
    }

    @GetMapping("/admin_dashboard")
    public String getDashboard(HttpSession session, Model model){
        Admin admin = (Admin) session.getAttribute("admin");
        if (session.getAttribute("admin") == null){
            return "redirect:/admin_login?error=Unauthorized";
        }
        model.addAttribute("admin", admin);
        model.addAttribute("totalClients", authService.getAllClients().size());
        model.addAttribute("totalTransactions", powerService.getAllTransactions().size());
        model.addAttribute("todaysTransactions", powerService.getTodaysTransactionCount());
        model.addAttribute("recentTransactions", powerService.getRecentTransactions(5));
        model.addAttribute("recentClients", authService.getRecentClients(5));
        return "admin/dashboard";
    }

    @GetMapping("/admin_clients")
    public String getClientsPage(HttpSession session, Model model){
        Admin admin = (Admin) session.getAttribute("admin");
        if (session.getAttribute("admin") == null){
            return "redirect:/admin_login?error=Unauthorized";
        }
        model.addAttribute("admin", admin);
        model.addAttribute("totalClients", authService.getAllClients().size());
        model.addAttribute("clients", authService.getAllClients());
        return "admin/clients";
    }

    @GetMapping("/admin_transactions")
    public String getTransactionsPage(HttpSession session, Model model){
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin_login?error=Unauthorized";
        }

        model.addAttribute("admin", admin);
        model.addAttribute("transactions", powerService.getAllTransactions());

        return "admin/transactions";
    }


    @PostMapping("/admin_logout")
    public  String logout(HttpSession session){
        if (session.getAttribute("admin") != null){
            session.invalidate();
            return "redirect:/admin_login";
        }
        return "redirect:/admin_dashboard?error=Logout+failed";
    }

    @GetMapping("/admin_profile")
    public String getAdminProfile(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin_login?error=Unauthorized";
        }
        model.addAttribute("admin", admin);
        return "admin/profile";
    }

    @GetMapping("/client_profile")
    public String getClientProfile(HttpSession session, Model model) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            return "redirect:/client_login?error=Unauthorized";
        }
        model.addAttribute("client", client);
        return "client/profile";
    }

}
