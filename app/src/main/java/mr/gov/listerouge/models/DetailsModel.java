package mr.gov.listerouge.models;

public class DetailsModel {
    private String nni;
    private String conduitcontact;
    private String nud;
    private String requestid;
    private String datered;
    private String description;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getConduitcontact() {
        return conduitcontact;
    }

    public void setConduitcontact(String conduitcontact) {
        this.conduitcontact = conduitcontact;
    }

    public String getNud() {
        return nud;
    }

    public void setNud(String nud) {
        this.nud = nud;
    }

    public String getDatered() {
        return datered;
    }

    public void setDatered(String datered) {
        this.datered = datered;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
