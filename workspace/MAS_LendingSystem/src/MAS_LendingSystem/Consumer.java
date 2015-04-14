package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.random.RandomHelper;

// GOAL: to actually spend their desired spending amount.
public class Consumer {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int costOfLiving = 1000;  // 1000/ month = 12k annually
	static int maximumSplurge = 100;
	
	double income = 0; //Income gained per tick
	double cash = 0; //Net cash of the 
	double spending = 0; //Money spent per tick
	double valueOfDefaults = 0;
	double riskOfDefault = 0; //0-1 percent risk of defaulting
	double desire = 0; //0-1 percent deesire for more netWorth 
	double assets = 0; //cahs value of non-cash assets
	List<Double> observedSplurges = new ArrayList<Double>();
	List<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent

	//TODO: implement
	public Consumer() {
		//this.desiredSpending = Dist(min(costOfLiving, income), income); 
		//this.income = Dist(Consumer.costOfLiving, 20000)
	}
	
	private void step() {
		this.receiveIncome();
		this.spendMoney();
		this.makeLoanPayments();
		this.receiveNeighborsSplurging();
		
		if (this.doesSplurge()) {
			
			if (this.splurgeAmount() < this.cash) { //Pay for splurge purchase if possible
				
				this.cash -= this.splurgeAmount();
			
			} else {
				
				boolean success = this.requestLoan(this.desiredLoanAmount(), this.disposableIncome());
				
				if (success) {
					this.assets += this.splurgeAmount();
					this.observedSplurges = new ArrayList<Double>();
				}
			}
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
			
			if (!success) {
				this.valueOfDefaults += l.getPrinciple();
				this.updateRisk(l.getPrinciple());
			}
		}
	}
	
	//TODO: implement (true if success, false if default)
	private boolean makeLoanPayment(Loan l) {
		
		return false;
	} 
	
	private double netWorth() {
		return this.cash + this.assets;
	}
	
	private double disposableIncome() {
		return this.income - this.spending;
	}
	
	private void updateRisk(double defaultValue) {
		this.riskOfDefault = (defaultValue / this.netWorth() + 1) * this.riskOfDefault;
	}
	
	private int splurgeDesire() {
		return this.observedSplurges.size() + 1;
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
		splurgeAmount *= this.adjustedRisk(modifier) * modifier;
		
		return splurgeAmount;
	}
		
	//TODO: add in multiple loans
	private double desiredLoanAmount() {
		return this.splurgeAmount() - this.cash; //+ value needed for other loans (refinancing?)
	}  
	
	//TODO: implement
	private List<Double> receiveNeighborsSplurging() {
		// ask all neighbors what they are spending
		// return the average of that
		return new ArrayList<Double>();
	}
	//TODO: implement
	private boolean requestLoan(double desiredLoanAmount, double disposableIncome) {
		//send message to nearest non-rejected bank
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
}
