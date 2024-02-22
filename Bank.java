import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> user;
    private ArrayList<Account> accounts;

    public String getName() {return this.name;}

    public Bank(String name){
        this.name = name;
        this.user = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }
    public String getNewUserID(){
        String userID;
        Random randomlyGenerated = new Random();
        int length = 8;
        boolean nonunique;

        // continue looping until a unique user ID is achieved
        do {
            userID = "";
            for (int i = 0; i < length; i++){
                userID += ((Integer)randomlyGenerated.nextInt(10)).toString(); //adding a number between 0 and 9 and appending to userID
            }

            nonunique = false;
            for (User u : this.user) {
                if (userID.compareTo(u.getUserID()) == 0){
                    nonunique = true;
                    break;
                }
            }

        } while(nonunique);

        return userID;
    }

    public String getNewAccountID(){
        String accountID;
        Random randomlyGenerated = new Random();
        int length = 10;
        boolean nonunique;

        do {
            accountID = "";
            for (int i = 0; i < length; i++){
                accountID += ((Integer)randomlyGenerated.nextInt(10)).toString();
            }

            nonunique = false;
            for (Account a : this.accounts) {
                if (accountID.compareTo(a.getAccountID()) == 0) {
                    nonunique = true;
                    break;
                }
            }
        } while(nonunique);
        return accountID;
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
    public User addUser(String firstName, String lastName, String pin) throws NoSuchAlgorithmException {
        // creates a new user object and adds it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.user.add(newUser);

        // creates a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) throws NoSuchAlgorithmException {
        for (User u : this.user){
            if (u.getUserID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        return null; //if user not found or wrong pin
    }
}
