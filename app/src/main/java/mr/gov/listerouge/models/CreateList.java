package mr.gov.listerouge.models;

public class CreateList {
    private DeviceInfo.Location location;
    private String nni;
    private String description;
    private String conduitContact;
    private String pieceJustif;
    private String nud;
    private String requestId;
    private String matricule;
    private String photo;
    public CreateList(DeviceInfo.Location location,String nni,String description,String conduitContact,String pieceJustif
    ,String nud,String requestId,String matricule,String photo ){
        this.nni = nni;
        this.description = description;
        this.conduitContact = conduitContact;
        this.pieceJustif = pieceJustif;
        this.location = location;
        this.nud = nud;
        this.requestId = requestId;
        this.matricule = matricule;
        this.photo = photo;
    }
    public DeviceInfo.Location getLocation() {
        return location;
    }
    public void setLocation(DeviceInfo.Location location) {
        this.location = location;
    }

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConduitContact() {
        return conduitContact;
    }

    public void setConduitContact(String conduitContact) {
        this.conduitContact = conduitContact;
    }

    public String getPieceJustif() {
        return pieceJustif;
    }

    public void setPieceJustif(String pieceJustif) {
        this.pieceJustif = pieceJustif;
    }

    public String getNud() {
        return nud;
    }

    public void setNud(String nud) {
        this.nud = nud;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
