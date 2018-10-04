package ca.qc.cgmatane.informatique.fishfinder.modele;

import java.util.HashMap;

public class Poisson {
    protected String nom;
    protected String famille;
    protected String dateAlarme;
    protected int id;
    protected int alarmePasse;

    public Poisson(String nom, String famille, int alarmePasse, String dateAlarme) {
        this.nom = nom;
        this.famille = famille;
        this.alarmePasse = alarmePasse;
        this.dateAlarme = dateAlarme;
    }

    public Poisson(String nom, String famille, int id, int alarmePasse, String dateAlarme) {
        this.nom = nom;
        this.famille = famille;
        this.id = id;
        this.dateAlarme = dateAlarme;
        this.alarmePasse = alarmePasse;
    }

    public HashMap<String, String> obtenirPoissonPourAdapteur() {
        HashMap<String, String> poissonAdapteur = new HashMap<>();
        poissonAdapteur.put("nom", this.nom);
        poissonAdapteur.put("dateAlarme", this.dateAlarme);
        poissonAdapteur.put("famille", this.famille);
        poissonAdapteur.put("id", "" + this.id);
        poissonAdapteur.put("alarmePasse", ""+this.alarmePasse);
        return poissonAdapteur;
    }

    public String getDateAlarme() {
        return dateAlarme;
    }

    public void setDateAlarme(String dateAlarme) {
        this.dateAlarme = dateAlarme;
    }

    public int getAlarmePasse() {
        return alarmePasse;
    }

    public void setAlarmePasse(int alarmePasse) {
        this.alarmePasse = alarmePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
