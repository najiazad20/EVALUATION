package ma.projet.service;


import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;

public class EmployeService extends ma.projet.service.AbstractFacade<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    // Liste des tâches réalisées par un employé (dateFinReelle remplie)
    public List<Tache> findTachesParEmploye(int employeId) {
        Session session = null;
        Transaction tx = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            taches = session.getNamedQuery("EmployeTache.findRealiseesByEmploye")
                    .setParameter("eid", employeId)
                    .list(); // retourne List<Tache>

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return taches;
    }

    public List<Projet> findProjetsParEmploye(int employeId) {
        Session session = null;
        Transaction tx = null;
        List<Projet> projets = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            projets = session.getNamedQuery("EmployeTache.findProjetsByEmploye")
                    .setParameter("eid", employeId)
                    .list(); // retourne List<Projet>

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return projets;
    }
}


