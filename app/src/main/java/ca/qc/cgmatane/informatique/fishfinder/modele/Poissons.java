package ca.qc.cgmatane.informatique.fishfinder.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Poissons extends ArrayList<Poisson> {
    public Poisson trouverAvecId(int id){
        for (Poisson poissonTrouver: this){
            if (poissonTrouver.getId() == id){
                //System.out.println("JE TROUVE "+poissonTrouver.getNom()+" AVEC CE ID :"+id);
                return poissonTrouver;
            }
        }
        return null;
    }

    /***
     * parcourir toute la liste de poisson et convertir chaque poisson en HashMap<String, String>
     * @return
     */
    public List<HashMap<String, String>> recupererListePourAdaptateur() {
        List<HashMap<String, String>> listPoissonAdapteur = new ArrayList<HashMap<String, String>>();
        for (Poisson poisson: this){
            listPoissonAdapteur.add(poisson.obtenirPoissonPourAdapteur());
        }
        return listPoissonAdapteur;
    }
}
