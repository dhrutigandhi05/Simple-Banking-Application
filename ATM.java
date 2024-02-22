import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
public class ATM {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);

        Bank theBank = new Bank("The Bank");

        User aUser = theBank.addUser("Jane", "Doe", "1309");
        System.out.println(aUser.toString());

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currentUser;
        while(true){
            currentUser = ATM.mainMenuPrompt(theBank, input);
            ATM.printUserMenu(currentUser, input);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner input) throws NoSuchAlgorithmException {
        String userID;
        String pin;
        User authenticatedUser;

        do {
            System.out.printf("\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = input.nextLine();
            System.out.print("Enter Pin: ");
            pin = input.nextLine();

            authenticatedUser = theBank.userLogin(userID, pin);
            if (authenticatedUser == null) {
                System.out.println("Incorrect user ID or pin combination. Please try again.");

            }
        } while(authenticatedUser == null);

        return authenticatedUser;
    }

    public static void printUserMenu(User theUser, Scanner input){
        theUser.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1. Show account transaction history");
            System.out.println(" 2. Withdraw");
            System.out.println(" 3. Deposit");
            System.out.println(" 4. Transfer funds");
            System.out.println(" 5. Log out");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid option. Choose between 1-5");
            }
        } while(choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, input);
                break;

            case 2:
                ATM.withdrawalFunds(theUser, input);
                break;

            case 3:
                ATM.depositFunds(theUser, input);
                break;

            case 4:
                ATM.transferFunds(theUser, input);
                break;

            case 5:
                input.nextLine();
                break;
        }

        if (choice != 5) {
            ATM.printUserMenu(theUser, input);
        }

    }

    public static void showTransactionHistory(User theUser, Scanner input) {
        int theAccount;

        do {
            System.out.printf("Enter the number (1-%d) of the account whose transaction you want to see: ", theUser.numAccounts());
            theAccount = input.nextInt()-1;
            if (theAccount < 0 || theAccount >= theUser.numAccounts()){
                System.out.println("Invalid Account. Please try again.");
            }
        } while(theAccount < 0 || theAccount >= theUser.numAccounts());

        theUser.printAccountTransactionHistory(theAccount);
    }

    public static void transferFunds(User theUser, Scanner input){
        int fromAccount;
        int toAccount;
        double amount;
        double accountBalance;

        // transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account you would like to transfer from: ", theUser.numAccounts());
            fromAccount = input.nextInt()-1;

            if(fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }

        } while(fromAccount < 0 || fromAccount >= theUser.numAccounts());

        accountBalance = theUser.getAccountBalance(fromAccount);

        // transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account you would like to transfer to: ", theUser.numAccounts());
            toAccount = input.nextInt()-1;

            if(toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }

        } while(toAccount < 0 || toAccount >= theUser.numAccounts());

        // amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", accountBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Invalid amount. Please try again.");
            } else if (amount > accountBalance) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount < 0 || amount > accountBalance);

        theUser.addAccountTransaction(fromAccount, -1*amount, String.format("Transfer to account %s", theUser.getUniqueAccountID(toAccount)));
        theUser.addAccountTransaction(toAccount, amount, String.format("Transfer from account %s", theUser.getUniqueAccountID(fromAccount)));
    }

    public static void withdrawalFunds(User theUser, Scanner input){
        int fromAccount;
        double amount;
        double accountBalance;

        // withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account you would like to withdrawal money from: ", theUser.numAccounts());
            fromAccount = input.nextInt()-1;

            if(fromAccount < 0 || fromAccount >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }

        } while(fromAccount < 0 || fromAccount >= theUser.numAccounts());

        accountBalance = theUser.getAccountBalance(fromAccount);

        // amount to withdraw
        do {
            System.out.printf("Enter the amount to withdrawal (max $%.02f): $", accountBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Invalid amount. Please try again.");
            } else if (amount > accountBalance) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount < 0 || amount > accountBalance);

        input.nextLine();

        theUser.addAccountTransaction(fromAccount, -1*amount, null);
    }

    public static void depositFunds(User theUser, Scanner input){
        int toAccount;
        double amount;
        double accountBalance;

        // deposit to
        do {
            System.out.printf("Enter the number (1-%d) of the account you would like to deposit to: ", theUser.numAccounts());
            toAccount = input.nextInt()-1;

            if(toAccount < 0 || toAccount >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please try again.");
            }

        } while(toAccount < 0 || toAccount >= theUser.numAccounts());

        accountBalance = theUser.getAccountBalance(toAccount);

        // amount to deposit
        do {
            System.out.printf("Enter the amount to deposit: $", accountBalance);
            amount = input.nextDouble();
            if (amount < 0) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount < 0);

        theUser.addAccountTransaction(toAccount, amount, null);
    }
}