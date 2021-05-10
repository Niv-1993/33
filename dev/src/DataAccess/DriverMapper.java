package DataAccess;

import Business.Employees.EmployeePKG.Employee;
import Business.Transportation.Driver;
import Business.Type.RoleType;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DriverMapper extends Mapper{

    static  private DriverMapper mapper=null;
    private Map <Integer, Business.Employees.EmployeePKG.Driver> drivers;

    public static DriverMapper getMapper(){
        if(mapper==null){
            mapper=new DriverMapper();
        }
        return mapper;
    }

    private DriverMapper(){
        super();
        drivers=new HashMap<>();
    }

    public HashMap<Integer, Driver>  selectAll() throws Exception {
        String sql = "SELECT * FROM Drivers";
        HashMap<Integer, Driver>  ret=new HashMap<>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
               ret.put(rs.getInt("EID"),new Driver(rs.getInt("EID"),rs.getInt("License")));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return ret;
    }
    public void insert(Business.Employees.EmployeePKG.Driver driver){
        String query = "INSERT INTO Drivers (EID,License) VALUES(?,?)";
        int res = 0;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1,driver.getEID());
            pstmt.setInt(2,driver.getLicense());
            res = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(res >0)
            drivers.put(driver.getEID(),driver);
    }
    public Business.Employees.EmployeePKG.Driver select(int id) throws  Exception {
        if(drivers.containsKey(id))
            return drivers.get(id);
        String sql = String.format("SELECT * FROM Employees as E JOIN Drivers as D ON E.EID = D.EID  WHERE E.EID= %d",id);
        Business.Employees.EmployeePKG.Driver driver = null;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                int[] bankDetails = {rs.getInt("AccountNumber"), rs.getInt("BankID"), rs.getInt("BranchNumber")};
                int[] terms = {rs.getInt("EducationFund"), rs.getInt("DaysOff"), rs.getInt("SickDays")};
                driver = new Business.Employees.EmployeePKG.Driver(rs.getInt(0), rs.getString("Name"), bankDetails, rs.getInt("Salary"),
                        RoleType.Driver, LocalDate.parse(rs.getString("StartWorkingDate")), terms,rs.getInt("License"));
                drivers.put(driver.getEID(),driver);
                EmployeeMapper.getInstance().getEmployees().put(driver.getEID(),driver);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return driver;
    }

}
