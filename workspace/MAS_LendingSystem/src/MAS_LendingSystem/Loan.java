package MAS_LendingSystem;

public class Loan {
	final public static double interestRate = .04;
	final double initialLoanAmount; //Initial value of the loan
	final private double payment; //Amount paid every tick
	final Banker banker;
	final Consumer consumer;
	
	double principle; //Current value of the loan
	private double paymentsMade = 0; //Money being transfered from consumer to banker
	boolean defaulted = false;
	
	
	public Loan(LoanRequest req) {
		this.initialLoanAmount = this.principle = req.amount;
		this.payment = req.payment;
		this.banker = req.bank;
		this.consumer = req.requester;
	}
	
	public double getPayment() {
		if (this.principle <= 0) {
			return 0;
		} else if (this.principle < this.payment) {
			return this.principle;
		} else {
			return this.payment;
		}
		//return this.principle < this.payment ? this.principle: this.payment; 
	}
	
	public void makePayment(double payment) {
		this.paymentsMade += payment;
		this.principle -= payment;
	}
	
	public double disbursePayment() {
		double thisPayment = this.paymentsMade;
		this.paymentsMade = 0;
		return thisPayment;
	}

	public void accrueInterest () {
		this.principle = this.principle * (1 + (Loan.interestRate / 12));
	}
}
