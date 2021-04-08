package ServiceLayer.Objects;

public class ItemServiceDTO {

    private long id;
    private String name;

    @Override
    public String toString() {
        return "ItemServiceDTO{\n" +
                "\tid=" + id +
                "\n\tname='" + name+
                "\n\t}\n";
    }

    public ItemServiceDTO(long id, String name){
        this.id=id;
        this.name=name;
    }

    public void setName(String name) { this.name = name; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public long getId() { return id; }
}