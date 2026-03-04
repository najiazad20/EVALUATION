package ma.projet.services;

import ma.projet.classes.*;
import ma.projet.services.AbstractFacade;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractFacade<Produit> {

    public ProduitService() {
        super(Produit.class);
    }


    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = session
                .createNamedQuery("Produit.findByCategory", Produit.class)
                .setParameter("categorie", categorie)
                .getResultList();
        session.close();
        return list;
    }


    public List<Produit> findBetweenDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = session
                .createNamedQuery("Produit.findBetweenDates", Produit.class)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .getResultList();
        session.close();
        return list;
    }

    public void afficherProduitsCommande(Commande commande) {

        System.out.println("Commande : " + commande.getId()
                + "     Date : " + commande.getDate());

        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");

        for (LigneCommandeProduit l : commande.getLignes()) {
            System.out.println(
                    l.getProduit().getReference() + "     " +
                            l.getProduit().getPrix() + " DH     " +
                            l.getQuantite()
            );
        }
    }



    public List<Produit> findPrixSuperieur(float prix) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits = session
                .createNamedQuery("Produit.findByPrix", Produit.class)
                .setParameter("prix", prix)
                .getResultList();

        session.close();
        return produits;
    }
}