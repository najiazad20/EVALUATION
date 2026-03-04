package ma.projet.service;


import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.transaction.Transaction;
import java.util.List;

public class EmployeTacheService extends ma.projet.service.AbstractFacade<EmployeTache> {

    public EmployeTacheService() {
        super(EmployeTache.class);
    }

    public List<EmployeTache> findRealiseesByProjet(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<EmployeTache> list =
                session.getNamedQuery("EmployeTache.findRealiseesByProjet")
                        .setParameter("pid", projetId)
                        .list();

        session.close();
        return list;
    }
}
