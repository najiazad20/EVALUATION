package ma.projet.beans;


import javax.persistence.*;

import java.util.List;

@Entity
@NamedQuery(
        name = "Femme.findFemmesMarieesDeuxFois",
        query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
)
@NamedNativeQuery(
        name = "Femme.countEnfantsBetweenDates",
        query = "SELECT SUM(nbrEnfant) FROM mariage " +
                "WHERE femme_id = ?1 AND dateDebut BETWEEN ?2 AND ?3"
)
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone,
                 String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }

    @Override
    public String toString() {
        return "Femme : " + super.toString();
    }
}
