package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractFacade<Femme> {

    public FemmeService() {
        super(Femme.class);
    }
    // ✔ Named Query : femmes mariées au moins deux fois
    public List<Femme> femmesMarieesDeuxFois() {
        Session session = null;
        Transaction tx = null;
        List<Femme> femmes = new ArrayList<>();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            femmes = session.createNamedQuery("Femme.findFemmesMarieesDeuxFois", Femme.class)
                    .getResultList();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return femmes;
    }

    // ✔ Criteria API : nombre d'hommes mariés à 4 femmes entre deux dates
    public long hommesAvecQuatreFemmes(Date d1, Date d2) {
        Session session = null;
        Transaction tx = null;
        Long result = 0L;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> root = cq.from(Mariage.class);

            cq.select(cb.countDistinct(root.get("homme")))
                    .where(cb.between(root.get("dateDebut"), d1, d2))
                    .groupBy(root.get("homme"))
                    .having(cb.equal(cb.countDistinct(root.get("femme")), 4));

            result = session.createQuery(cq).getSingleResult();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return result != null ? result : 0L;
    }
    public Femme femmePlusAgee() {
        Session session = null;
        Transaction tx = null;
        Femme femme = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            femme = session.createQuery(
                            "FROM Femme f ORDER BY f.dateNaissance ASC",
                            Femme.class)
                    .setMaxResults(1)
                    .getSingleResult(); // getSingleResult à la place de uniqueResult()

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return femme;
    }
    // ✔ Native Named Query : nombre d'enfants d'une femme entre deux dates
    public long countEnfantsBetweenDates(int femmeId, Date d1, Date d2) {
        Session session = null;
        Transaction tx = null;
        long result = 0L; // type primitif long

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Object queryResult = session.getNamedQuery("Femme.countEnfantsBetweenDates")
                    .setParameter(1, femmeId)
                    .setParameter(2, d1)
                    .setParameter(3, d2)
                    .uniqueResult();

            // Vérification et conversion BigDecimal -> long
            if (queryResult != null) {
                if (queryResult instanceof BigDecimal) {
                    result = ((BigDecimal) queryResult).longValue();
                } else if (queryResult instanceof Number) {
                    result = ((Number) queryResult).longValue();
                }
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return result;
    }
}
