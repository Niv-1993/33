package Business.EmployeePKG;

public class BankAccount {
    private int accountNum;
    private int bankBranch;
    private int bankID;

    public BankAccount(int accountNum, int bankBranch, int bankID){
        this.accountNum = accountNum;
        this.bankBranch = bankBranch;
        this.bankID = bankID;
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

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public void setBankBranch(int bankBranch) {
        this.bankBranch = bankBranch;
    }
}
