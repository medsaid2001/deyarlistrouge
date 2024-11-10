package mr.gov.listerouge.models;

public class Location {
    private String type;
    private Coordinates coordinates;

    // Getter and Setter methods

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
