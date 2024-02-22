import java.util.Date;
public class Transaction {
    private double amount;
    private Date timestamp;
    private Account inAccount;
    private String description;

    public double getAmount() {return this.amount;}

    public Transaction(double amount, String description, Account inAccount) {
        this.amount = amount;
        this.description = description;
        this.inAccount = inAccount;
        this.timestamp = new Date();
    }

    public String getSummaryLine(){
        if (this.amount >=0){
            return String.format("%s : $%.02f", this.timestamp.toString(), this.amount);
        } else {
            return String.format("%s : $(%.02f)", this.timestamp.toString(), -this.amount);
        }
    }
}
