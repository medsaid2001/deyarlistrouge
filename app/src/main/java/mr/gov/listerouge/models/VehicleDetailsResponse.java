package mr.gov.listerouge.models;


import com.google.gson.annotations.SerializedName;

public class VehicleDetailsResponse {

    @SerializedName("num_chassus")
    private String numChassus;

    @SerializedName("marque")
    private String marque;

    @SerializedName("typev")
    private String typev;

    @SerializedName("couleur_ar")
    private String couleurAr;

    @SerializedName("couleur_fr")
    private String couleurFr;

    @SerializedName("amc")
    private String amc;

    @SerializedName("poids_vide")
    private int poidsVide;

    @SerializedName("poids_charge")
    private int poidsCharge;

    @SerializedName("nbr_places")
    private int nbrPlaces;

    @SerializedName("source_energie_fr")
    private String sourceEnergieFr;

    @SerializedName("num_declaration")
    private String numDeclaration;

    @SerializedName("date_declaration")
    private String dateDeclaration;

    @SerializedName("date_quitance_dedouanement")
    private String dateQuitanceDedouanement;

    @SerializedName("serie")
    private String serie;

    @SerializedName("bureau_dedouanement")
    private String bureauDedouanement;

    @SerializedName("anne_enregistrement")
    private String anneEnregistrement;

    @SerializedName("numero_volet")
    private String numeroVolet;

    @SerializedName("date_enregistrement_dtt")
    private String dateEnregistrementDtt;

    @SerializedName("matricule_fr")
    private String matriculeFr;

    @SerializedName("matricule_ar")
    private String matriculeAr;

    @SerializedName("date_imatriculation")
    private String dateImatriculation;

    @SerializedName("nni_properietaire")
    private String nniProprietaire;

    @SerializedName("nom_properietaire_ar")
    private String nomProprietaireAr;

    @SerializedName("nom_properietaire_fr")
    private String nomProprietaireFr;

    @SerializedName("isInListRouge")
    private boolean isInListRouge;

    // Getters and Setters

    public String getNumChassus() {
        return numChassus;
    }

    public void setNumChassus(String numChassus) {
        this.numChassus = numChassus;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getTypev() {
        return typev;
    }

    public void setTypev(String typev) {
        this.typev = typev;
    }

    public String getCouleurAr() {
        return couleurAr;
    }

    public void setCouleurAr(String couleurAr) {
        this.couleurAr = couleurAr;
    }

    public String getCouleurFr() {
        return couleurFr;
    }

    public void setCouleurFr(String couleurFr) {
        this.couleurFr = couleurFr;
    }

    public String getAmc() {
        return amc;
    }

    public void setAmc(String amc) {
        this.amc = amc;
    }

    public int getPoidsVide() {
        return poidsVide;
    }

    public void setPoidsVide(int poidsVide) {
        this.poidsVide = poidsVide;
    }

    public int getPoidsCharge() {
        return poidsCharge;
    }

    public void setPoidsCharge(int poidsCharge) {
        this.poidsCharge = poidsCharge;
    }

    public int getNbrPlaces() {
        return nbrPlaces;
    }

    public void setNbrPlaces(int nbrPlaces) {
        this.nbrPlaces = nbrPlaces;
    }

    public String getSourceEnergieFr() {
        return sourceEnergieFr;
    }

    public void setSourceEnergieFr(String sourceEnergieFr) {
        this.sourceEnergieFr = sourceEnergieFr;
    }

    public String getNumDeclaration() {
        return numDeclaration;
    }

    public void setNumDeclaration(String numDeclaration) {
        this.numDeclaration = numDeclaration;
    }

    public String getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(String dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public String getDateQuitanceDedouanement() {
        return dateQuitanceDedouanement;
    }

    public void setDateQuitanceDedouanement(String dateQuitanceDedouanement) {
        this.dateQuitanceDedouanement = dateQuitanceDedouanement;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getBureauDedouanement() {
        return bureauDedouanement;
    }

    public void setBureauDedouanement(String bureauDedouanement) {
        this.bureauDedouanement = bureauDedouanement;
    }

    public String getAnneEnregistrement() {
        return anneEnregistrement;
    }

    public void setAnneEnregistrement(String anneEnregistrement) {
        this.anneEnregistrement = anneEnregistrement;
    }

    public String getNumeroVolet() {
        return numeroVolet;
    }

    public void setNumeroVolet(String numeroVolet) {
        this.numeroVolet = numeroVolet;
    }

    public String getDateEnregistrementDtt() {
        return dateEnregistrementDtt;
    }

    public void setDateEnregistrementDtt(String dateEnregistrementDtt) {
        this.dateEnregistrementDtt = dateEnregistrementDtt;
    }

    public String getMatriculeFr() {
        return matriculeFr;
    }

    public void setMatriculeFr(String matriculeFr) {
        this.matriculeFr = matriculeFr;
    }

    public String getMatriculeAr() {
        return matriculeAr;
    }

    public void setMatriculeAr(String matriculeAr) {
        this.matriculeAr = matriculeAr;
    }

    public String getDateImatriculation() {
        return dateImatriculation;
    }

    public void setDateImatriculation(String dateImatriculation) {
        this.dateImatriculation = dateImatriculation;
    }

    public String getNniProprietaire() {
        return nniProprietaire;
    }

    public void setNniProprietaire(String nniProprietaire) {
        this.nniProprietaire = nniProprietaire;
    }

    public String getNomProprietaireAr() {
        return nomProprietaireAr;
    }

    public void setNomProprietaireAr(String nomProprietaireAr) {
        this.nomProprietaireAr = nomProprietaireAr;
    }

    public String getNomProprietaireFr() {
        return nomProprietaireFr;
    }

    public void setNomProprietaireFr(String nomProprietaireFr) {
        this.nomProprietaireFr = nomProprietaireFr;
    }

    public boolean isInListRouge() {
        return isInListRouge;
    }

    public void setInListRouge(boolean inListRouge) {
        isInListRouge = inListRouge;
    }
}

