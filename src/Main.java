import java.util.ArrayList;
import java.util.List;

abstract class BankAccount {
    protected int interestRate;
    protected int accountBalance;
    protected int accountNumber;
    protected String type;
    private static int accountNumberAutoGenerate = 772681;

    public BankAccount(int interestRate, int accountBalance, String type) {
        this.type = type;
        this.interestRate = interestRate;
        this.accountBalance = accountBalance;
        accountNumberAutoGenerate++;
        this.accountNumber = accountNumberAutoGenerate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void withdraw(int amount) {
        if (accountBalance >= amount) {
            accountBalance -= amount;
            System.out.println("Successfully Withdrawn");
        } else {
            System.out.println("INSUFFICIENT BALANCE");
        }
    }

    public void deposit(int amount) {
        accountBalance += amount;
        System.out.println("SUCCESSFULLY DEPOSITED");
    }

    public void printDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + type);
        System.out.println("Account Balance: " + accountBalance);
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }

    public abstract void payInterest();
}

class Customer {
    private String custName;
    private String ID;
    private List<BankAccount> accounts;

    public Customer(String custName, String ID, List<BankAccount> accounts) {
        this.custName = custName;
        this.ID = ID;
        this.accounts = accounts;
    }

    public void deposit(int accountNumber, int amount) {
        BankAccount account = null;
        boolean flag = false;

        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                flag = true;
                account = acc;
                break;
            }
        }

        if (flag) {
            account.deposit(amount);
        } else {
            System.out.println("This account does not exist");
        }
    }

    public void withdraw(int accountNumber, int amount) {
        BankAccount account = null;
        boolean flag = false;

        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                flag = true;
                account = acc;
                break;
            }
        }

        if (flag) {
            account.withdraw(amount);
        } else {
            System.out.println("This account does not exist");
        }
    }

    public void printDetails() {
        System.out.println();
        System.out.println("================================================================================================================================\n\n");
        System.out.println("Customer Name: " + custName);
        System.out.println("Customer ID: " + ID);
        System.out.println("Account Details: \n");

        for (BankAccount account : accounts) {
            account.printDetails();
        }
    }
}

final class Bank {
    private String bankName;
    private String IFSC;
    private List<Customer> customers;

    public Bank(String bankName, String IFSC, List<Customer> customers) {
        this.bankName = bankName;
        this.IFSC = IFSC;
        this.customers = customers;
    }

    public void printDetails() {
        System.out.println("Bank Name: " + bankName);
        System.out.println("Customer Details: ");

        for (Customer customer : customers) {
            customer.printDetails();
        }
    }
}

class CurrentBankAccount extends BankAccount {
    public CurrentBankAccount(int initialAmount) {
        super(5, initialAmount, "CURRENT");
    }

    @Override
    public void payInterest() {
        float interest = (accountBalance * 5 * 1) / 100.0f;
        System.out.println("Yearly Interest for this account will be Rs. " + interest);
    }
}

class SavingsBankAccount extends BankAccount {
    public SavingsBankAccount(int initialAmount) {
        super(8, initialAmount, "SAVINGS");
    }

    @Override
    public void payInterest() {
        float interest = (accountBalance * 8 * 1) / 100.0f;
        System.out.println("Yearly Interest for this account will be Rs. " + interest);
    }
}

public class Main {
    public static void main(String[] args) {
        List<BankAccount> accounts1 = new ArrayList<>();
        accounts1.add(new SavingsBankAccount(8000));
        accounts1.add(new SavingsBankAccount(8000));
        accounts1.add(new CurrentBankAccount(6000));

        List<BankAccount> accounts2 = new ArrayList<>();
        accounts2.add(new CurrentBankAccount(5000));
        accounts2.add(new CurrentBankAccount(1000));

        Customer saurav = new Customer("Saurav Kumar", "ID-001", accounts1);
        Customer anandSagar = new Customer("Anand Sagar", "ID-002", accounts2);

        Bank SBI = new Bank("SBI HUBLI B1", "SBI000012", List.of(saurav, anandSagar));

        saurav.deposit(102, 1000); // This account does not exist
        saurav.deposit(772682, 2000); // this account exists and it's value and 1000 will be deposited in this account

        saurav.withdraw(772682, 13879348); // Withdrawing more than my limit
        saurav.withdraw(772682, 1500); // withdrawing within my limit

        SBI.printDetails();
    }
}

