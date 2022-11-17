package matej.mrozek.shopping.atm;

public class Account {
    public final String username;

    private final String password;

    private int balance = 0;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean checkPasswordCorrect(String password) {
        if (password == null) {
            return false;
        }

        return this.password.equals(password);
    }

    public int getBalance() {
        return balance;
    }

    public void addMoney(int amount) {
        balance += amount;
    }

    public void removeMoney(int amount) {
        balance -= amount;
    }
}
