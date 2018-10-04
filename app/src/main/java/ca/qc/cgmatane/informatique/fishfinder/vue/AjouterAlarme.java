package ca.qc.cgmatane.informatique.fishfinder.vue;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.TestLooperManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import ca.qc.cgmatane.informatique.fishfinder.ListePoisson;
import ca.qc.cgmatane.informatique.fishfinder.R;
import ca.qc.cgmatane.informatique.fishfinder.donnee.Alarme;
import ca.qc.cgmatane.informatique.fishfinder.donnee.PoissonDAO;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;

import static ca.qc.cgmatane.informatique.fishfinder.ListePoisson.ACTIVITE_AJOUTER_ALARME;

public class AjouterAlarme extends AppCompatActivity {

    protected PoissonDAO accesseurPoisson;
    private Poisson poissonAlarme;
    private TimePicker horloge;
    private Calendar calendar = Calendar.getInstance();
    private Calendar compareCalendar = (Calendar) calendar.clone();

    private int id;
    private int heure;
    private int minutes;
    private int jour;
    private int mois;
    private int annee;
    private int extraPremiereArrive;

    private String ALARME_TEXTE_JOUR;
    private String ALARME_TEXTE_HEURE;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_alarme);

        accesseurPoisson = PoissonDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        String parametre_id = (String) "" + parametres.get("id");
        String parametre_arrive = (String) "" + parametres.get("premiereArrive");
        extraPremiereArrive = Integer.parseInt(parametre_arrive);
        id = Integer.parseInt(parametre_id);

        if (extraPremiereArrive == 1) {
            jour = (int) parametres.get("jour");
            mois = (int) parametres.get("mois");
            annee = (int) parametres.get("annee");
        } else {
            jour = calendar.get(Calendar.DAY_OF_MONTH);
            mois = calendar.get(Calendar.MONTH);
            annee = calendar.get(Calendar.YEAR);
        }
        heure = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);
        ALARME_TEXTE_JOUR = jour + "/" + (mois+1) + "/" + annee;

        System.out.println("DATE : " + jour + "/" + (mois+1) + "/" + annee);

        horloge = (TimePicker) findViewById(R.id.timePicker);
        horloge.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                heure = hourOfDay;
                minutes = minute;
            }
        });


        TextView textJour = (TextView) findViewById(R.id.text_jour);
        textJour.setText(ALARME_TEXTE_JOUR);


        poissonAlarme = accesseurPoisson.listerPoisson().trouverAvecId(id);

        TextView texteAlarmeAjouter = (TextView) findViewById(R.id.texte_ajout_alarme);
        String textBouton = texteAlarmeAjouter.getText() + " (" + poissonAlarme.getNom() + ") ";
        texteAlarmeAjouter.setText(textBouton);

        Button choixJour = (Button) findViewById(R.id.action_changer_jour);
        choixJour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intentionNaviguerChoixJour = new Intent(AjouterAlarme.this, ChoixJour.class);
                intentionNaviguerChoixJour.putExtra("id", id);
                startActivity(intentionNaviguerChoixJour);
                naviguer();
            }
        });

        Button enregistrerAlarme = (Button) findViewById(R.id.action_enregistrer_alarme);

        enregistrerAlarme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (heureEstValide()) {
                    ajoutAlarme();

                    ALARME_TEXTE_HEURE = heure + ":" + minutes;
                    poissonAlarme.setDateAlarme("ALARME : " + ALARME_TEXTE_JOUR + " - " + ALARME_TEXTE_HEURE);
                    accesseurPoisson.modifierPoisson(poissonAlarme);
                    accesseurPoisson.listerPoisson();

                    Intent intentionNaviguerListePoisson = new Intent(AjouterAlarme.this, ListePoisson.class);
                    startActivityForResult(intentionNaviguerListePoisson, ACTIVITE_AJOUTER_ALARME);
                    naviguer();
                }else{

                }
            }
        });
    }

    private boolean heureEstValide(){
        compareCalendar.set(annee,mois,jour,heure,minutes,0);
        System.out.println(compareCalendar);
        if (Calendar.getInstance().compareTo(compareCalendar) < 0) {
            return true;
        }else{
            Toast.makeText(this, "Veuillez rentrer une date dans le future", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void naviguer(){
        this.finish();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ajoutAlarme() {
        calendar.set(annee, mois, jour, heure, minutes, 0);

        setAlarm(calendar.getTimeInMillis());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(long time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intentAjoutAlarme = new Intent(this, Alarme.class);
        intentAjoutAlarme.putExtra("id", id);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intentAjoutAlarme, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pi);

        Toast.makeText(this, "Alarme enclenché", Toast.LENGTH_LONG).show();
    }

}
