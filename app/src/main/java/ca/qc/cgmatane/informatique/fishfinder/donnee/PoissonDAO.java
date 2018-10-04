package ca.qc.cgmatane.informatique.fishfinder.donnee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;
import ca.qc.cgmatane.informatique.fishfinder.modele.Poissons;

public class PoissonDAO {
    private static PoissonDAO instance = null;
    private Poissons listePoissons;
    private BaseDeDonnee accesseurBaseDeDonnee;

    public static PoissonDAO getInstance(){
        if (instance == null){
            instance = new PoissonDAO();
        }
        return instance;
    }

    public PoissonDAO() {
        this.accesseurBaseDeDonnee = BaseDeDonnee.getInstance();
        listePoissons = new Poissons();
        listePoissons = listerPoisson();
    }

    public Poissons listerPoisson() {
        String LISTER_POISSONS = "SELECT * FROM poisson WHERE alarmePasse = 0";
        Cursor curseur = accesseurBaseDeDonnee.getReadableDatabase().rawQuery(LISTER_POISSONS, null);
        this.listePoissons.clear();
        Poisson poisson;

        int indexId = curseur.getColumnIndex("id");
        int indexNom = curseur.getColumnIndex("nom");
        int indexFamille = curseur.getColumnIndex("famille");
        int indexDateAlarme = curseur.getColumnIndex("dateAlarme");
        int indexAlarmePasse = curseur.getColumnIndex("alarmePasse");

        for (curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()) {
            int id = curseur.getInt(indexId);
            int alarmePasse = curseur.getInt(indexAlarmePasse);
            String nom = curseur.getString(indexNom);
            String dateAlarme = curseur.getString(indexDateAlarme);
            String famille = curseur.getString(indexFamille);
            poisson = new Poisson(nom, famille, id,alarmePasse,dateAlarme);
            this.listePoissons.add(poisson);
        }
        return listePoissons;
    }

    public void ajouterPoisson(Poisson poisson) {
        //listePoissons.add(poisson);
        SQLiteDatabase db = accesseurBaseDeDonnee.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id", id);
        values.put("nom", poisson.getNom());
        values.put("famille", poisson.getFamille());
        values.put("alarmePasse", poisson.getAlarmePasse());
        values.put("dateAlarme", poisson.getDateAlarme());
        // Inserting Row
        db.insert("poisson", null, values);
    }

    public void modifierPoisson(Poisson poisson) {
        System.out.println("j'update dans BDD le poisson : "+poisson.getNom());

        for (Poisson poissonTest : this.listerPoisson()) {
            if (poissonTest.getId() == poisson.getId()) {
                poissonTest.setNom(poisson.getNom());
                poissonTest.setFamille(poisson.getFamille());
                poissonTest.setAlarmePasse(poisson.getAlarmePasse());

                SQLiteDatabase db = accesseurBaseDeDonnee.getWritableDatabase();

                ContentValues contentValues= new ContentValues();
                contentValues.put("nom",poisson.getNom());
                contentValues.put("famille",poisson.getFamille());
                contentValues.put("dateAlarme",poisson.getDateAlarme());
                contentValues.put("alarmePasse",poisson.getAlarmePasse());
                db.update("poisson", contentValues,"id = "+ poisson.getId(),null);
                return;
            }
        }
    }


    /*public void simulerListePoisson(){
        listePoissons.clear();

        listePoissons.add(new Poisson("Saumon","Salmonidées", 1));
        listePoissons.add(new Poisson("Silure","Siluridés", 2));
        listePoissons.add(new Poisson("Brochet","Esox Lucius", 3));
        listePoissons.add(new Poisson("Perche","Percide", 4));
        listePoissons.add(new Poisson("Redfish","snapper", 5));
    }*/

    /*public void preparerListePoisson(){
        simulerListePoisson();
    }*/

    public Poissons getListePoissons() {
        return listePoissons;
    }
}
