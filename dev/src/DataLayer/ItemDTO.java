package DataLayer;

import BussinessLayer.Item;

import java.util.Objects;

public class ItemDTO {

    private long id;
    private String name;

    public ItemDTO(long id,String name){
        this.id=id;
        this.name=name;
    }

    public void setName(String name) { this.name = name; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public long getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return id == itemDTO.id && Objects.equals(name, itemDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
