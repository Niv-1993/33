package DataAccess;

import Business.Employees.EmployeePKG.Driver;
import Business.Type.RoleType;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DriverMapper extends Mapper{

    static  private DriverMapper mapper=null;
    private Map <Integer, Driver> drivers;

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

    public void insert(Driver driver){
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
    public Driver select(int id) throws  Exception {
        if(drivers.containsKey(id))
            return drivers.get(id);
        String sql = String.format("SELECT * FROM Employees as E JOIN Drivers as D ON E.EID = D.EID  WHERE E.EID= %d",id);
        Driver driver = null;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            if (rs.next()) {
                int[] bankDetails = {rs.getInt("AccountNumber"), rs.getInt("BankID"), rs.getInt("BranchNumber")};
                int[] terms = {rs.getInt("EducationFund"), rs.getInt("DaysOff"), rs.getInt("SickDays")};
                int did=rs.getInt("EID");
                String name=rs.getString("Name");
                int salary= rs.getInt("Salary");
                LocalDate date=  LocalDate.parse(rs.getString("StartWorkingDate"));
                int license=rs.getInt("License");
                driver = new Driver(did, name, bankDetails,salary, RoleType.Driver, date, terms,license);
                drivers.put(driver.getEID(),driver);
                EmployeeMapper.getInstance().getEmployees().put(driver.getEID(),driver);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get a driver"+id +"from database");
        }
        return driver;
    }

}
