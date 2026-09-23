import java.io.Serializable;

public class Category implements Serializable {

    private static final long serialVersionUID = 2L;

    private int id;
    private String name;
    private String description;

    public Category(int id, String name) {
        this(id, name, "General Category");
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = (description != null && !description.trim().isEmpty()) ? description.trim() : "General Category";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d | Category: %-16s | Description: %s", id, name, description);
    }
}