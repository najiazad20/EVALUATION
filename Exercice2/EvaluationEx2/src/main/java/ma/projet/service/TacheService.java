package ma.projet.service;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.Date;
import java.util.List;

public class TacheService extends ma.projet.service.AbstractFacade<Tache> {

    public TacheService() {
        super(Tache.class);
    }


    public List<Tache> findTachesPrixSup1000() {
        Session session = null;
        Transaction tx = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            taches = session.createNamedQuery("Tache.findByPrixSup1000", Tache.class)
                    .list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return taches;
    }


    public List<Tache> findTachesEntreDates(Date d1, Date d2) {
        Session session = null;
        Transaction tx = null;
        List<Tache> taches = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            taches = session.createNamedQuery("Tache.findBetweenDates", Tache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return taches;
    }
}


