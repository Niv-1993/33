package DataAccess;

import Business.EmployeePKG.Employee;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeMapper extends Mapper {
    private static EmployeeMapper empMapper = null;
    private final HashMap<Integer, Employee> employees;
    private final String tableName = "Employees";
    private final String id = "EID";

    public static EmployeeMapper getInstance() {
        if (empMapper == null)
            empMapper = new EmployeeMapper();
        return empMapper;
    }


    private EmployeeMapper() {
        super();
        employees = new HashMap<>();
        createTables();
    }

    public Employee get(int EID) {
        Employee emp = null;
        ResultSet res;
        if (employees.containsKey(EID))
            return employees.get(EID);
        String query = String.format("SELECT * FROM %s WHERE %s=? AND BID=%d", tableName, this.id,getCurrBranchID());
        try (Connection con = connect(); PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, EID);
            res = pre.executeQuery();
            if (res.first()) {
                int[] bankDetails = {res.getInt("AccountNumber"), res.getInt("BankID"), res.getInt("BranchNumber")};
                int[] terms = {res.getInt("EducationFund"), res.getInt("DaysOff"), res.getInt("SickDays")};
                emp = new Employee(res.getInt("EID"), res.getString("Name"), bankDetails, res.getInt("Salary"),
                        LocalDate.parse(res.getString("StartWorkingDate")), terms);
                employees.put(emp.getEID(), emp);
            }
        } catch (Exception e) {
        } finally {
            if (emp != null)
                loadAllRoles(emp);
        }
        return emp;
    }

    public HashMap<Integer, Employee> getEmployees() {
        return employees;
    }


    public boolean containsKey(int eid) {
        if (employees.containsKey(eid))
            return true;
        return get(eid) != null;
    }

    private void loadAllRoles(Employee emp) {
        String query = "SELECT * FROM RolesAndEmployees WHERE EID= ?";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, emp.getEID());
            ResultSet res = pre.executeQuery();
            List<String> roles = new ArrayList<>();
            while (res.next()) {
                roles.add(res.getString("Role"));
            }
            emp.setRole(roles);
        } catch (Exception e) {
        }
    }


    public boolean insert(int EID, Employee emp) {
        boolean res = false;
        String query = "INSERT INTO %s VALUES(?,?,?,?,?,?,?,?,?,?,?,?);\n" +
                "INSERT INTO RolesAndEmployees VALUES(?,?)";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, emp.getEID());
            pre.setString(2, emp.getName());
            pre.setString(3, emp.getStartWorkingDate().toString());
            pre.setInt(4, emp.getSalary());
            pre.setInt(5, emp.getBankAccount().getBankID());
            pre.setInt(6, emp.getBankAccount().getBankBranch());
            pre.setInt(7, emp.getBankAccount().getAccountNum());
            pre.setInt(8, emp.getTermsOfEmployment().getEducationFun());
            pre.setInt(9, emp.getTermsOfEmployment().getDaysOff());
            pre.setInt(10, emp.getTermsOfEmployment().getSickDays());
            pre.setInt(11, 1);
            pre.setInt(12, getCurrBranchID());
            pre.setInt(13, emp.getEID());
            pre.setString(14, emp.getRole().get(0).name());
            res = pre.executeUpdate() > 0;
        } catch (Exception e) {
        } finally {
            if (res)
                employees.put(EID, emp);
        }
        return res;
    }

    public List<Integer> getAllBranches() {
        List<Integer> branches = new ArrayList<>();
        String query = "SELECT BID FROM Branches";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                branches.add(res.getInt("BID"));
            }
        } catch (Exception e) {
        }
        return branches;
    }

    //TODO: ask what to do with all the branches properties in database
    public void insertNewBranch(int newEID, Employee m) {
        String getNextBID = "SELECT max(BID)+1 AS mx FROM Branches";
        String addBranch = "INSERT INTO BRANCHES (?,'','',0,0,'',0,0)";
        try (Connection con = connect();
             PreparedStatement nextID = con.prepareStatement(getNextBID);
             PreparedStatement pre = con.prepareStatement(addBranch)) {
            ResultSet res = nextID.executeQuery();
            int bid = res.getInt("mx");
            if (bid == 0) bid++;
            pre.setInt(1, bid);
            pre.executeUpdate();
        } catch (Exception e) {
        } finally {
            insert(newEID, m);
        }
    }


    public Employee delete(int fireEID) {
        Employee removed = employees.remove(fireEID);
        updateIntInt(fireEID, 0, "Active", id, tableName);
        return removed;
    }


    public void updateName(int updateEID, String newName) {
        updateIntString(updateEID, newName, "Name", id, tableName);
    }

    public void updateSalary(int updateEID, int newSalary) {
        updateIntInt(updateEID, newSalary, "Salary", id, tableName);
    }

    public void updateBankAccountNum(int updateEID, int newAccountNumber) {
        updateIntInt(updateEID, newAccountNumber, "AccountNumber", id, tableName);
    }

    public void updateBankBranch(int updateEID, int newBranch) {
        updateIntInt(updateEID, newBranch, "BranchNumber", id, tableName);
    }

    public void updateBankID(int updateEID, int newBankID) {
        updateIntInt(updateEID, newBankID, "BankID", id, tableName);
    }

    public void updateEducationFund(int updateEID, int newEducationFund) {
        updateIntInt(updateEID, newEducationFund, "EducationFund", id, tableName);
    }

    public void updateDaysOff(int updateEID, int newAmount) {
        updateIntInt(updateEID, newAmount, "DaysOff", id, tableName);
    }

    public void updateSickDays(int updateEID, int newAmount) {
        updateIntInt(updateEID, newAmount, "SickDays", id, tableName);
    }


    public List<Employee> loadEmployeesInBranch() {
        String query = String.format("SELECT * FROM %s as E JOIN RolesAndEmployees as R ON E.EID=R.EID WHERE BID=? AND Active = 1", tableName);
        Map<Integer, Employee> emps = new HashMap<>();
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, getCurrBranchID());
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                if (emps.containsKey(res.getInt(id))) {
                    emps.get(res.getInt(id)).addRole(res.getString("Role"));
                } else {
                    int[] bankDetails = {res.getInt("AccountNumber"), res.getInt("BankID"), res.getInt("BranchNumber")};
                    int[] terms = {res.getInt("EducationFund"), res.getInt("DaysOff"), res.getInt("SickDays")};
                    Employee emp = new Employee(res.getInt("EID"), res.getString("Name"), bankDetails, res.getInt("Salary"),
                            LocalDate.parse(res.getString("StartWorkingDate")), terms);
                    emp.addRole(res.getString("Role"));
                    emps.put(emp.getEID(), emp);
                }
            }
        } catch (Exception e) {
        }
        return new ArrayList<>(emps.values());
    }
}
