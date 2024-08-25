package bean;
import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime dateTime;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.dateTime = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f", dateTime, type, amount);
    }
}

