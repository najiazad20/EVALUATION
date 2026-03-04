package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "taches")

@NamedQuery(
        name = "Tache.findByProjet",
        query = "SELECT t FROM Tache t WHERE t.projet.id = :pid"
)
@NamedQueries({
        @NamedQuery(
                name = "Tache.findByPrixSup1000",
                query = "SELECT t FROM Tache t WHERE t.prix > 1000"
        ),
        @NamedQuery(
                name = "Tache.findBetweenDates",
                query = "SELECT t FROM Tache t WHERE t.dateDebut BETWEEN :d1 AND :d2"
        )
})
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private double prix;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}