package MAS_LendingSystem;

public class Loan {
	public static double interestRate = .08;
	
	double loanAmount; //Value of the entire loan
	double principle; //Current value of the loan
	private double payment; //Amount paid every tick
	boolean defaulted = false;
	Banker banker;
	Consumer consumer;
	
	public Loan(double loanAmount) {
		this.loanAmount = this.principle = loanAmount;
		this.payment = 0;
	}
	
	public Loan(double loanAmount, double paymentPerTick) {
		this.loanAmount = this.principle = loanAmount;
		this.payment = paymentPerTick;
	}
	
	public Loan(double loanAmount, int ticksToPayoff) {
		this.loanAmount = this.principle = loanAmount;
		this.payment = (this.interestRate * loanAmount) / ( 1- Math.pow(1 + this.interestRate, -ticksToPayoff));
	}
	
	public double getPayment() {
		return this.principle < this.payment ? this.principle: this.payment; 
	}
		
	public void accrueInterest () {
		this.principle = this.principle * (1 + Loan.interestRate) - this.payment;
	}
}
