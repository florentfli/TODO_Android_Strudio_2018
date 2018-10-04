package ca.qc.cgmatane.informatique.fishfinder.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ca.qc.cgmatane.informatique.fishfinder.R;
import ca.qc.cgmatane.informatique.fishfinder.donnee.PoissonDAO;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;

public class AjouterPoisson extends AppCompatActivity {

    protected PoissonDAO accesseurPoisson;

    private EditText champNom;
    private EditText champFamille;
    private CheckBox dejaPecher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_poisson);

        accesseurPoisson = PoissonDAO.getInstance();

        champNom = findViewById(R.id.vue_ajouter_poisson_champ_nom);
        champFamille = findViewById(R.id.vue_ajouter_poisson_champ_famille);
        dejaPecher = findViewById(R.id.checkBox_ajout_dejaPecher);

        Button actionModifierPoisson = (Button) findViewById(R.id.action_enregistrer_poisson);

        actionModifierPoisson.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        ajouterPoisson();
                    }
                }
        );
    }

    private void ajouterPoisson() {
        Poisson poissonAAjouter;
        if (!dejaPecher.isChecked())
            poissonAAjouter = new Poisson(champNom.getText().toString(), champFamille.getText().toString(), 0, "ARLARME : -");
        else
            poissonAAjouter = new Poisson(champNom.getText().toString(), champFamille.getText().toString(), 1, "ARLARME : -");
        accesseurPoisson.ajouterPoisson(poissonAAjouter);
        naviguerRetourListePoisson();
    }


    public void naviguerRetourListePoisson() {
        accesseurPoisson.listerPoisson();
        this.finish();
    }
}
