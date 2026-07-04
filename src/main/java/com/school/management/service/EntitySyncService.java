package com.school.management.service;

import com.school.management.entity.*;
import com.school.management.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EntitySyncService {

    private final AbsenceRepository absenceRepo;
    private final MatiereRepository matiereRepo;
    private final ReclamationRepository reclamationRepo;
    private final ReservationRepository reservationRepo;
    private final NoteRepository noteRepo;
    private final PaymentRepository paymentRepo;
    private final RapportRepository rapportRepo;
    private final ParentRepository parentRepo;
    private final ClasseRepository classeRepo;
    private final EnseignantRepository enseignantRepo;
    private final SalleRepository salleRepo;
    private  final PlanningRepository PlanningRepo;

    public EntitySyncService(AbsenceRepository absenceRepo, MatiereRepository matiereRepo,
                             ReclamationRepository reclamationRepo, ReservationRepository reservationRepo,
                             NoteRepository noteRepo, PaymentRepository paymentRepo,
                             RapportRepository rapportRepo, ParentRepository parentRepo,
                             ClasseRepository classeRepo, EnseignantRepository enseignantRepo,
                             SalleRepository salleRepo, PlanningRepository PlanningRepo) {
        this.absenceRepo = absenceRepo;
        this.matiereRepo = matiereRepo;
        this.reclamationRepo = reclamationRepo;
        this.reservationRepo = reservationRepo;
        this.noteRepo = noteRepo;
        this.paymentRepo = paymentRepo;
        this.rapportRepo = rapportRepo;
        this.parentRepo = parentRepo;
        this.classeRepo = classeRepo;
        this.enseignantRepo = enseignantRepo;
        this.salleRepo = salleRepo;
        this.PlanningRepo = PlanningRepo;
    }

    public void syncDeleteMatiere(Long matiereId)
    {
        this.enseignantRepo.findAll().stream().filter(enseignant -> enseignant.getMatieres()
                .contains(matiereId)).forEach(enseignant -> {enseignant.getMatieres().remove(matiereId);});
        this.enseignantRepo.saveAll(enseignantRepo.findAll());
    }

    public void syncDeleteEleve(Long eleveId)
    {
        this.noteRepo.findAll().stream().filter(note -> note.getEleve().getId().equals(eleveId)).findFirst().ifPresent(
                note -> {noteRepo.deleteById(note.getId());}
                );
         List<Payment> res  = this.paymentRepo.findByEleve_Id(eleveId);
        if(!res.isEmpty())
        {
            res.forEach(payment -> payment.setEleve(null));
            this.paymentRepo.saveAll(res);
        }
        List<Parent> parents = this.parentRepo.findByEleves_Id(eleveId);
        if(!parents.isEmpty()) {
            this.parentRepo.deleteAll(parents);
        }

        List<Rapport> rapports = this.rapportRepo.findByEleve_Id(eleveId);
        if(!rapports.isEmpty()) {
            this.rapportRepo.deleteAll(rapports);
        }
        List<Reclamation> reclamations = this.reclamationRepo.findByEleve_Id(eleveId);
        if(!reclamations.isEmpty()) {
            this.reclamationRepo.deleteAll(reclamations);
        }

    }

    public void syncEnseignant(Enseignant updated) {
        Long id = updated.getId();
        absenceRepo.findByEnseignant_Id(id).forEach(a -> { a.setEnseignant(updated); absenceRepo.save(a); });
        matiereRepo.findByEnseignant_Id(id).forEach(m -> { m.setEnseignant(updated); matiereRepo.save(m); });
        reclamationRepo.findByEnseignant_Id(id).forEach(r -> { r.setEnseignant(updated); reclamationRepo.save(r); });
        reservationRepo.findByEnseignant_Id(id).forEach(r -> { r.setEnseignant(updated); reservationRepo.save(r); });

    }
    public void syncDeleteEnseignant(Long id) {
        List<Reservation> reservations = reservationRepo.findByEnseignant_Id(id);
        if(!reservations.isEmpty()) {
            reservationRepo.deleteAll(reservations);
        }
        List<Absence> absences = absenceRepo.findByEnseignant_Id(id);
        if(!absences.isEmpty()) {
            absenceRepo.deleteAll(absences);
        }
    }

    public void syncEleve(Eleve updated) {
        Long id = updated.getId();
        noteRepo.findByEleve_Id(id).forEach(n -> { n.setEleve(updated); noteRepo.save(n); });
        paymentRepo.findByEleve_Id(id).forEach(p -> { p.setEleve(updated); paymentRepo.save(p); });
        rapportRepo.findByEleve_Id(id).forEach(r -> { r.setEleve(updated); rapportRepo.save(r); });
        reclamationRepo.findByEleve_Id(id).forEach(r -> { r.setEleve(updated); reclamationRepo.save(r); });
        parentRepo.findByEleves_Id(id).forEach(p -> {
            List<Eleve> eleves = p.getEleves();
            if (eleves != null) {
                for (int i = 0; i < eleves.size(); i++) {
                    if (eleves.get(i).getId().equals(id)) eleves.set(i, updated);
                }
                p.setEleves(eleves);
                parentRepo.save(p);
            }
        });
        classeRepo.findByEleves_Id(id).forEach(c -> {
            List<Eleve> eleves = c.getEleves();
            if (eleves != null) {
                for (int i = 0; i < eleves.size(); i++) {
                    if (eleves.get(i).getId().equals(id)) eleves.set(i, updated);
                }
                c.setEleves(eleves);
                classeRepo.save(c);
            }
        });
    }

    public void syncMatiere(Matiere updated) {
        Long id = updated.getId();
        noteRepo.findByMatiere_Id(id).forEach(n -> { n.setMatiere(updated); noteRepo.save(n); });
        classeRepo.findByMatieres_Id(id).forEach(c -> {
            List<Matiere> matieres = c.getMatieres();
            if (matieres != null) {
                for (int i = 0; i < matieres.size(); i++) {
                    if (matieres.get(i).getId().equals(id)) matieres.set(i, updated);
                }
                c.setMatieres(matieres);
                classeRepo.save(c);
            }
        });
        enseignantRepo.findByMatieres_Id(id).forEach(e -> {
            List<Matiere> matieres = e.getMatieres();
            if (matieres != null) {
                for (int i = 0; i < matieres.size(); i++) {
                    if (matieres.get(i).getId().equals(id)) matieres.set(i, updated);
                }
                e.setMatieres(matieres);
                enseignantRepo.save(e);
            }
        });
    }

    public void syncClasse(Classe updated) {
        Long id = updated.getId();
        noteRepo.findByClasse_Id(id).forEach(n -> { n.setClasse(updated); noteRepo.save(n); });
        matiereRepo.findByClasse_Id(id).forEach(m -> { m.setClasse(updated); matiereRepo.save(m); });
    }

    public void syncSalle(Salle updated) {
        Long id = updated.getId();
        reservationRepo.findBySalle_Id(id).forEach(r -> { r.setSalle(updated); reservationRepo.save(r); });
    }
}
