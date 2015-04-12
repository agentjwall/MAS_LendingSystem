package MAS_LendingSystem;

public class Loan {
	private double loanAmount; //Value of the entire loan
	private double principle; //Current value of the loan
	private double rate; //Interest rate per tick
	private double payment; //Amount paid every tick
	private boolean defaulted = false;
	
	public Loan(double loanAmount, double interestRate) {
		this.loanAmount = this.principle = loanAmount;
		this.rate = interestRate;
		this.payment = 0;
	}
	
	public Loan(double loanAmount, double interestRate, double paymentPerTick) {
		this.loanAmount = this.principle = loanAmount;
		this.rate = interestRate;
		this.payment = paymentPerTick;
	}
	
	public Loan(double loanAmount, double interestRate, int ticksToPayoff) {
		this.loanAmount = this.principle = loanAmount;
		this.rate = interestRate;
		this.payment = (interestRate * loanAmount) / ( 1- Math.pow(1 + interestRate, -ticksToPayoff));
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public double getPrinciple() {
		return principle;
	}

	public double getRate() {
		return rate;
	}

	public double getPayment() {
		return payment;
	}
	
	public boolean makePayment(double payment) {
		
		double principle = this.principle * (1 + rate) - payment;
		
		if (principle < 0) {
			return false;
		} else {
			this.principle = principle;
			return true;
		}
	}
	
	public void defaultOnLoan() {
		this.defaulted = true;
	}
	
	public boolean isDefaulted() {
		return this.defaulted;
	}
}
