package com.school.management.service;

import com.school.management.entity.Admin;
import com.school.management.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository repository;
    public AdminService(AdminRepository repository) { this.repository = repository; }

    public List<Admin> findAll() { return repository.findAll(); }

    public Admin findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public Admin update(String id, Admin admin) {
        Admin existing = findById(id);
        System.out.println("Rolllllllllllllllllllllle"+admin.getRole());
        existing.setRole(admin.getRole());
        existing.setStatus(admin.getStatus());
        return repository.save(existing);
    }

    public void delete(String id) { repository.deleteById(id); }

    public List<Admin> findByStatus(String status) { return repository.findByStatus(status); }
}
