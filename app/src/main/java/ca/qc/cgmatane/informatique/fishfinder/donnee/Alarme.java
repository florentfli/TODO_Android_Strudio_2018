package ca.qc.cgmatane.informatique.fishfinder.donnee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;
import ca.qc.cgmatane.informatique.fishfinder.vue.AlarmeEcran;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class Alarme extends BroadcastReceiver {
    private Intent intent;
    private int id;
    PoissonDAO accesseurPoisson = PoissonDAO.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle parametres = intent.getExtras();
        String parametre_id = (String) "" + parametres.get("id");
        id = Integer.parseInt(parametre_id);

        Poisson poissonAlarme = accesseurPoisson.listerPoisson().trouverAvecId(id);
        Log.d("Alarme", "Alarme poisson :" + poissonAlarme.getNom());

        Intent intentionNaviguerModiferLivre = new Intent(context,AlarmeEcran.class);
        intentionNaviguerModiferLivre.putExtra("id",id);
        context.startActivity(intentionNaviguerModiferLivre);
    }
}
