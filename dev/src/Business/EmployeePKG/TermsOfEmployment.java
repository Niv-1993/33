package Business.EmployeePKG;

public class TermsOfEmployment {
    private int educationFun;
    private int daysOff;
    private int sickDays;

    public TermsOfEmployment(int educationFun, int daysOff, int sickDays){
        this.educationFun = educationFun;
        this.daysOff = daysOff;
        this.sickDays = sickDays;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public int getEducationFun() {
        return educationFun;
    }

    public int getSickDays() {
        return sickDays;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public void setEducationFun(int educationFun) {
        this.educationFun = educationFun;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }
}
