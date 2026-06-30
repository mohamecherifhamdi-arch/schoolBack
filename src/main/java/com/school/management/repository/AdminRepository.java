package com.school.management.repository;

import com.school.management.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    List<Admin> findByStatus(String status);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByInvitationToken(String invitationToken);
}
