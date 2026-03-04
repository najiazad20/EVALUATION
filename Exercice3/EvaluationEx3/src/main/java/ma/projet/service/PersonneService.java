package ma.projet.service;



import ma.projet.beans.Personne;
import ma.projet.service.AbstractFacade;

public class PersonneService extends AbstractFacade<Personne> {

    public PersonneService() {
        super(Personne.class);
    }

}
