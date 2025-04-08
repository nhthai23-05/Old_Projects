import javax.swing.JOptionPane;
public class NormalBankAcc {
    private static int balance;
    private static int WithdrawFee = 5000;
    private static int BalanceMinimum = 50000;

    public void Deposit(int money) {
        balance += money;
    }

    public void Withdraw(int money) {
        if (balance - money < BalanceMinimum) {
            JOptionPane.showMessageDialog(null, "You cannot withdraw this amount of money");
        } else {
            balance -= money;
            balance -= WithdrawFee;
            JOptionPane.showMessageDialog(null, "You have withdrawn " + money + " from your account");
            JOptionPane.showMessageDialog(null, "Your balance is now " + balance);
        }
    }

    public void ShowBalance() {
        System.out.println("Your balance: " + balance);
    }
}