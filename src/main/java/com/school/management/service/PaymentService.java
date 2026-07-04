package com.school.management.service;

import com.school.management.dto.PaymentDTO;
import com.school.management.entity.Eleve;
import com.school.management.entity.Payment;
import com.school.management.repository.EleveRepository;
import com.school.management.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private  Long paymentId  = 1L;
    private final EleveRepository eleveRepository;
    public PaymentService(PaymentRepository repository, EleveRepository eleveRepository) {
        this.repository = repository;
        this.eleveRepository = eleveRepository;
    }

    public List<PaymentDTO> findAll() { return repository.findAll().stream().map(PaymentDTO::fromEntity).toList(); }
    public PaymentDTO findById(Long id) { return PaymentDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"))); }
    public PaymentDTO create(PaymentDTO dto) {
        Payment payment = new Payment();
        paymentId = paymentId + 1 ;
        payment.setId(paymentId);
        payment.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        payment.setMois(dto.getMois());
        payment.setMontantAttendu(dto.getMontantAttendu());
        payment.setMontantPaye(dto.getMontantPaye());
        payment.setDatePaiement(dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDate.now());
        payment.setTypePaiement(dto.getTypePaiement());
        payment.setStatut(dto.getStatut());
        payment.setRemarque(dto.getRemarque());
        Payment saved = repository.save(payment);
        if (saved.getEleve() != null) syncElevePayments(saved.getEleve().getId());
        return PaymentDTO.fromEntity(saved);
    }
    public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        Long oldEleveId = existing.getEleve() != null ? existing.getEleve().getId() : null;
        existing.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        existing.setMois(dto.getMois());
        existing.setMontantAttendu(dto.getMontantAttendu());
        existing.setMontantPaye(dto.getMontantPaye());
        existing.setDatePaiement(dto.getDatePaiement());
        existing.setTypePaiement(dto.getTypePaiement());
        existing.setStatut(dto.getStatut());
        existing.setRemarque(dto.getRemarque());
        Payment saved = repository.save(existing);
        if (saved.getEleve() != null) syncElevePayments(saved.getEleve().getId());
        if (oldEleveId != null && (saved.getEleve() == null || !oldEleveId.equals(saved.getEleve().getId()))) {
            syncElevePayments(oldEleveId);
        }
        return PaymentDTO.fromEntity(saved);
    }
    public void delete(Long id) {
        Payment payment = repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        Long eleveId = payment.getEleve() != null ? payment.getEleve().getId() : null;
        repository.deleteById(id);
        if (eleveId != null) syncElevePayments(eleveId);
    }
    public List<PaymentDTO> findByEleve(Long eleveId) { return repository.findByEleve_Id(eleveId).stream().map(PaymentDTO::fromEntity).toList(); }

    private void syncElevePayments(Long eleveId) {
        Optional<Eleve> opt = eleveRepository.findById(eleveId);
        if (opt.isPresent()) {
            Eleve e = opt.get();
            e.setPayments(repository.findByEleve_Id(eleveId));
            eleveRepository.save(e);
        }
    }
}
