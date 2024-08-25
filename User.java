package bean;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public String getUserId() { return userId; }
    public String getPin() { return pin; }
    public Account getAccount() { return account; }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }
}
