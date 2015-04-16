package MAS_LendingSystem;

public class Loan {
	public static double interestRate = .04;
	
	double initialLoanAmount; //Initial value of the loan
	double principle; //Current value of the loan
	private double payment; //Amount paid every tick
	private double paymentsMade = 0; //Money being transfered from consumer to banker
	boolean defaulted = false;
	Banker banker;
	Consumer consumer;
	
	public Loan(LoanRequest req) {
		this.initialLoanAmount = this.principle = req.amount;
		this.payment = req.payment;
		this.banker = req.bank;
		this.consumer = req.requester;
	}
	
	public Loan(double loanAmount) {
		this.initialLoanAmount = this.principle = loanAmount;
		this.payment = 0;
	}
	
	public Loan(double loanAmount, double paymentPerTick) {
		this.initialLoanAmount = this.principle = loanAmount;
		this.payment = paymentPerTick;
	}
	
	public Loan(double loanAmount, int ticksToPayoff) {
		this.initialLoanAmount = this.principle = loanAmount;
		this.payment = (Loan.interestRate * loanAmount) / ( 1- Math.pow(1 + Loan.interestRate, -ticksToPayoff));
	}
	
	public double getPayment() {
		return this.principle < this.payment ? this.principle: this.payment; 
	}
	
	public void makePayment(double payment) {
		this.paymentsMade += payment;
	}
	
	public double disbursePayment() {
		double thisPayment = this.paymentsMade;
		this.paymentsMade = 0;
		return thisPayment;
	}

	public void accrueInterest () {
		this.principle = this.principle * (1 + Loan.interestRate) - this.payment;
	}
}
