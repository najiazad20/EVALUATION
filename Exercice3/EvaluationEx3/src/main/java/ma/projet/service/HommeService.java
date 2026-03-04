package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService extends ma.projet.service.AbstractFacade<Homme> {

    public HommeService() {
        super(Homme.class);
    }


    public List<Femme> findEpousesBetweenDates(int hommeId, Date d1, Date d2) {
        Session session = null;
        Transaction tx = null;
        List<Femme> femmes = new ArrayList<>();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Femme> query = session.createQuery(
                    "SELECT m.femme FROM Mariage m " +
                            "WHERE m.homme.id = :id " +
                            "AND m.dateDebut BETWEEN :d1 AND :d2",
                    Femme.class
            );
            query.setParameter("id", hommeId);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);

            femmes = query.getResultList();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return femmes;
    }


    public void afficherMariages(int hommeId) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Homme h = findById(hommeId);
            if (h == null) {
                System.out.println("Homme introuvable !");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Nom : " + h.getNom() + " " + h.getPrenom());
            System.out.println("Mariages en cours :");

            int i = 1;
            for (Mariage m : h.getMariages()) {
                if (m.getDateFin() == null) {
                    System.out.println(i++ + ". Femme : "
                            + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + sdf.format(m.getDateDebut())
                            + "    Nbr Enfants : " + m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages terminés :");
            i = 1;
            for (Mariage m : h.getMariages()) {
                if (m.getDateFin() != null) {
                    System.out.println(i++ + ". Femme : "
                            + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + sdf.format(m.getDateDebut())
                            + "\n   Date Fin : " + sdf.format(m.getDateFin())
                            + "    Nbr Enfants : " + m.getNbrEnfant());
                }
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}