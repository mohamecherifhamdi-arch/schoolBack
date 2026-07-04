package com.school.management.repository;

import com.school.management.entity.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Long> {
    List<Reservation> findBySalle_Id(Long salleId);
    List<Reservation> findByDate(String date);
    List<Reservation> findBySalle_IdAndDateAndSession(Long salleId, String date, String session);
    List<Reservation> findByEnseignant_Id(Long enseignantId);
}
