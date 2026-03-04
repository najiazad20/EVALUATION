package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {

    @EmbeddedId
    private CommandeProduitPK pK;

    private int quantite;



    @ManyToOne
    @JoinColumn(name = "produit", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Commande commande;

    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(int quantite,
                                Produit produit, Commande commande) {
        this.quantite = quantite;

        this.produit = produit;
        this.commande = commande;


        this.pK = new CommandeProduitPK(
                commande.getId(),
                produit.getId()
        );
    }

    public CommandeProduitPK getpK() {
        return pK;
    }

    public void setpK(CommandeProduitPK pK) {
        this.pK = pK;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}