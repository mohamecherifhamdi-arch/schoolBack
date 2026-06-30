package com.school.management.repository;

import com.school.management.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, Long> {
    List<Payment> findByEleve_Id(Long eleveId);
    List<Payment> findByStatut(String statut);
}
