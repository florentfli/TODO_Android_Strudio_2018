package ca.qc.cgmatane.informatique.fishfinder.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.qc.cgmatane.informatique.fishfinder.ListePoisson;
import ca.qc.cgmatane.informatique.fishfinder.R;
import ca.qc.cgmatane.informatique.fishfinder.donnee.PoissonDAO;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;

import static ca.qc.cgmatane.informatique.fishfinder.ListePoisson.ACTIVITE_AJOUTER_ALARME;

public class AlarmeEcran extends AppCompatActivity {

    private Poisson poissonAlarme;
    private CheckBox checkBoxAlarme;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_alarme_ecran);

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id = (String) "" + parametres.get("id");
        id = Integer.parseInt(parametre_id);

        final PoissonDAO accesseurPoisson = PoissonDAO.getInstance();

        poissonAlarme = accesseurPoisson.listerPoisson().trouverAvecId(id);

        TextView texteAlarme = (TextView) findViewById(R.id.texte_alarme);
        texteAlarme.setText(texteAlarme.getText() + poissonAlarme.getNom());

        checkBoxAlarme = (CheckBox) findViewById(R.id.checkBox_alarme_ecran_deja_pecher);
        checkBoxAlarme.setText(poissonAlarme.getNom() + checkBoxAlarme.getText());

        Button actionAlarmeOk = (Button) findViewById(R.id.button_alarme);

        actionAlarmeOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //mettre a jour le poisson dans la base de donné alarmePasse
                if (checkBoxAlarme.isChecked()) {
                    poissonAlarme.setAlarmePasse(1);
                } else {
                    poissonAlarme.setAlarmePasse(0);
                }

                poissonAlarme.setDateAlarme("ALARME : -");

                accesseurPoisson.modifierPoisson(poissonAlarme);
                accesseurPoisson.listerPoisson();
                Intent intentionNaviguerListePoisson = new Intent(AlarmeEcran.this, ListePoisson.class);
                startActivityForResult(intentionNaviguerListePoisson, ACTIVITE_AJOUTER_ALARME);
            }
        });

        Button boutonReplanifier = (Button) findViewById(R.id.action_replanifier);
        boutonReplanifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentionNaviguerAjouterAlarme = new Intent(AlarmeEcran.this, AjouterAlarme.class);
                intentionNaviguerAjouterAlarme.putExtra("id",id);
                intentionNaviguerAjouterAlarme.putExtra("premiereArrive",0);
                startActivity(intentionNaviguerAjouterAlarme);
            }
        });
    }
}
