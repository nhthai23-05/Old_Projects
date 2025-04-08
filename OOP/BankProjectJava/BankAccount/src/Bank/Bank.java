import javax.swing.JOptionPane;
// import java.util.Scanner;
public class Bank {
    public void main(String[] args) {
        
        JOptionPane.showMessageDialog(null, "Welcome to the Bank Account System");
        JOptionPane.showMessageDialog(null, "Please choose your account type");
        String[] options = {"Normal Account", "Saving Account"};
        int accountType = JOptionPane.showOptionDialog(null, "Please choose your account type", "Account Type", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (accountType == 0) {
            NormalBankAcc normalAcc = new NormalBankAcc();
            JOptionPane.showMessageDialog(null, "You have chosen Normal Account, you must deposit at least 50000 VND to open an account");
            int money = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount of money you want to deposit"));
            normalAcc.Deposit(money);
            normalAcc.ShowBalance();
            int withdrawMoney = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount of money you want to withdraw"));
            normalAcc.Withdraw(withdrawMoney);
            normalAcc.ShowBalance();

        } else if (accountType == 1) {
            final float AnnualInterestRate = 0.1f;
            JOptionPane.showMessageDialog(null, "You have chosen Saving Account");
            int depositMoney = Integer.parseInt(JOptionPane.showInputDialog("Please enter the amount of money you want to deposit"));
            SavingAcc savingAcc = new SavingAcc(depositMoney, AnnualInterestRate);
            savingAcc.ShowBalance();
            int month = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of months you want to keep your money in the account"));
            savingAcc.EndingBalance(month);
        } else {
            JOptionPane.showMessageDialog(null, "You have not chosen any account type");
            System.exit(0);
        }
    }
}    
