package mr.gov.listerouge.models;

public class VehiRequest {
    // Define the fields based on your API requirements
    private String chassis;
    private String matricule;
    private String nniUser;

    // Constructor
    public VehiRequest(String chassis, String matricule, String nniUser) {
        this.chassis = chassis;
        this.matricule = matricule;
        this.nniUser = nniUser;
    }

    // Getters and Setters
    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNniUser() {
        return nniUser;
    }

    public void setNniUser(String nniUser) {
        this.nniUser = nniUser;
    }
}
