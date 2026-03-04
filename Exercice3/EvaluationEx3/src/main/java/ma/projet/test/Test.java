package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {

        FemmeService fs = new FemmeService();
        HommeService hs = new HommeService();
        MariageService ms = new MariageService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



        Femme f1 = new Femme("OUMNIA", "HADDAD", "0621002511", "Echemaia", sdf.parse("03/03/1983"));
        Femme f2 = new Femme("SAMIRA", "NASSIRI", "0621098122", "El Jadida", sdf.parse("17/07/1986"));
        Femme f3 = new Femme("AHLAM", "BOUKHARI", "0621871203", "El youssofia", sdf.parse("25/01/1992"));
        Femme f4 = new Femme("CHOROUK", "MEZIANE", "0621983404", "Safi", sdf.parse("09/09/1981"));
        Femme f5 = new Femme("MERYEM", "KHALDI", "062191285", "Beni Mellal", sdf.parse("14/12/1994"));
        Femme f6 = new Femme("SALWA", "AMRANI", "0621004306", "Khouribga", sdf.parse("06/06/1988"));
        Femme f7 = new Femme("NISSA", "EL IDRISSI", "0621017807", "Marrakech", sdf.parse("28/02/1990"));
        Femme f8 = new Femme("KAWTAR", "SABRI", "0621098348", "Settat", sdf.parse("11/11/1987"));
        Femme f9 = new Femme("HOUDA", "RAHMANI", "0621761909", "Nador", sdf.parse("19/05/1985"));
        Femme f10 = new Femme("ZINEB", "FARISSI", "0621000010", "Dakhla", sdf.parse("30/08/1993"));


        fs.create(f1); fs.create(f2); fs.create(f3); fs.create(f4); fs.create(f5);
        fs.create(f6); fs.create(f7); fs.create(f8); fs.create(f9); fs.create(f10);



        Homme h1 = new Homme("ISMAIL", "BENOMAR", "0712000001", "Essaouira", sdf.parse("15/01/1973"));
        Homme h2 = new Homme("AHMED", "TAHIRI", "0712000002", "El Jadida", sdf.parse("22/06/1978"));
        Homme h3 = new Homme("ANWAR", "FAROUK", "0712000003", "Ouarzazate", sdf.parse("10/10/1980"));
        Homme h4 = new Homme("YOUSSEF", "KABIR", "0712000004", "Taza", sdf.parse("04/04/1976"));
        Homme h5 = new Homme("ANAS", "CHAMI", "0712000005", "Beni Mellal", sdf.parse("29/09/1982"));
        hs.create(h1); hs.create(h2); hs.create(h3); hs.create(h4); hs.create(h5);





        ms.create(new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1));
        ms.create(new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2));
        ms.create(new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3));


        ms.create(new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4));




        System.out.println("\n===== LISTE DES FEMMES =====");
        fs.findAll().forEach(System.out::println);




        System.out.println("\n===== FEMME LA PLUS ÂGÉE =====");
        System.out.println(fs.femmePlusAgee());




        System.out.println("\n===== ÉPOUSES ENTRE 1988 ET 2001 =====");
        hs.findEpousesBetweenDates(
                h1.getId(),
                sdf.parse("01/01/1988"),
                sdf.parse("01/01/2001")
        ).forEach(System.out::println);



        System.out.println("\n===== NOMBRE D'ENFANTS DE SALIMA RAMI =====");
        System.out.println(
                fs.countEnfantsBetweenDates(
                        f1.getId(),
                        sdf.parse("01/01/1988"),
                        sdf.parse("01/01/2025")
                )
        );



        System.out.println("\n FEMMES MARIÉES AU MOINS 2 FOIS ");
        fs.femmesMarieesDeuxFois().forEach(System.out::println);



        System.out.println("\n HOMMES MARIÉS À 4 FEMMES ");
        System.out.println(
                fs.hommesAvecQuatreFemmes(
                        sdf.parse("01/01/1988"),
                        sdf.parse("01/01/2025")
                )
        );



        System.out.println("\n===== DÉTAILS DES MARIAGES DE SAFI SAID =====");
        afficherMariagesComplet(h1.getId(), ms, sdf);
    }

    private static void afficherMariagesComplet(int hommeId, MariageService ms, SimpleDateFormat sdf) {
        List<Mariage> mariages = ms.findByHomme(hommeId);

        System.out.println("Nom : SAFI SAID");

        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %-12s %-12s Date Début : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                        sdf.format(m.getDateDebut()), m.getNbrEnfant());
            }
        }

        System.out.println("\nMariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %-12s %-12s Date Début : %s    Date Fin : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                        sdf.format(m.getDateDebut()), sdf.format(m.getDateFin()), m.getNbrEnfant());
            }
        }
    }
}