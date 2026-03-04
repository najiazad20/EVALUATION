package ma.projet;

import ma.projet.classes.*;
import ma.projet.services.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        CategorieService cs = new CategorieService();
        ma.projet.services.ProduitService ps = new ma.projet.services.ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeProduitService ls = new LigneCommandeProduitService();

        Categorie c1 = new Categorie("EL", "Electronique");
        cs.create(c1);


        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c1);
        Produit p3 = new Produit("EE85", 200, c1);

        ps.create(p1);
        ps.create(p2);
        ps.create(p3);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateCommande = sdf.parse("14/03/2013");

        Commande cmd = new Commande(dateCommande);
        cms.create(cmd);


        cmd = cms.findAll().get(0);


        LigneCommandeProduit l1 = new LigneCommandeProduit(7, p1, cmd);
        LigneCommandeProduit l2 = new LigneCommandeProduit(14, p2, cmd);
        LigneCommandeProduit l3 = new LigneCommandeProduit(5, p3, cmd);

        ls.create(l1);
        ls.create(l2);
        ls.create(l3);

        cmd = cms.findById(cmd.getId());


        ps.afficherProduitsCommande(cmd);



        System.out.println("\nProduits dont le prix est supérieur à 100 DH :");
        List<Produit> produitsPrixSup100 = ps.findPrixSuperieur(100);
        for (Produit p : produitsPrixSup100) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }


        System.out.println("\nProduits de la catégorie Electronique :");
        List<Produit> produitsParCategorie = ps.findByCategorie(c1);
        for (Produit p : produitsParCategorie) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }


        Date dateDebut = sdf.parse("03/02/2013");
        Date dateFin = sdf.parse("31/12/2013");

        System.out.println("\nProduits commandés entre le 03/02/2013 et le 31/12/2013 :");
        List<Produit> produitsEntreDates = ps.findBetweenDates(dateDebut, dateFin);
        for (Produit p : produitsEntreDates) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }
    }
}