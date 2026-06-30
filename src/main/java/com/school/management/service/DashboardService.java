package com.school.management.service;

import com.school.management.entity.*;
import com.school.management.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EleveRepository eleveRepo;
    private final EnseignantRepository enseignantRepo;
    private final ClasseRepository classeRepo;
    private final ParentRepository parentRepo;
    private final MatiereRepository matiereRepo;
    private final AbsenceRepository absenceRepo;
    private final PaymentRepository paymentRepo;
    private final PlanningRepository planningRepo;
    private final SalleRepository salleRepo;

    public DashboardService(EleveRepository eleveRepo, EnseignantRepository enseignantRepo,
                            ClasseRepository classeRepo, ParentRepository parentRepo,
                            MatiereRepository matiereRepo, AbsenceRepository absenceRepo,
                            PaymentRepository paymentRepo, PlanningRepository planningRepo,
                            SalleRepository salleRepo) {
        this.eleveRepo = eleveRepo;
        this.enseignantRepo = enseignantRepo;
        this.classeRepo = classeRepo;
        this.parentRepo = parentRepo;
        this.matiereRepo = matiereRepo;
        this.absenceRepo = absenceRepo;
        this.paymentRepo = paymentRepo;
        this.planningRepo = planningRepo;
        this.salleRepo = salleRepo;
    }

    public Map<String, Object> getStats() {
        long students = eleveRepo.count();
        long teachers = enseignantRepo.count();
        long classes = classeRepo.count();
        long parents = parentRepo.count();
        long subjects = matiereRepo.count();
        long absences = absenceRepo.count();

        List<Map<String, Object>> recentAbsencesList = absenceRepo.findAll().stream()
                .limit(3)
                .map(a -> {
                    //String ens = a.getEnseignant();
                   // String className = (ens != null ? ens : "N/A");
                    String badge = a.isJustifie() ? "Justifié" : "Non justifié";
                    String badgeClass = a.isJustifie() ? "text-success" : "text-danger";
                    String icon = a.isJustifie() ? "ti ti-arrow-up-right" : "ti ti-arrow-down";
                    String iconClass = a.isJustifie() ? "text-success" : "text-danger";
                    String tag = a.isJustifie() ? "bg-light-success" : "bg-light-danger";
                    Map<String, Object> item = new HashMap<>();
                    item.put("className", "className");
                    item.put("details", a.getMotif() != null ? a.getMotif() : "Absence");
                    item.put("badge", badge);
                    item.put("badgeClass", badgeClass);
                    item.put("icon", icon);
                    item.put("iconClass", iconClass);
                    item.put("tag", tag);
                    return item;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> upcomingEventsList = planningRepo.findAll().stream()
                .filter(p -> !p.getDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Planning::getDate))
                .limit(3)
                .map(p -> {
                    Map<String, Object> evt = new HashMap<>();
                    evt.put("date", p.getDate().toString());
                    evt.put("title", p.getMatiere() != null ? p.getMatiere() : "Cours");
                    evt.put("description", (p.getClasse() != null ? p.getClasse() : "") + " - " + p.getHeureDebut() + " à " + p.getHeureFin());
                    return evt;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> performanceHighlightsList = new ArrayList<>();

        Map<String, Object> hl1 = new HashMap<>();
        hl1.put("title", "Total Students");
        hl1.put("detail", String.valueOf(students));
        hl1.put("value", String.valueOf(students));
        performanceHighlightsList.add(hl1);

        Map<String, Object> hl2 = new HashMap<>();
        hl2.put("title", "Active Teachers");
        hl2.put("detail", String.valueOf(teachers));
        hl2.put("value", String.valueOf(teachers));
        performanceHighlightsList.add(hl2);

        Map<String, Object> hl3 = new HashMap<>();
        hl3.put("title", "Attendance Rate");
        hl3.put("detail", "Overall");
        hl3.put("value", (students > 0 ? Math.round((1 - (double) absences / (students * 30)) * 100) : 0) + "%");
        performanceHighlightsList.add(hl3);

        Map<String, Object> result = new HashMap<>();
        result.put("totalStudents", students);
        result.put("totalTeachers", teachers);
        result.put("totalClasses", classes);
        result.put("totalParents", parents);
        result.put("totalSubjects", subjects);
        result.put("totalAbsences", absences);
        result.put("attendanceRate", students > 0 ? Math.round((1 - (double) absences / (students * 30)) * 100) : 0);
        result.put("recentAbsences", recentAbsencesList);
        result.put("upcomingEvents", upcomingEventsList);
        result.put("performanceHighlights", performanceHighlightsList);
        return result;
    }
}
