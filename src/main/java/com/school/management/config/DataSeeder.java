package com.school.management.config;

import com.school.management.entity.*;
import com.school.management.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EnseignantRepository enseignantRepo;
    private final ClasseRepository classeRepo;
    private final MatiereRepository matiereRepo;
    private final SalleRepository salleRepo;
    private final PlanningRepository planningRepo;
    private final AbsenceRepository absenceRepo;
    private final NoteRepository noteRepo;
    private final ReservationRepository reservationRepo;
    private final EleveRepository eleveRepo;
    private final ParentRepository parentRepo;
    private final PaymentRepository paymentRepo;
    private final ReclamationRepository reclamationRepo;
    private final RapportRepository rapportRepo;

    public DataSeeder(EnseignantRepository enseignantRepo, ClasseRepository classeRepo,
                      MatiereRepository matiereRepo, SalleRepository salleRepo,
                      PlanningRepository planningRepo, AbsenceRepository absenceRepo,
                      NoteRepository noteRepo, ReservationRepository reservationRepo,
                      EleveRepository eleveRepo, ParentRepository parentRepo,
                      PaymentRepository paymentRepo, ReclamationRepository reclamationRepo,
                      RapportRepository rapportRepo) {
        this.enseignantRepo = enseignantRepo;
        this.classeRepo = classeRepo;
        this.matiereRepo = matiereRepo;
        this.salleRepo = salleRepo;
        this.planningRepo = planningRepo;
        this.absenceRepo = absenceRepo;
        this.noteRepo = noteRepo;
        this.reservationRepo = reservationRepo;
        this.eleveRepo = eleveRepo;
        this.parentRepo = parentRepo;
        this.paymentRepo = paymentRepo;
        this.reclamationRepo = reclamationRepo;
        this.rapportRepo = rapportRepo;
    }

    @Override
    public void run(String... args) {
        if (enseignantRepo.count() > 0) {
            System.out.println("Donn\u00e9es d\u00e9j\u00e0 pr\u00e9sentes, seed ignor\u00e9.");
            return;
        }

        System.out.println("=== Seed de la base de donn\u00e9es ===");

        Enseignant e1 = enseignantRepo.save(new Enseignant(null, "Dupont", "Jean", "jean.dupont@ecole.fr", "0612345678", new ArrayList<>(), "Actif", new ArrayList<>()));
        Enseignant e2 = enseignantRepo.save(new Enseignant(null, "Martin", "Sophie", "sophie.martin@ecole.fr", "0623456789", new ArrayList<>(), "Actif", new ArrayList<>()));
        Enseignant e3 = enseignantRepo.save(new Enseignant(null, "Bernard", "Pierre", "pierre.bernard@ecole.fr", "0634567890", new ArrayList<>(), "Actif", new ArrayList<>()));
        Enseignant e4 = enseignantRepo.save(new Enseignant(null, "Petit", "Marie", "marie.petit@ecole.fr", "0645678901", new ArrayList<>(), "Actif", new ArrayList<>()));
        Enseignant e5 = enseignantRepo.save(new Enseignant(null, "Dubois", "Laurent", "laurent.dubois@ecole.fr", "0656789012", new ArrayList<>(), "Absent", new ArrayList<>()));
        System.out.println("\u2192 5 enseignants");

        Classe c1 = classeRepo.save(new Classe(null, "3\u00e8me A", "3\u00e8me ann\u00e9e", "Sciences", 28, "2025/2026", new ArrayList<>(), new ArrayList<>()));
        Classe c2 = classeRepo.save(new Classe(null, "3\u00e8me B", "3\u00e8me ann\u00e9e", "Lettres", 25, "2025/2026", new ArrayList<>(), new ArrayList<>()));
        Classe c3 = classeRepo.save(new Classe(null, "Terminale Maths", "Terminale", "Sciences", 22, "2025/2026", new ArrayList<>(), new ArrayList<>()));
        Classe c4 = classeRepo.save(new Classe(null, "4\u00e8me A", "4\u00e8me ann\u00e9e", "Technique", 30, "2025/2026", new ArrayList<>(), new ArrayList<>()));
        System.out.println("\u2192 4 classes");

        Matiere m1 = matiereRepo.save(new Matiere(null, "MATH01", "Math\u00e9matiques", "3\u00e8me ann\u00e9e", e1, c1, 4, 8, 120, "Actif"));
        Matiere m2 = matiereRepo.save(new Matiere(null, "MATH02", "Math\u00e9matiques", "Terminale", e1, c3, 5, 10, 150, "Actif"));
        Matiere m3 = matiereRepo.save(new Matiere(null, "FR01", "Fran\u00e7ais", "3\u00e8me ann\u00e9e", e2, c1, 3, 6, 90, "Actif"));
        Matiere m4 = matiereRepo.save(new Matiere(null, "PC01", "Physique-Chimie", "Terminale", e3, c3, 5, 10, 140, "Actif"));
        Matiere m5 = matiereRepo.save(new Matiere(null, "ANG01", "Anglais", "4\u00e8me ann\u00e9e", e4, c4, 2, 4, 60, "Actif"));
        Matiere m6 = matiereRepo.save(new Matiere(null, "HG01", "Histoire-G\u00e9ographie", "3\u00e8me ann\u00e9e", e5, c1, 3, 5, 80, "Actif"));
        System.out.println("\u2192 6 mati\u00e8res");

        e1.setMatieres(List.of(m1, m2)); e2.setMatieres(List.of(m3));
        e3.setMatieres(List.of(m4)); e4.setMatieres(List.of(m5));
        e5.setMatieres(List.of(m6));
        enseignantRepo.saveAll(List.of(e1, e2, e3, e4, e5));
        c1.setMatieres(List.of(m1, m3, m6)); c3.setMatieres(List.of(m2, m4));
        c4.setMatieres(List.of(m5));
        classeRepo.saveAll(List.of(c1, c3, c4));

        Salle s1 = salleRepo.save(new Salle(null, "S101", "Salle 101", 30, "A", 1, "Disponible", List.of("Tableau", "Projecteur"), new ArrayList<>()));
        Salle s2 = salleRepo.save(new Salle(null, "S102", "Salle 102", 25, "A", 1, "Disponible", List.of("Tableau", "Projecteur"), new ArrayList<>()));
        Salle s3 = salleRepo.save(new Salle(null, "Labo1", "Laboratoire Physique", 20, "B", 0, "Disponible", List.of("Paillasses", "Microscope"), new ArrayList<>()));
        Salle s4 = salleRepo.save(new Salle(null, "S201", "Salle 201", 35, "A", 2, "Maintenance", List.of("Tableau"), new ArrayList<>()));
        System.out.println("\u2192 4 salles");

        Eleve el1 = new Eleve(null, "Durant", "Alice", "3\u00e8me A", "3\u00e8me", "0611111111", "alice@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el2 = new Eleve(null, "Martin", "Bob", "3\u00e8me A", "3\u00e8me", "0622222222", "bob@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el3 = new Eleve(null, "Petit", "Claire", "3\u00e8me A", "3\u00e8me", "0633333333", "claire@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el4 = new Eleve(null, "Bernard", "David", "3\u00e8me A", "3\u00e8me", "0644444444", "david@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el5 = new Eleve(null, "Dubois", "Emma", "3\u00e8me A", "3\u00e8me", "0655555555", "emma@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el6 = new Eleve(null, "Moreau", "Lucas", "Terminale Maths", "Terminale", "0666666666", "lucas@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el7 = new Eleve(null, "Girard", "L\u00e9a", "Terminale Maths", "Terminale", "0677777777", "lea@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el8 = new Eleve(null, "Lefevre", "Hugo", "Terminale Maths", "Terminale", "0688888888", "hugo@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el9 = new Eleve(null, "Roux", "Chlo\u00e9", "Terminale Maths", "Terminale", "0699999999", "chloe@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        Eleve el10 = new Eleve(null, "Leroy", "Nathan", "4\u00e8me A", "4\u00e8me", "0610101010", "nathan@mail.com", "Actif", new ArrayList<>(), new ArrayList<>());
        var eleves = eleveRepo.saveAll(List.of(el1, el2, el3, el4, el5, el6, el7, el8, el9, el10));
        el1 = eleves.get(0); el2 = eleves.get(1); el3 = eleves.get(2); el4 = eleves.get(3); el5 = eleves.get(4);
        el6 = eleves.get(5); el7 = eleves.get(6); el8 = eleves.get(7); el9 = eleves.get(8); el10 = eleves.get(9);
        System.out.println("\u2192 10 \u00e9l\u00e8ves");

        c1.setEleves(List.of(el1, el2, el3, el4, el5));
        c3.setEleves(List.of(el6, el7, el8, el9));
        c4.setEleves(List.of(el10));
        classeRepo.saveAll(List.of(c1, c3, c4));

        LocalDate today = LocalDate.now();
        LocalDate mon = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate tue = mon.plusDays(1);
        LocalDate wed = mon.plusDays(2);
        LocalDate thu = mon.plusDays(3);
        LocalDate fri = mon.plusDays(4);

        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "3\u00e8me A", "S101", mon, LocalTime.of(8, 0), LocalTime.of(10, 0), "Cours"));
        planningRepo.save(new Planning(null, "Sophie Martin", "Fran\u00e7ais", "3\u00e8me B", "S102", mon, LocalTime.of(8, 0), LocalTime.of(9, 30), "Cours"));
        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "Terminale Maths", "S201", mon, LocalTime.of(10, 30), LocalTime.of(12, 30), "TD"));
        planningRepo.save(new Planning(null, "Marie Petit", "Anglais", "4\u00e8me A", "S101", mon, LocalTime.of(14, 0), LocalTime.of(15, 30), "Cours"));
        planningRepo.save(new Planning(null, "Pierre Bernard", "Physique-Chimie", "Terminale Maths", "Labo1", tue, LocalTime.of(8, 0), LocalTime.of(10, 30), "TP"));
        planningRepo.save(new Planning(null, "Laurent Dubois", "Histoire-G\u00e9ographie", "3\u00e8me A", "S101", tue, LocalTime.of(9, 0), LocalTime.of(10, 30), "Cours"));
        planningRepo.save(new Planning(null, "Sophie Martin", "Fran\u00e7ais", "3\u00e8me A", "S102", tue, LocalTime.of(10, 30), LocalTime.of(12, 0), "TD"));
        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "3\u00e8me B", "S101", tue, LocalTime.of(14, 0), LocalTime.of(16, 0), "Cours"));
        planningRepo.save(new Planning(null, "Marie Petit", "Anglais", "3\u00e8me A", "S102", wed, LocalTime.of(8, 0), LocalTime.of(9, 30), "Cours"));
        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "4\u00e8me A", "S101", wed, LocalTime.of(10, 0), LocalTime.of(12, 0), "TD"));
        planningRepo.save(new Planning(null, "Pierre Bernard", "Physique-Chimie", "3\u00e8me A", "Labo1", wed, LocalTime.of(14, 0), LocalTime.of(16, 0), "TP"));
        planningRepo.save(new Planning(null, "Sophie Martin", "Fran\u00e7ais", "Terminale Maths", "S101", thu, LocalTime.of(8, 0), LocalTime.of(9, 30), "Cours"));
        planningRepo.save(new Planning(null, "Laurent Dubois", "Histoire-G\u00e9ographie", "4\u00e8me A", "S102", thu, LocalTime.of(9, 0), LocalTime.of(10, 30), "Cours"));
        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "3\u00e8me A", "S101", thu, LocalTime.of(10, 30), LocalTime.of(12, 0), "TD"));
        planningRepo.save(new Planning(null, "Pierre Bernard", "Physique-Chimie", "Terminale Maths", "Labo1", thu, LocalTime.of(14, 0), LocalTime.of(16, 30), "TP"));
        planningRepo.save(new Planning(null, "Marie Petit", "Anglais", "Terminale Maths", "S102", fri, LocalTime.of(8, 0), LocalTime.of(9, 30), "Cours"));
        planningRepo.save(new Planning(null, "Jean Dupont", "Math\u00e9matiques", "3\u00e8me B", "S101", fri, LocalTime.of(10, 0), LocalTime.of(12, 0), "Cours"));
        planningRepo.save(new Planning(null, "Sophie Martin", "Fran\u00e7ais", "4\u00e8me A", "S102", fri, LocalTime.of(14, 0), LocalTime.of(15, 30), "Cours"));
        System.out.println("\u2192 plannings cr\u00e9\u00e9s");

        absenceRepo.save(new Absence(null, e1, "3\u00e8me A", "S101", mon.minusDays(2), "1\u00e8re s\u00e9ance", true, "Rendez-vous m\u00e9dical"));
        absenceRepo.save(new Absence(null, e3, "Terminale Maths", "Labo1", wed, "Apr\u00e8s-midi", false, ""));
        absenceRepo.save(new Absence(null, e1, "3\u00e8me A", "S101", tue, "Matin", false, "Probl\u00e8me personnel"));
        absenceRepo.save(new Absence(null, e4, "4\u00e8me A", "S102", fri, "Matin", true, "Formation p\u00e9dagogique"));
        System.out.println("\u2192 4 absences");

        noteRepo.saveAll(List.of(
            new Note(null, el1, m1, c1, "Devoir", 15.0, 1.0, mon.minusDays(5), "Publi\u00e9"),
            new Note(null, el2, m1, c1, "Devoir", 8.0, 1.0, mon.minusDays(5), "Publi\u00e9"),
            new Note(null, el3, m1, c1, "Devoir", 12.0, 1.0, mon.minusDays(5), "Publi\u00e9"),
            new Note(null, el4, m1, c1, "Interrogation", 17.0, 0.5, mon.minusDays(3), "Publi\u00e9"),
            new Note(null, el5, m1, c1, "Interrogation", 10.0, 0.5, mon.minusDays(3), "Publi\u00e9"),
            new Note(null, el6, m2, c3, "Devoir", 14.0, 1.0, mon.minusDays(4), "Publi\u00e9"),
            new Note(null, el7, m2, c3, "Devoir", 16.0, 1.0, mon.minusDays(4), "Publi\u00e9"),
            new Note(null, el8, m2, c3, "Devoir", 6.0, 1.0, mon.minusDays(4), "Publi\u00e9"),
            new Note(null, el9, m2, c3, "Interrogation", 11.0, 0.5, mon.minusDays(2), "Publi\u00e9"),
            new Note(null, el1, m3, c1, "Devoir", 13.0, 1.0, mon.minusDays(6), "Publi\u00e9"),
            new Note(null, el2, m3, c1, "Devoir", 9.0, 1.0, mon.minusDays(6), "Publi\u00e9"),
            new Note(null, el5, m5, c4, "Devoir", 15.5, 1.0, mon.minusDays(5), "Publi\u00e9"),
            new Note(null, el6, m4, c3, "TP", 13.0, 1.5, mon.minusDays(3), "Publi\u00e9"),
            new Note(null, el7, m4, c3, "TP", 18.0, 1.5, mon.minusDays(3), "Publi\u00e9"),
            new Note(null, el8, m4, c3, "TP", 7.0, 1.5, mon.minusDays(3), "Publi\u00e9"),
            new Note(null, el3, m6, c1, "Devoir", 14.0, 1.0, mon.minusDays(4), "Publi\u00e9"),
            new Note(null, el4, m6, c1, "Devoir", 12.0, 1.0, mon.minusDays(4), "Publi\u00e9"),
            new Note(null, el5, m5, c4, "Interrogation", 11.0, 0.5, mon.minusDays(1), "Publi\u00e9")
        ));
        System.out.println("\u2192 notes cr\u00e9\u00e9es");

        reservationRepo.saveAll(List.of(
            new Reservation(null, e1, s1, "3\u00e8me A", mon.plusDays(7).toString(), "Matin", "Math\u00e9matiques"),
            new Reservation(null, e3, s3, "Terminale Maths", mon.plusDays(8).toString(), "Apr\u00e8s-midi", "Physique-Chimie"),
            new Reservation(null, e2, s2, "4\u00e8me A", mon.plusDays(9).toString(), "Matin", "Fran\u00e7ais")
        ));
        System.out.println("\u2192 3 r\u00e9servations");

        parentRepo.saveAll(List.of(
            new Parent(null, "Durant", "15 rue de Paris", "0611111111", "parent.durant@mail.com", List.of(el1), "Actif"),
            new Parent(null, "Martin", "20 avenue des Fleurs", "0622222222", "parent.martin@mail.com", List.of(el2), "Actif"),
            new Parent(null, "Moreau", "8 boulevard Central", "0666666666", "parent.moreau@mail.com", List.of(el6), "Actif")
        ));
        System.out.println("\u2192 3 parents");

        paymentRepo.saveAll(List.of(
            new Payment(null, el1, "Septembre", 300.0, 300.0, LocalDate.of(2025, 9, 5), "Esp\u00e8ces", "Pay\u00e9", ""),
            new Payment(null, el2, "Septembre", 300.0, 150.0, LocalDate.of(2025, 9, 10), "Virement", "Partiel", "Reste 150\u20ac"),
            new Payment(null, el6, "Octobre", 350.0, 350.0, LocalDate.of(2025, 10, 1), "Ch\u00e8que", "Pay\u00e9", "")
        ));
        System.out.println("\u2192 3 paiements");

        reclamationRepo.saveAll(List.of(
            new Reclamation(null, e3, el6, LocalDate.now().minusDays(3), "Comportement en classe", "Heure de retenue", "Trait\u00e9e"),
            new Reclamation(null, e2, el2, LocalDate.now().minusDays(1), "Devoir non rendu", "Avertissement", "En attente")
        ));
        System.out.println("\u2192 2 r\u00e9clamations");

       // rapportRepo.saveAll(List.of(
     //       new Rapport(null, "Rapport PFE", el1, List.of("Mme Martin", "M. Dupont"), "Excellent travail", LocalDate.now(), "Valid\u00e9", true),
          //  new Rapport(null, "Rapport Stage", el6, List.of("M. Bernard"), "Bon travail", LocalDate.now(), "En cours", false)
      //  ));
        System.out.println("\u2192 2 rapports");

        System.out.println("=== Seed termin\u00e9 avec succ\u00e8s ===");
    }
}
