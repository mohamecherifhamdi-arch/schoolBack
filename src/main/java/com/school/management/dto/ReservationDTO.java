package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Reservation;
import com.school.management.entity.Salle;

public class ReservationDTO {
    private Long id;
    @JsonIgnoreProperties({"matieres", "absences"})
    private Enseignant enseignant;
    @JsonIgnoreProperties("reservations")
    private Salle salle;
    private String className;
    private String date;
    private String session;
    private String subject;

    public ReservationDTO() {}

    public ReservationDTO(Long id, Enseignant enseignant, Salle salle, String className, String date, String session, String subject) {
        this.id = id; this.enseignant = enseignant; this.salle = salle;
        this.className = className; this.date = date; this.session = session; this.subject = subject;
    }

    public static ReservationDTO fromEntity(Reservation r) {
        return new ReservationDTO(r.getId(), r.getEnseignant(), r.getSalle(),
                r.getClassName(), r.getDate(), r.getSession(), r.getSubject());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
    public Salle getSalle() { return salle; }
    public void setSalle(Salle salle) { this.salle = salle; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
