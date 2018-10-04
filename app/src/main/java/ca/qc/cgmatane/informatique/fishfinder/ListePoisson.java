package ca.qc.cgmatane.informatique.fishfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;

import ca.qc.cgmatane.informatique.fishfinder.donnee.BaseDeDonnee;
import ca.qc.cgmatane.informatique.fishfinder.donnee.PoissonDAO;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poissons;
import ca.qc.cgmatane.informatique.fishfinder.vue.AjouterPoisson;
import ca.qc.cgmatane.informatique.fishfinder.vue.ModifierPoisson;

public class ListePoisson extends AppCompatActivity {

    static final public int ACTIVITE_AJOUTER_POISSON = 1;
    static final public int ACTIVITE_MODIFIER_POISSON = 2;
    static final public int ACTIVITE_AJOUTER_ALARME = 3;

    protected ListView vueListePoisson;
    protected Poissons listePoisson;

    protected PoissonDAO accesseurPoisson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_poisson);

        BaseDeDonnee.getInstance(getApplicationContext());

        accesseurPoisson = PoissonDAO.getInstance();

        listePoisson = accesseurPoisson.getListePoissons();

        vueListePoisson = findViewById(R.id.vue_liste_poisson);

        Log.d("ListePoisson", "on create");

        afficherTousLesPoissons();

        //clique sur item --> modifier
        vueListePoisson.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View vue,
                                            int positionDansAdapteur,
                                            long positionItem) {
                        Log.d("ListePoisson", "onItemClick");
                        ListView vueListePoisson = (ListView) vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String, String> poisson = (HashMap<String, String>) vueListePoisson.getItemAtPosition((int) positionItem);

                        //Toast message = Toast.makeText(getApplicationContext(), "Position " + positionItem + " nom " + poisson.get("nom"), Toast.LENGTH_SHORT);

                        Log.d("ListePoisson", "onItemClick Position: " + positionItem);
                        Log.d("ListePoisson", "onItemClick Titre: " + poisson.get("nom"));

                        //message.show();

                        Intent intentionNaviguerModiferLivre = new Intent(ListePoisson.this, ModifierPoisson.class);
                        intentionNaviguerModiferLivre.putExtra("id", poisson.get("id"));
                        startActivityForResult(intentionNaviguerModiferLivre, ACTIVITE_MODIFIER_POISSON);
                    }
                }
        );

        Button actionAjouterPoisson = (Button) findViewById(R.id.action_naviguer_ajouter_poisson);

        actionAjouterPoisson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intentionNaviguerAjouterPoisson = new Intent(ListePoisson.this, AjouterPoisson.class);
                startActivityForResult(intentionNaviguerAjouterPoisson, ACTIVITE_AJOUTER_POISSON);
            }
        });


    }

    protected void onActivityResult(int activite, int resultat, Intent donnees) {
        switch (activite) {
            case ACTIVITE_MODIFIER_POISSON:
                afficherTousLesPoissons();
                break;
            case ACTIVITE_AJOUTER_POISSON:
                afficherTousLesPoissons();
                break;
            case ACTIVITE_AJOUTER_ALARME:
                afficherTousLesPoissons();
                break;
        }

    }

    private void afficherTousLesPoissons() {
        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listePoisson.recupererListePourAdaptateur(),
                android.R.layout.two_line_list_item,
                new String[]{"nom", "dateAlarme"},
                new int[]{android.R.id.text1, android.R.id.text2});

        vueListePoisson.setAdapter(adapteur);
    }

    /*private List<HashMap<String, String>> prerparerListePoisson() {
        List<HashMap<String, String>> listePoisson =
                new ArrayList<HashMap<String, String>>();
        HashMap<String, String> poisson;

        poisson = new HashMap<String, String>();
        poisson.put("nom", "Saumon");
        poisson.put("famille", "Salmonidee");
        poisson.put("id", "1");
        listePoisson.add(poisson);

        poisson = new HashMap<String, String>();
        poisson.put("nom", "Brochet");
        poisson.put("famille", "Esocidae");
        poisson.put("id", "2");
        listePoisson.add(poisson);

        poisson = new HashMap<String, String>();
        poisson.put("nom", "Silure");
        poisson.put("famille", "Siluridae");
        poisson.put("id", "3");
        listePoisson.add(poisson);

        return listePoisson;
    }*/
}
