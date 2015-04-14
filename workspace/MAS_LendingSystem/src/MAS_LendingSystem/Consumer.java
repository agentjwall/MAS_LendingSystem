package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.random.RandomHelper;


<<<<<<< HEAD
=======
import repast.simphony.engine.schedule.ScheduledMethod;

// GOAL: to actually spend their desired spending amount.
>>>>>>> master
public class Consumer {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int costOfLiving = 1000;  // 1000/ month = 12k annually
	static int maximumSplurge = 100;
	static double loanPaymentPercentage = .2; //percent of disposable income that will be payed towards loans
	
	double income = 0; //Income gained per tick
	double cash = 0; //Net cash of the 
	double spending = 0; //Money spent per tick
	double deltaNetWorth = 0;
	double splurgeValue = 0;
	double valueOfDefaults = 0;
	double risk = 0; //0-1 percent risk of defaulting
	double desire = 0; //0-1 percent deesire for more netWorth 
	double assets = 0; //cahs value of non-cash assets
	List<Double> observedSplurges = new ArrayList<Double>();
	List<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent
	List<Banker> rejectedBanks = new ArrayList<Banker>();

	//TODO: implement
	public Consumer() {
		//this.desiredSpending = Dist(min(costOfLiving, income), income); 
		//this.income = Dist(Consumer.costOfLiving, 20000);
	}
	
	private void step() {
		double netWorth = this.netWorth();
		this.receiveIncome();
		this.spendMoney();
		this.makeLoanPayments();
		this.receiveNeighborsSplurging();
		this.deltaNetWorth = this.netWorth() - netWorth;
		
		if (this.doesSplurge()) {
			this.splurgeValue = this.splurgeAmount();
			
			if (this.splurgeAmount() < this.cash) { //Pay for splurge purchase if possible
				
				this.cash -= this.splurgeAmount();
			
			} else {
				
				boolean success = this.requestLoan();
				
				if (success) {
					this.assets += this.splurgeAmount();
					this.observedSplurges = new ArrayList<Double>();
				}
			}
		} else {
			this.splurgeValue = 0;
		}
	}

	private void receiveIncome() {
		this.cash += this.income;
	}
	
	private void spendMoney() {
		this.cash -= this.spending;
		this.assets += this.spending * Consumer.percentDurable;
	}
	
	private void makeLoanPayments() {
		for (Loan l: this.loans) {
			boolean success = this.makeLoanPayment(l);
		}
	}
	
	private boolean makeLoanPayment(Loan l) {
		l.accrueInterest();
		
		double payment = l.getPayment();
		
		if (this.cash > payment && this.risk < RandomHelper.nextDoubleFromTo(0, 1)) {			
			this.cash -= payment;
			l.makePayment(payment);
			
			if (l.principle == 0) { //Loan is payed off
				this.updateRisk(l);
				this.loans.remove(l);
			}
			
			return true;
			
		} else { //default 
			
			l.defaulted = true;
			this.valueOfDefaults += l.principle;
			this.updateRisk(l);
			
			return false;
		}
	} 
	
	private double netWorth() {
		return this.cash + this.assets;
	}
	
	private double disposableIncome() {
		return this.income - this.spending;
	}
	
	private void updateRisk(Loan l) {
		if (l.principle == 0) {
			this.risk  = this.risk / (l.initialLoanAmount / this.netWorth() + 1);
		} else {
			this.risk = this.risk * (l.principle / this.netWorth() + 1);
		}
	}
	
<<<<<<< HEAD
	private int splurgeDesire() {
		return this.observedSplurges.size() + 1;
=======
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {  
		this.netWorth = this.netWorth + this.income - this.spending + (this.spending * Consumer.percentDurable);
>>>>>>> master
	}
	
	private int splurgeThreshold() {
		return (int) (Consumer.maximumSplurge * this.desire);
	}
	
	private boolean doesSplurge() {
		 if (RandomHelper.nextDoubleFromTo(0, 1) < (this.splurgeDesire() /  this.splurgeThreshold())) {
			 return true; 
		 } else {
			 return false;
		 }
	}
	
	private double splurgeAmount() {
		double splurgeAmount = 0;
		
		for (Double splurge: this.observedSplurges) {
			splurgeAmount += splurge;
		}
		
		double modifier = RandomHelper.nextDoubleFromTo(-1, 1);
		splurgeAmount /= this.observedSplurges.size();
		splurgeAmount *= this.adjustedDesire(modifier) * modifier;
		
		return splurgeAmount;
	}
		
	private double desiredLoanAmount() {
		return this.splurgeAmount() - this.cash;
	}  
	
	private double desiredPaymentAmount() {
		return this.disposableIncome() * Consumer.loanPaymentPercentage;
	}
	
	//TODO: implement
	private List<Double> receiveNeighborsSplurging() {
		// ask all neighbors what they are spending
		// return the average of that
		return new ArrayList<Double>();
	}
	
	//TODO: implement
	private Banker getNearestAvalibleBank() {
		Banker nearestBank = null;
		
		// for (Banker b : globalBanksList) {
			// if ( !this.rejectedBanks.contains(b) && (nearestBank == null || b is closer than nearestBank) {
				//nearestBanker = b;
			//}
		//}
		
		return nearestBank;
	}
	
	//TODO: implement
	private boolean requestLoan() {
		LoanRequest req = new LoanRequest(this.desiredLoanAmount(), this.desiredPaymentAmount(), this.risk, this, this.getNearestAvalibleBank());
		
		//send message to nearest non-rejected bank
		//(refinance if consumer has one or more loans)
		//wait for request
		return false;
	}
	
	private double adjustedDesire(double value) {
		if (value < 0) {
			return 1-this.desire;
		} else {
			return this.desire;
		}
	} 
	
	public double getNetWorth() {
		return this.netWorth();
	};
	
	public double economicIndicator() {
		return this.deltaNetWorth + this.spending + this.splurgeValue; 
	}
}
