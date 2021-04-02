package Business.EmployeePKG;

import org.apache.log4j.Logger;

public class BankAccount {
    final static Logger log = Logger.getLogger(BankAccount.class);
    private int accountNum;
    private int bankBranch;
    private int bankID;


    public BankAccount(int[] bankDetails) throws Exception {
        checkBank(bankDetails);
        this.accountNum = bankDetails[0];
        this.bankBranch = bankDetails[1];
        this.bankID = bankDetails[2];
    }

    /**
     * copy constructor
     * @param other
     */
    public BankAccount(BankAccount other){
        this.accountNum = other.accountNum;
        this.bankBranch = other.bankBranch;
        this.bankID = other.bankID;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public int getBankID() {
        return bankID;
    }

    public int getBankBranch() {
        return bankBranch;
    }

    public void setAccountNum(int accountNum) throws Exception {
        if (accountNum <= 0) {
            log.error("the new account number is 0 or negative: "+accountNum);
            throw new Exception("invalid new account number: "+accountNum);
        }
        this.accountNum = accountNum;
    }

    public void setBankID(int bankID) throws Exception {
        if (bankID <= 0) {
            log.error("the new bank id is 0 or negative: "+bankID);
            throw new Exception("invalid new bank id number: "+bankID);
        }
        this.bankID = bankID;
    }

    public void setBankBranch(int bankBranch) throws Exception {
        if (bankBranch <= 0) {
            log.error("the new branch is 0 or negative: "+bankBranch);
            throw new Exception("invalid new branch number: "+bankBranch);
        }
        this.bankBranch = bankBranch;
    }

    private void checkBank(int[] bankDetails) throws Exception {
        log.debug("checking bank details");
        boolean isValid = bankDetails[0] > 0 && bankDetails[1] > 0 && bankDetails[2] > 0;
        if (!isValid) {
            log.error("bank details are invalid: AccountNum: " + bankDetails[0] + " BankBranch: " + bankDetails[1] + " BandID: " + bankDetails[2]);
            throw new Exception("Invalid bank details");
        }
    }
}
