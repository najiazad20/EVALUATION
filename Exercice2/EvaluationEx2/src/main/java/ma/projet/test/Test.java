package ma.projet.test;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.service.EmployeService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.service.EmployeTacheService;
import ma.projet.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();


        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Employe emp1 = new Employe("malak", "zaad", "0653793456");
        Employe emp2 = new Employe("salma", "malek", "0607089708");
        session.save(emp1);
        session.save(emp2);

        Projet proj1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
        proj1.setChefProjet(emp1);
        Projet proj2 = new Projet("Site web e-commerce", sdf.parse("01/03/2013"), sdf.parse("31/08/2013"));
        proj2.setChefProjet(emp2);
        session.save(proj1);
        session.save(proj2);

        Tache t1 = new Tache("Analyse", sdf.parse("10/02/2013"), sdf.parse("20/02/2013"), 800);
        t1.setProjet(proj1);
        Tache t2 = new Tache("Conception", sdf.parse("10/03/2013"), sdf.parse("15/03/2013"), 1500);
        t2.setProjet(proj1);
        Tache t3 = new Tache("Développement", sdf.parse("10/04/2013"), sdf.parse("25/04/2013"), 2000);
        t3.setProjet(proj1);
        session.save(t1);
        session.save(t2);
        session.save(t3);

        EmployeTache et1 = new EmployeTache(t1.getDateDebut(), t1.getDateFin(), emp1, t1);
        EmployeTache et2 = new EmployeTache(t2.getDateDebut(), t2.getDateFin(), emp1, t2);
        EmployeTache et3 = new EmployeTache(t3.getDateDebut(), t3.getDateFin(), emp2, t3);
        session.save(et1);
        session.save(et2);
        session.save(et3);

        tx.commit();
        session.close();


        int employeId = emp1.getId();
        System.out.println("\n=== Tâches réalisées par l'employé " + employeId + " ===");
        List<Tache> taches = employeService.findTachesParEmploye(employeId);
        System.out.println("Num\tNom\t\tDate Début\tDate Fin");
        for (Tache t : taches) {
            System.out.printf("%d\t%s\t\t%s\t%s\n",
                    t.getId(),
                    t.getNom(),
                    t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "-",
                    t.getDateFin() != null ? sdf.format(t.getDateFin()) : "-");
        }

        System.out.println("\n=== Projets gérés par l'employé " + employeId + " ===");
        List<Projet> projets = employeService.findProjetsParEmploye(employeId);
        for (Projet p : projets) {
            System.out.printf("Projet : %d\tNom : %s\tDate début : %s\n",
                    p.getId(),
                    p.getNom(),
                    p.getDateDebut() != null ? sdf.format(p.getDateDebut()) : "-");
        }


        int projetId = proj1.getId();
        System.out.println("\n=== Tâches planifiées pour le projet " + projetId + " ===");
        List<Tache> tachesPlanifiees = projetService.findTachesPlanifiees(projetId);
        System.out.println("Num\tNom\t\tDate Début\tDate Fin");
        for (Tache t : tachesPlanifiees) {
            System.out.printf("%d\t%s\t\t%s\t%s\n",
                    t.getId(),
                    t.getNom(),
                    t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "-",
                    t.getDateFin() != null ? sdf.format(t.getDateFin()) : "-");
        }

        System.out.println("\n=== Tâches réalisées pour le projet " + projetId + " ===");
        List<Tache> tachesRealisees = projetService.findTachesRealisees(projetId);
        System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");
        for (Tache t : tachesRealisees) {
            System.out.printf("%d\t%s\t\t%s\t%s\n",
                    t.getId(),
                    t.getNom(),
                    t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "-",
                    t.getDateFin() != null ? sdf.format(t.getDateFin()) : "-");
        }


        System.out.println("\n=== Tâches dont le prix > 1000 DH ===");
        List<Tache> tachesChers = tacheService.findTachesPrixSup1000();
        System.out.println("Num\tNom\t\tPrix");
        for (Tache t : tachesChers) {
            System.out.printf("%d\t%s\t\t%.2f\n",
                    t.getId(),
                    t.getNom(),
                    t.getPrix());
        }

        System.out.println("\n=== Tâches entre 01/02/2013 et 30/04/2013 ===");
        Date d1 = sdf.parse("01/02/2013");
        Date d2 = sdf.parse("30/04/2013");
        List<Tache> tachesEntreDates = tacheService.findTachesEntreDates(d1, d2);
        System.out.println("Num\tNom\t\tDate Début\tDate Fin");
        for (Tache t : tachesEntreDates) {
            System.out.printf("%d\t%s\t\t%s\t%s\n",
                    t.getId(),
                    t.getNom(),
                    t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "-",
                    t.getDateFin() != null ? sdf.format(t.getDateFin()) : "-");
        }
    }
}