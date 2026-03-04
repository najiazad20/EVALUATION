package ma.projet.classes;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "produits")

@NamedQueries({


        @NamedQuery(
                name = "Produit.findByCategory",
                query = "from Produit p where p.categorie = :categorie"
        ),


        @NamedQuery(
                name = "Produit.findBetweenDates",
                query = "select distinct l.produit from LigneCommandeProduit l " +
                        "where l.commande.date between :d1 and :d2"
        )
})

@NamedQuery(
        name = "Produit.findByPrix",
        query = "from Produit p where p.prix > :prix"
)

public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;

    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.EAGER)
    private List<LigneCommandeProduit> lignes;

    public Produit() {
    }

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }
}

