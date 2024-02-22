import java.util.ArrayList;

public class Account {
    private String accountName;
    //private double balance;
    private String accountID;
    private User accountHolder;
    private ArrayList<Transaction> transactions;

    public String getAccountID() {return this.accountID;}

    public Account(String accountName, User accountHolder, Bank theBank) {
        this.accountName = accountName;
        this.accountHolder = accountHolder;

        this.accountID = theBank.getNewAccountID();

        this.transactions = new ArrayList<Transaction>();
    }

    public String summary(){
        double balance = this.getBalance();

        if (balance >= 0){
            return String.format("Account #%s: %s: $%.02f", this.accountID, this.accountName, balance);
        } else {
            return String.format("Account #%s: %s: $(%.02f)", this.accountID, this.accountName, balance);

        }
    }

    public double getBalance(){
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransactionHistory(){
        System.out.printf("\nTransaction history for account %s\n", this.accountID);
        for (int t = this.transactions.size()-1; t>0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String description){
        Transaction newTransaction = new Transaction(amount, description, this);
        this.transactions.add(newTransaction);
    }
}
