package mr.gov.listerouge.models;

public class PersonDetailModel {
    private String id;
    private String nni;
    private String nud;
    private String requestId;
    private String matricule;
    private String description;
    private String conduitContact;
    private String pieceJustif;
    private String photo;

    public PersonDetailModel(String id, String nni, String nud, String requestId, String matricule, String description, String conduitContact, String pieceJustif, String photo) {
        this.id = id;
        this.nni = nni;
        this.nud = nud;
        this.requestId = requestId;
        this.matricule = matricule;
        this.description = description;
        this.conduitContact = conduitContact;
        this.pieceJustif = pieceJustif;
        this.photo = photo;
    }

    public String getId() { return id; }
    public String getNni() { return nni; }
    public String getNud() { return nud; }
    public String getRequestId() { return requestId; }
    public String getMatricule() { return matricule; }
    public String getDescription() { return description; }
    public String getConduitContact() { return conduitContact; }
    public String getPieceJustif() { return pieceJustif; }
    public String getPhoto() { return photo; }
}

