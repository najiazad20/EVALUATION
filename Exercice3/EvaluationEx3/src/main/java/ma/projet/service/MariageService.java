package ma.projet.service;


import ma.projet.beans.Mariage;
import ma.projet.service.AbstractFacade;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.transaction.Transaction;
import java.util.Collections;
import java.util.List;

public class MariageService extends AbstractFacade<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }
    public List<Mariage> findByHomme(int hommeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL simple, pas besoin de transaction pour SELECT
            return session.createQuery(
                            "FROM Mariage m WHERE m.homme.id = :idHomme",
                            Mariage.class
                    )
                    .setParameter("idHomme", hommeId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // retourne liste vide en cas d'erreur
        }
    }
}
