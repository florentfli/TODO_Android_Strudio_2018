package ca.qc.cgmatane.informatique.fishfinder.donnee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import ca.qc.cgmatane.informatique.fishfinder.modele.Poisson;

public class BaseDeDonnee extends SQLiteOpenHelper {

    private static BaseDeDonnee instance = null;

    public static BaseDeDonnee getInstance(Context contexte) {
        if (null == instance) instance = new BaseDeDonnee(contexte);
        return instance;
    }

    public static BaseDeDonnee getInstance() {
        return instance;
    }

    private BaseDeDonnee(Context contexte) {
        super(contexte, "fishfinder", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table poisson(id INTEGER PRIMARY KEY, nom TEXT, famille TEXT, alarmePasse INTEGER, dateAlarme TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        String DETRUIRE_TABLE = "drop table poisson";
        db.execSQL(DETRUIRE_TABLE);
        String CREER_TABLE = "create table poisson(id INTEGER PRIMARY KEY, nom TEXT, famille TEXT, alarmePasse INTEGER, dateAlarme TEXT)";
        db.execSQL(CREER_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String DELETE = "delete from poisson where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into poisson(id, nom, famille, alarmePasse, dateAlarme) VALUES('1', 'Saumon', 'Salmonidée','0','ALARME : -')";
        String INSERT_2 = "insert into poisson(id, nom, famille, alarmePasse, dateAlarme) VALUES('2','Silure', 'Siluridé','0','ALARME : -')";
        String INSERT_3 = "insert into poisson(id, nom, famille, alarmePasse, dateAlarme) VALUES('4','Brochet', 'Esox Lucius','0','ALARME : -')";
        String INSERT_4 = "insert into poisson(id, nom, famille, alarmePasse, dateAlarme) VALUES('5','Perche', 'Percidé','0','ALARME : -')";
        String INSERT_5 = "insert into poisson(id, nom, famille, alarmePasse, dateAlarme) VALUES('6','Carpe', 'Cyprinidée','1','ALARME : -')";

        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
        db.execSQL(INSERT_4);
        db.execSQL(INSERT_5);
    }
}