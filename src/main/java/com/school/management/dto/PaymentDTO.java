package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Eleve;
import com.school.management.entity.Payment;
import java.time.LocalDate;

public class PaymentDTO {
    private Long id;
    @JsonIgnoreProperties({"payments", "notes", "parents"})
    private Eleve eleve;
    private String mois;
    private double montantAttendu;
    private double montantPaye;
    private LocalDate datePaiement;
    private String typePaiement;
    private String statut;
    private String remarque;

    public PaymentDTO() {}

    public PaymentDTO(Long id, Eleve eleve, String mois, double montantAttendu, double montantPaye, LocalDate datePaiement, String typePaiement, String statut, String remarque) {
        this.id = id; this.eleve = eleve; this.mois = mois;
        this.montantAttendu = montantAttendu; this.montantPaye = montantPaye;
        this.datePaiement = datePaiement; this.typePaiement = typePaiement;
        this.statut = statut; this.remarque = remarque;
    }

    public static PaymentDTO fromEntity(Payment p) {
        return new PaymentDTO(p.getId(), p.getEleve(),
                p.getMois(), p.getMontantAttendu(), p.getMontantPaye(),
                p.getDatePaiement(), p.getTypePaiement(), p.getStatut(), p.getRemarque());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; }
    public String getMois() { return mois; }
    public void setMois(String mois) { this.mois = mois; }
    public double getMontantAttendu() { return montantAttendu; }
    public void setMontantAttendu(double montantAttendu) { this.montantAttendu = montantAttendu; }
    public double getMontantPaye() { return montantPaye; }
    public void setMontantPaye(double montantPaye) { this.montantPaye = montantPaye; }
    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }
    public String getTypePaiement() { return typePaiement; }
    public void setTypePaiement(String typePaiement) { this.typePaiement = typePaiement; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getRemarque() { return remarque; }
    public void setRemarque(String remarque) { this.remarque = remarque; }
}
