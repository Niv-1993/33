package ServiceLayer.Objects;

public class ItemService {

    private long id;
    private String name;

    public ItemService(long id,String name){
        this.id=id;
        this.name=name;
    }

    public void setName(String name) { this.name = name; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public long getId() { return id; }
}
