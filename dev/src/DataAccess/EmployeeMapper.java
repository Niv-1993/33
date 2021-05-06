package DataAccess;

import Business.EmployeePKG.Employee;
import java.util.HashMap;

public class EmployeeMapper extends Mapper {

    private static EmployeeMapper empMapper = null;
    private HashMap<Integer, Employee> employees;

    public static EmployeeMapper getInstance() {
        if (empMapper == null)
            empMapper = new EmployeeMapper();
        return empMapper;
    }


    private EmployeeMapper(){
        super();
        employees = new HashMap<>();
    }
    public HashMap<Integer, Employee> getEmployees() {
        return employees;
    }



}
