import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
public class User {
    private String firstName;
    private String lastName;
    private String userID;
    private byte pinHash[]; // uniqueness, flexibility of keys, etc
    private ArrayList<Account> accounts;

    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getUserID() {return this.userID;}
    public User(String firstName, String lastName, String pin, Bank theBank) throws NoSuchAlgorithmException {
        this.firstName = firstName;
        this.lastName = lastName;

        // storing the pin's MD5 hash for security reasons
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());

        this.userID = theBank.getNewUserID();

        this.accounts = new ArrayList<Account>();

    }

    public String toString() {
        return ("New user " + firstName + " " + lastName + " with ID " + this.userID + " has been created.");
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public boolean validatePin(String aPin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
    }

    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
        for(int i = 0; i < this.accounts.size(); i ++){
            System.out.printf(" %d) %s\n", i + 1, this.accounts.get(i).summary());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int accountIndex){
        this.accounts.get(accountIndex).printTransactionHistory();
    }

    public double getAccountBalance(int accountIndex){
        return this.accounts.get(accountIndex).getBalance();
    }

    public String getUniqueAccountID(int accountIndex){
        return this.accounts.get(accountIndex).getAccountID();
    }

    public void addAccountTransaction(int accountIndex, double amount, String description) {
        this.accounts.get(accountIndex).addTransaction(amount, description);
    }

}
