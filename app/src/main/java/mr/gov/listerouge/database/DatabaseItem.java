package mr.gov.listerouge.database;

public class DatabaseItem implements Comparable<DatabaseItem> {
    private String firstname;
    private String id;
    private boolean isSelected;
    private String lastname;

    public DatabaseItem(String str, String str2, String str3) {
        this.id = str;
        this.firstname = str2;
        this.lastname = str3;
        setSelected(false);
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastname;
    }

    public int compareTo(DatabaseItem databaseItem) {
        String str = this.id;
        if (str != null) {
            return str.toLowerCase().compareTo(databaseItem.getId().toLowerCase());
        }
        throw new IllegalArgumentException();
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
