package BussinessLayer;

import java.util.Objects;

public class Item {

    private long id;
    private String name;

    public Item(long id,String name){
        this.id=id;
        this.name=name;
    }

   public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
