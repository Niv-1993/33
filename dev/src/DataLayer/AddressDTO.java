package DataLayer;

import java.util.Objects;

public class AddressDTO {

    private int number;
    private String street;
    private String city;

    public AddressDTO(int number, String street, String city){

        this.city=city;
        this.number=number;
        this.street=street;
    }
    public int getNumber() {return this.number;}
    public String getStreet(){ return this.street;}
    public String getCity(){return this.city;}

    public void setNumber(int num){this.number=num;}
    public void setStreet(String street){this.street=street;}
    public void setCity(String city){this.city=city;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return number == that.number && Objects.equals(street, that.street) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, street, city);
    }
}
