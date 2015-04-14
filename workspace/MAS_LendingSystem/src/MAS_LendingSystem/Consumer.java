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
	int risk = 0; //0-1 percent risk of defaulting	
	double assets = 0;
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
		//make loan payments
		if (this.doesSplurge()) {
			
		}
	}
	
	private void receiveIncome() {
		this.cash += this.income;
	}
	
	public void spendMoney() {
		this.cash -= this.spending;
		this.assets += this.spending * Consumer.percentDurable;
	}
	
	public double netWorth() {
		return this.cash + this.assets;
	}
	
	private int splurgeDesire() {
		return this.observedSplurges.size() + 1;
	}
	
	private int splurgeThreshold() {
		return (int) Consumer.maximumSplurge * this.risk;
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
	private double desiredLoanAmount(double splurgeAmount) {
		return splurgeAmount - this.cash; //+ value needed for other loans (refinancing?)
	}  
	
	
	//TODO: implement
	private List<Double> receiveNeighborsAvgSplurging() {
		// ask all neighbors what they are spending
		// return the average of that
		return new ArrayList<Double>();
	}
	//TODO: implement
	private boolean requestLoan() {
		//send message to nearest non-rejected bank
		//wait for request
		return false;
	}
	
	private double adjustedRisk(double value) {
		if (value < 0) {
			return 1-this.risk;
		} else {
			return this.risk;
		}
	} 
	
	public double getNetWorth() {
		return this.netWorth();
	};
}
