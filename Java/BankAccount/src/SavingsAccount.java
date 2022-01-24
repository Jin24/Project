
public class SavingsAccount extends BankAccount{

	
	private double interestRate;
	
	public SavingsAccount (double initialAmount, double rate) {
		super(initialAmount);
		interestRate = rate;
	}
	
	public double getInterestRate () {
		return interestRate;
	}
	
	public void calculateInterest() {
		double interestMonth = getBalance() * (interestRate);
		deposit(interestMonth);
	}
	
	public String toString() {
		return ("SavingsAccount: balance $" + getBalance() + ", interest rate " + interestRate);
	}
}
