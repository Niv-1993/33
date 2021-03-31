package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;
import Business.EmployeePKG.EmployeeController;
import Business.Type.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EmployeeService implements iEmployeeService {
    private EmployeeController employeeController;

    public EmployeeService() {
        this.employeeController = new EmployeeController();
    }


    /**
     * Logins EID with his/her role of to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Login(int EID, String role) {
        try {
            employeeController.Login(EID, RoleType.valueOf(role));
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Logs out the connected employee
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Logout() {
        try {
            employeeController.Logout();
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    /**
     * In order to create a new branch in the system, one must enter the unique code and all the details of the PersonnelEmployee of that branch
     *
     * @param code        for now code to create a branch = 00000;
     * @param newEID      identifier of the new PersonnelManager
     * @param name        his name
     * @param bankDetails bank details of the PersonnelEmployee
     * @param salary      his salary
     * @param terms
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms) {
        try {
            employeeController.createBranch(code,newEID,name,bankDetails,salary,terms);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    /**
     * Add a constraint of const day-type with type-shift and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> addConstConstraint(DayOfWeek day, String shiftType, String reason) {
        try {
            return new ResponseData<>(new Constraint(employeeController.addConstConstraint(day, ShiftType.valueOf(shiftType), reason)));
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Add a constraint on date {@link LocalDate} with type-shift and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> addConstraint(LocalDate c_date, String shiftType, String reason) {
        try {
            return new ResponseData<>(new Constraint(employeeController.addConstraint(c_date, ShiftType.valueOf(shiftType), reason)));
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> removeConstraint(int CID) {
        try {
            return new ResponseData<>(new Constraint(employeeController.removeConstraint(CID)));
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateReasonConstraint(int CID, String newReason) {
        try {
            employeeController.updateReasonConstraint(CID, newReason);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Edit/Update an existing constraint with CID with a different shift-type
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateShiftTypeConstraint(int CID, String newType) {
        try {
            employeeController.updateShiftTypeConstraint(CID, ShiftType.valueOf(newType));
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Add a new employee to the system with all his details
     *
     * @param newEID        Identification number of the new employee
     * @param name          Name of the new employee
     * @param bankDetails   array of bank details :
     *                      bankDetails[0] -> account number
     *                      bankDetails[1] -> bank branch
     *                      bankDetails[2] -> bank id
     * @param salary        THe salary of the new employee
     * @param role          The role of the new employee
     * @param startWorkDate THe starting date of the new employee
     * @param terms         Terms of employments :
     *                      terms[0] -> education fund
     *                      terms[1] -> days off
     *                      terms[2] -> sick days
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response addEmployee(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms) {
        try {
            employeeController.addEmployee(newEID, name, bankDetails, salary, RoleType.valueOf(role), startWorkDate, terms);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response fireEmployee(int fireEID) {
        try {
            employeeController.fireEmployee(fireEID);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeName(int updateEID, String newName) {
        try {
            employeeController.updateEmployeeName(updateEID, newName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeSalary(int updateEID, int newSalary) {
        try {
            employeeController.updateEmployeeSalary(updateEID, newSalary);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBANum(int updateEID, int newAccountNumber) {
        try {
            employeeController.updateEmployeeBANum(updateEID, newAccountNumber);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBABranch(int updateEID, int newBranch) {
        try {
            employeeController.updateEmployeeBABranch(updateEID, newBranch);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBAID(int updateEID, int newBankID) {
        try {
            employeeController.updateEmployeeBAID(updateEID, newBankID);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeEducationFund(int updateEID, int newEducationFund) {
        try {
            employeeController.updateEmployeeEducationFund(updateEID, newEducationFund);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeDaysOff(int updateEID, int newAmount) {
        try {
            employeeController.updateEmployeeDaysOff(updateEID, newAmount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeSickDays(int updateEID, int newAmount) {
        try {
            employeeController.updateEmployeeSickDays(updateEID, newAmount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Create a new shift with given roles and amount of each role at a specific date {@link LocalDate} and shift-type
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param rolesAmount Map with key: role and value: the amount
     * @param date        The date of the shift to take place
     * @param shiftType   type of the shift
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response createShift(Map<String, Integer> rolesAmount, LocalDate date, String shiftType) {
        try {
            employeeController.createShift(rolesAmount, date, ShiftType.valueOf(shiftType));
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Gets the details of a connected employee
     *
     * @return A response object with a value set to the employee
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<Employee> getEmployeeDetails() {
        try {
            Business.EmployeePKG.Employee emp = employeeController.getEmployeeDetails();
            Employee employee = new Employee(emp.getEID(), emp.getName(), employeeController.getCurrConnectedEmpRole(), emp.getBankAccount(),emp.getSalary(), emp.getTermsOfEmployment());
            return new ResponseData<>(employee);
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response object with a value set to shift containing the employees in it,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> getShiftsAndEmployees() {
        try {
            List<Shift> shifts = convertShifts(employeeController.getShiftsAndEmployees());
            return new ResponseData<>(shifts);
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Removes employee with removeEID id from an existing shift with SID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param removeEID Identifier of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response removeEmpFromShift(int SID, int removeEID) {
        try {
            employeeController.removeEmpFromShift(SID, removeEID);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Adds an employee with  id(addEID) and  his/her role to shift(SID)
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID    Identifier of the shift
     * @param addEID Identifier of the employee
     * @param role   The role of the employee will be in the shift
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response addEmpToShift(int SID, int addEID, String role) {
        try {
            employeeController.addEmpToShift(SID, addEID, RoleType.valueOf(role));
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Update the amount of a specific role in shift with SID id with new amount
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param role      The role that will be updated in the shift
     * @param newAmount nNew amount of the role
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateAmountRole(int SID, String role, int newAmount) {
        try {
            employeeController.updateAmountRole(SID, RoleType.valueOf(role), newAmount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * Gets the currently connected employee his/er's shifts and constraints
     *
     * @return A response object with a value set to employee containing the details,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<Employee> getOnlyEmployeeShiftsAndConstraints() {
        try {
            Employee emp = new Employee(employeeController.getCurrConnectedEmpID(), employeeController.getCurrentConEmpName(),
                    employeeController.getCurrConnectedEmpRole(), convertShifts(employeeController.getOnlyEmployeeShifts()),
                    convertConstrains(employeeController.getOnlyEmployeeConstraints()));
            return new ResponseData<>(emp);
        } catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }


    /**
     * Loads the relevant data of a spcific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response loadData(int BID) {
        try {
            employeeController.loadData(BID);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    /**
     * Convert Help Functions of lists
     */
    private List<Shift> convertShifts(List<Business.ShiftPKG.Shift> allShifts) {
        List<Shift> shifts = new ArrayList<>();
        allShifts.forEach(s -> {
            shifts.add(new Shift(s.getSID(), s.getDate(), s.getShiftType(), s.getEmployees()));
        });
        return shifts;
    }

    private List<Constraint> convertConstrains(List<Business.ShiftPKG.Constraint> allConstraints) {
        List<Constraint> constraints = new ArrayList<>();
        allConstraints.forEach(c -> {
            constraints.add(new Constraint(c.getCID(), c.getEID(), c.getReason(),c.getShiftType()));
        });
        return constraints;
    }
}
