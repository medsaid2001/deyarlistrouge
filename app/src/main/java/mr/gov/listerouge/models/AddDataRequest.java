package mr.gov.listerouge.models;

import com.google.gson.annotations.SerializedName;

public class AddDataRequest {

    @SerializedName("nni")
    private String nni;

    @SerializedName("description")
    private String description;

    @SerializedName("conduitContact")
    private String conduitContact;


    @SerializedName("pieceJustif")
    private String pieceJustifBase64;  // This will hold the Base64 encoded string of the image

    public AddDataRequest(String _nni, String description, String conduitContact, String pieceJustifBase64) {
        this.nni = _nni;
        this.description = description;
        this.conduitContact = conduitContact;
        this.pieceJustifBase64 = pieceJustifBase64;
    }

}