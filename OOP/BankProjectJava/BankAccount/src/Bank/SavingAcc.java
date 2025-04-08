import javax.swing.JOptionPane;
public class SavingAcc {
    private int balance;
    private float AnnualInterestRate;
    public SavingAcc(int balance, float AnnualInterestRate) {
        this.balance = balance;
        this.AnnualInterestRate = AnnualInterestRate;
    }
    public int ShowBalance() {
        JOptionPane.showMessageDialog(null, "Your balance is " + balance);
        return balance;
    }
    public void Deposit(int money) {
        balance += money;
        JOptionPane.showMessageDialog(null, "You have deposited " + money + " into your account");
        JOptionPane.showMessageDialog(null, "Your balance is now " + balance);
    }
    public void EndingBalance(int month) {
        balance += balance * AnnualInterestRate * month / 12;
        JOptionPane.showMessageDialog(null, "Your ending balance is " + balance);
    }
}