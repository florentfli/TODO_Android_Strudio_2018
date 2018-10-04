package ca.qc.cgmatane.informatique.fishfinder.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ca.qc.cgmatane.informatique.fishfinder.ListePoisson;
import ca.qc.cgmatane.informatique.fishfinder.R;
import ca.qc.cgmatane.informatique.fishfinder.donnee.PoissonDAO;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;

public class ModifierPoisson extends AppCompatActivity {

    protected Poisson poissonAModifier;
    protected PoissonDAO accesseurPoisson;


    EditText champNom;
    EditText champFamille;
    CheckBox champDejaPecher;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_poisson);

        accesseurPoisson = PoissonDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id = (String) parametres.get("id");
        id = Integer.parseInt(parametre_id);
        System.out.println(id);

        poissonAModifier = accesseurPoisson.getListePoissons().trouverAvecId(id);
        System.out.println("JE MODIFIE CE POISSON : " + poissonAModifier.getNom());

        champNom = (EditText) findViewById(R.id.vue_modifier_poisson_champ_nom);
        champFamille = (EditText) findViewById(R.id.vue_modifier_poisson_champ_famille);
        champDejaPecher = (CheckBox) findViewById(R.id.checkBox_dejaPecher);

        champNom.setText(poissonAModifier.getNom());
        champFamille.setText(poissonAModifier.getFamille());
        if (poissonAModifier.getAlarmePasse() == 1) champDejaPecher.setChecked(true);
        else champDejaPecher.setChecked(false);

        Button actionModifierPoisson = (Button) findViewById(R.id.action_modifier_poisson);

        actionModifierPoisson.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        modifierPoisson();
                    }
                }
        );

        Button actionNaviguerAjouterAlarme = (Button) findViewById(R.id.action_naviguer_ajouter_alarme);

        actionNaviguerAjouterAlarme.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intentionNaviguerAjouterAlarme = new Intent(ModifierPoisson.this, AjouterAlarme.class);
                        intentionNaviguerAjouterAlarme.putExtra("id",id);
                        intentionNaviguerAjouterAlarme.putExtra("premiereArrive",0);
                        startActivity(intentionNaviguerAjouterAlarme);
                    }
                }
        );

    }

    private void modifierPoisson() {
        poissonAModifier.setNom(champNom.getText().toString());
        poissonAModifier.setFamille(champFamille.getText().toString());
        if (champDejaPecher.isChecked()) {
            poissonAModifier.setAlarmePasse(1);
        } else {
            poissonAModifier.setAlarmePasse(0);
        }

        accesseurPoisson.modifierPoisson(poissonAModifier);
        accesseurPoisson.listerPoisson();

        naviguerRetourListePoisson();
    }

    public void naviguerRetourListePoisson() {
        accesseurPoisson.listerPoisson();
        this.finish();
    }
}
