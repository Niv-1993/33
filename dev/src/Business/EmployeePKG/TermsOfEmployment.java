package Business.EmployeePKG;

import org.apache.log4j.Logger;

public class TermsOfEmployment {
    final static Logger log = Logger.getLogger(TermsOfEmployment.class);
    private int educationFun;
    private int daysOff;
    private int sickDays;

    public TermsOfEmployment(int[] terms) throws Exception {
        checkTerms(terms);
        this.educationFun = terms[0];
        this.daysOff = terms[1];
        this.sickDays = terms[2];
    }

    /**
     * copy constructor
     * @param other
     */
    public TermsOfEmployment(TermsOfEmployment other) {
        this.educationFun = other.educationFun;
        this.daysOff = other.daysOff;
        this.sickDays = other.sickDays;
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

    public void setDaysOff(int daysOff) throws Exception {
        if (daysOff < 0) {
            log.error("the new amount of days off is negative: "+daysOff);
            throw new Exception("invalid new amount of days off: "+daysOff);
        }
        this.daysOff = daysOff;
    }

    public void setEducationFun(int educationFun) throws Exception {
        if (educationFun <= 0) {
            log.error("the new education fund is 0 or negative: "+educationFun);
            throw new Exception("invalid new education fund number: "+educationFun);
        }
        this.educationFun = educationFun;
    }

    public void setSickDays(int sickDays) throws Exception {
        if (sickDays < 0) {
            log.error("the new amount of sick-days is negative: "+sickDays);
            throw new Exception("invalid new amount of sick-days: "+sickDays);
        }
        this.sickDays = sickDays;
    }
    private void checkTerms(int[] terms) throws Exception {
        log.debug("checking terms");
        boolean isValid = terms[0] > 0 && terms[1] >= 0 && terms[2] >= 0;
        if (!isValid) {
            log.error("terms are invalid : education fund: " + terms[0] + " daysOff: " + terms[1] + " sickDays: " + terms[2]);
            throw new Exception("Invalid terms of employment");
        }
    }
}
