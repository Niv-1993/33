package ServiceLayer.Objects;

public class BranchService {

    private String phone;
    private String contactName;
    private int id;
    private  String Area;

    public BranchService(String phone, String contactName, int id,String area ){

        this.phone=phone;
        this.contactName=phone;
        this.id=id;
        this.Area=area;
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
