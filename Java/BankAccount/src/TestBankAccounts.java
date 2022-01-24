import java.io.*;

/**
 * TestBankAccounts.java:
 *  This class will test aspects of inheritance for the BankAccount class
 *  and its subclasses.
 * @author CS027b 2007
 */

public class TestBankAccounts {
    
    public static void main(String[] args) {
      
        BankAccount bacc0 = new BankAccount(0);
        System.out.println(bacc0.toString());
        
        BankAccount bacc1 = new BankAccount(5000);
        System.out.println(bacc1.toString());
        
        CheckingAccount chacc1 = new CheckingAccount(500.0);
        System.out.println(chacc1.toString());
                          
        SavingsAccount sacc1 = new SavingsAccount(1000.0, 1.0);
        System.out.println(sacc1.toString()); 
        
        //-------------------------------------------------------
        // add your code here
       
        
        
        //CheckingAccount overrides the deposit, withdraw, and toString methods
        
        /**
         * This is legal because CheckingAccount class inherits the BankAccount class and therefore a BankAccount object can be referenced to CheckingAccount
         */
        bacc0 = chacc1; 
        
        /**
         * This used the CheckingAccount class toString() method because in the previous line(bacc0 = chacc1), we initialized bacc0 as a CheckingAccount, and 
         * therefore the toString() of that class is used over the super
         */
        System.out.println(bacc0.toString());
        
        /**
         * This is not legal because CheckingAccount class inherits the BankAccount class and an inherited class cannot be referenced to a super class.
         */
        //chacc1 = bacc1;
        
        
        BankAccount bacc2 = new CheckingAccount(200.0);
        chacc1 = (CheckingAccount) (bacc2);
        
        /**
         * charcc1 was the only legal object to invoke the deductFees() method because it was the only object of the three (bacc1, chacc1, sacc1) that was from 
         * the CheckingAccount class. The CheckingAccount class was the only class with the deductFees() method.
         */
        //bacc1.deductFees();
        chacc1.deductFees();
        //sacc1.deductFees();
        
        
        /**
         * this statement creates a runtime error when executed because when the super is removed from the deposit(amount); line, the deposit() method is in an
         * infinite loop, as the line deposit(amount) from the method deposit() continues to call itself.
         */
        chacc1.deposit(100.0);
        
        
    }
    
}
