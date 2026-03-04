package ma.projet.service;


import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService extends ma.projet.service.AbstractFacade<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    public List<Tache> findTachesPlanifiees(int projetId) {
        Session session = null;
        Transaction tx = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            taches = session.getNamedQuery("Tache.findByProjet")
                    .setParameter("pid", projetId)
                    .list(); // retourne List<Tache>

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return taches;
    }

    public List<Tache> findTachesRealisees(int projetId) {
        Session session = null;
        Transaction tx = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            taches = session.getNamedQuery("EmployeTache.findRealiseesByProjet")
                    .setParameter("pid", projetId)
                    .list(); // retourne List<Tache>

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return taches;
    }
}



