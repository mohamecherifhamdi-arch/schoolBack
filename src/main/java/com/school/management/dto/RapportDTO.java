package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Eleve;
import com.school.management.entity.Rapport;
import java.time.LocalDate;
import java.util.List;

public class RapportDTO {
    private Long id;
    private String titre;
    @JsonIgnoreProperties({"payments", "notes", "parents"})
    private Eleve eleve;
    private List<String> jurys;
    private String remarque;
    private LocalDate date;
    private String statut;
    private boolean bibliotheque;

    public RapportDTO() {}

    public RapportDTO(Long id, String titre, Eleve eleve, List<String> jurys, String remarque, LocalDate date, String statut, boolean bibliotheque) {
        this.id = id; this.titre = titre; this.eleve = eleve; this.jurys = jurys;
        this.remarque = remarque; this.date = date; this.statut = statut; this.bibliotheque = bibliotheque;
    }

    public static RapportDTO fromEntity(Rapport r) {
        return new RapportDTO(r.getId(), r.getTitre(), r.getEleve(),
                r.getJurys(), r.getRemarque(), r.getDate(), r.getStatut(), r.isBibliotheque());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; }
    public List<String> getJurys() { return jurys; }
    public void setJurys(List<String> jurys) { this.jurys = jurys; }
    public String getRemarque() { return remarque; }
    public void setRemarque(String remarque) { this.remarque = remarque; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public boolean isBibliotheque() { return bibliotheque; }
    public void setBibliotheque(boolean bibliotheque) { this.bibliotheque = bibliotheque; }
}
