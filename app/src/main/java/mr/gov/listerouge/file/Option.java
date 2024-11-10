package mr.gov.listerouge.file;

public class Option implements Comparable<Option> {
    private String data;
    private String name;
    private String path;
    private ItemType type;

    public enum ItemType {
        File,
        Folder
    }

    public Option(String str, String str2, String str3, ItemType itemType) {
        this.name = str;
        this.data = str2;
        this.path = str3;
        this.type = itemType;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public String getPath() {
        return this.path;
    }

    public ItemType getType() {
        return this.type;
    }

    public int compareTo(Option option) {
        String str = this.name;
        if (str != null) {
            return str.toLowerCase().compareTo(option.getName().toLowerCase());
        }
        throw new IllegalArgumentException();
    }
}
