package mr.gov.listerouge.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "red_list_items")
public class RedListItem {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @SerializedName("_id")
    private String _id;

    @SerializedName("nni")
    private String nni;

    @SerializedName("nud")
    private String nud;

    @SerializedName("requestId")
    private String requestId;

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("description")
    private String description;

    @SerializedName("conduitContact")
    private String conduitContact;

    @SerializedName("pieceJustif")
    private String pieceJustif;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @SerializedName("photo")
    private String photo;
}
