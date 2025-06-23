package com.samwel.smart_meter.service;

import com.samwel.smart_meter.dto.AdminDTO;
import com.samwel.smart_meter.dto.ClientDTO;
import com.samwel.smart_meter.helperClass.ClientMethods;
import com.samwel.smart_meter.model.Admin;
import com.samwel.smart_meter.model.Client;
import com.samwel.smart_meter.repository.AdminRepository;
import com.samwel.smart_meter.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    BCryptPasswordEncoder encoder;
    private final ClientMethods clientMethods;

    public Client registerClient(ClientDTO clientDTO) {
        if (clientDTO.getFirstName() == null || clientDTO.getLastName() == null) {
            return null;
        }
        if (!clientDTO.getPassword().equals(clientDTO.getConfirmPassword())) {
            return null;
        }
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setMeterNum(clientMethods.generateUniqueMeterNum());
        client.setPassword(encoder.encode(clientDTO.getPassword()));
        return clientRepository.save(client);
    }

    public Client authenticateClient(ClientDTO clientDTO) {
        if (clientDTO.getMeterNum() == 0 || clientDTO.getPassword() == null) {
            return null;
        }
        Optional<Client> optionalClient = clientRepository.findByMeterNum(clientDTO.getMeterNum());
        if (optionalClient.isEmpty()) return null;
        Client client = optionalClient.get();
        if (encoder.matches(clientDTO.getPassword(), client.getPassword())) {
            return client;
        }
        return null;
    }

    public Admin registerAdmin(AdminDTO adminDTO) {
        if (adminDTO.getFirstName() == null|| adminDTO.getLastName() == null || adminDTO.getEmail() == null || adminDTO.getStation() == null || adminDTO.getPassword() == null || adminDTO.getConfirmPassword() == null) {
            return null;
        }
        if (!adminDTO.getConfirmPassword().equals(adminDTO.getPassword())) {
            return null;
        }
        Admin admin = new Admin();
        admin.setFirstName(adminDTO.getFirstName().trim());
        admin.setLastName(adminDTO.getLastName().trim());
        admin.setEmail(adminDTO.getEmail().trim());
        admin.setStation(adminDTO.getStation());
        admin.setPassword(encoder.encode(adminDTO.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin authenticateAdmin(AdminDTO adminDTO) {
        if (adminDTO.getEmail() == null || adminDTO.getPassword() == null) {
            return null;
        }
        System.out.println(adminDTO.getEmail());
        System.out.println(adminDTO.getPassword());
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(adminDTO.getEmail());
        if (optionalAdmin.isEmpty()) return null;
        Admin admin = optionalAdmin.get();
        if (encoder.matches(adminDTO.getPassword(), admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public boolean deleteUser(int id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(int id) {
        Optional<Client> userOptional = clientRepository.findById(id);

        if (userOptional.isPresent()) {
            return (Client) userOptional.get();
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }

    public List<Client> getRecentClients(int count) {
        return clientRepository.findAllByOrderByUserIdDesc(PageRequest.of(0, count));
    }

}
