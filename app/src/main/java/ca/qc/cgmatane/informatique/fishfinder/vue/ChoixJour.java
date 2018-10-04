package ca.qc.cgmatane.informatique.fishfinder.vue;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import ca.qc.cgmatane.informatique.fishfinder.R;

public class ChoixJour extends AppCompatActivity {
    private Calendar date = Calendar.getInstance();

    private int jour = date.get(Calendar.DAY_OF_MONTH);
    private int mois= date.get(Calendar.MONTH);
    private int annee=date.get(Calendar.YEAR);
    private int id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_choix_jour);

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id = (String) "" + parametres.get("id");
        id = Integer.parseInt(parametre_id);

        DatePicker calendrierChoixJour = (DatePicker) findViewById(R.id.simpleDatePicker);
        calendrierChoixJour.setMinDate(date.getTimeInMillis());
        calendrierChoixJour.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Log.d("DateLog", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                jour = dayOfMonth;
                mois = month;
                annee = year;
            }
        });

        Button enregistrerJour = (Button) findViewById(R.id.action_enregistrer_jour);
        enregistrerJour.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View arg0) {
                Intent intentionNaviguerEcranAlarme = new Intent(ChoixJour.this, AjouterAlarme.class);
                intentionNaviguerEcranAlarme.putExtra("jour", jour);
                intentionNaviguerEcranAlarme.putExtra("mois", mois);
                intentionNaviguerEcranAlarme.putExtra("annee", annee);
                intentionNaviguerEcranAlarme.putExtra("premiereArrive", 1);
                intentionNaviguerEcranAlarme.putExtra("id", id);
                startActivity(intentionNaviguerEcranAlarme);
                naviguer();
            }
        });
    }
    public void naviguer(){
        this.finish();
    }
}
