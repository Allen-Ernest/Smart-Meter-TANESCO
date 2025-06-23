package com.samwel.smart_meter.controller;

import com.samwel.smart_meter.dto.AdminDTO;
import com.samwel.smart_meter.dto.ClientDTO;
import com.samwel.smart_meter.model.Admin;
import com.samwel.smart_meter.model.Client;
import com.samwel.smart_meter.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/client_login")
    public String login(@ModelAttribute ClientDTO clientDTO, HttpSession session){
        Client client = authService.authenticateClient(clientDTO);
        if (client == null){
            return "redirect:/client_login?error=Invalid+Credentials";
        }
        session.setAttribute("client", client);
        return "redirect:/client_dashboard";
    }

    @PostMapping("/client_register")
    public String registerClient(@ModelAttribute ClientDTO clientDTO){
        Client client = authService.registerClient(clientDTO);
        if (client == null){
            return "redirect:/admin_clients?error=Failed+to+register+client";
        }
        return "redirect:/admin_clients?success=Registered+successfully";
    }

    @PostMapping("/admin_login")
    public String adminLogin(@ModelAttribute AdminDTO adminDTO, HttpSession session){
        Admin admin = authService.authenticateAdmin(adminDTO);
        if (admin == null){
            return "redirect:/admin_login?error=Invalid+Credentials";
        }
        session.setAttribute("admin", admin);
        return "redirect:/admin_dashboard";
    }

    @PostMapping("/admin_register")
    public String registerAdmin(@ModelAttribute AdminDTO adminDTO, HttpSession session){
        Admin admin = authService.registerAdmin(adminDTO);
        if (admin == null){
            return "redirect:/admin_register?error=Failed+to+register+administrator";
        }
        return "redirect:/admin_login";
    }
}
