package ServiceLayer.Objects;

public class BranchServiceDTO {

    private String phone;
    private String contactName;
    private int id;
    private  String Area;

    public BranchServiceDTO(String phone, String contactName, int id, String area ){

        this.phone=phone;
        this.contactName=contactName;
        this.id=id;
        this.Area=area;
    }


    @Override
    public String toString() {
        return "BranchServiceDTO{\tphone='" + phone +
                "\tcontactName='" + contactName +
                "\tid=" + id +
                "\tArea='" + Area +
                "\t}\n";
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public String getArea() { return Area; }

    public String getContactName() { return contactName; }

    public String getPhone() { return phone; }

    public void setArea(String area) { Area = area; }

    public void setContactName(String contactName) { this.contactName = contactName; }

    public void setPhone(String phone) { this.phone = phone; }

}
