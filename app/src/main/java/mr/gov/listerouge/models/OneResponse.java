package mr.gov.listerouge.models;
import com.google.gson.annotations.SerializedName;

public class OneResponse {
    @SerializedName("_id")
    private String id;

    @SerializedName("nni")
    private String nni;

    @SerializedName("description")
    private String description;

    @SerializedName("conduitContact")
    private String conduitContact;

    @SerializedName("pieceJustif")
    private String pieceJustif;

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getNni() {
        return nni;
    }

    public String getDescription() {
        return description;
    }

    public String getConduitContact() {
        return conduitContact;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
