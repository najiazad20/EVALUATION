package ma.projet.classes;


import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "employe_tache")
@NamedQuery(
        name = "EmployeTache.findRealiseesByEmploye",
        query = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :eid AND et.dateFinReelle IS NOT NULL"
)
@NamedQuery(
        name = "EmployeTache.findRealiseesByProjet",
        query = "SELECT et.tache FROM EmployeTache et WHERE et.tache.projet.id = :pid AND et.dateFinReelle IS NOT NULL"
)
@NamedQuery(
        name = "EmployeTache.findProjetsByEmploye",
        query = "SELECT DISTINCT et.tache.projet FROM EmployeTache et WHERE et.employe.id = :eid"
)
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK pK;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    @ManyToOne
    @JoinColumn(name = "employe", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "tache", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Tache tache;

    public EmployeTache() {
    }

    public EmployeTache(Date dateDebutReelle, Date dateFinReelle,
                        Employe employe, Tache tache) {
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.employe = employe;
        this.tache = tache;
        this.pK = new EmployeTachePK(employe.getId(), tache.getId());
    }

    public EmployeTachePK getpK() {
        return pK;
    }

    public void setpK(EmployeTachePK pK) {
        this.pK = pK;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }
}