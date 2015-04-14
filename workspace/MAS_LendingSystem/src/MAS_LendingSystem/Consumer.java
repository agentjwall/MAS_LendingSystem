package MAS_LendingSystem;

import java.util.ArrayList;

import repast.simphony.engine.schedule.ScheduledMethod;

// GOAL: to actually spend their desired spending amount.
public class Consumer {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int costOfLiving = 1000;  // 1000/ month = 12k annually 
	
	int income = 0; //Income gained per tick
	double spending = 0; //Money spent per tick
	double desiredLoanAmount; //Money wanted from loan in the current tick
	int splurgeThreshold = 0;
	int splurgeDesire = 0;
	int risk = 0; //0-1 percent risk of defaulting	
	double netWorth = 0; 
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent

	public Consumer() {
		//this.desiredSpending = Dist(min(costOfLiving, income), income); 
		//this.income = Dist(Consumer.costOfLiving, 20000)
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {  
		this.netWorth = this.netWorth + this.income - this.spending + (this.spending * Consumer.percentDurable);
	}
	
	// returns amount of money spent
	
	private void updateSplurgeDesire(int neighborsSplurge) {
		
	}
	
	//TODO implement
	public double[] receiveNeighborsAvgSplurging() {
		// ask all neighbors what they are spending
		// return the average of that
		return null;
	}
	
	public double getNetWorth() {
		return this.netWorth;
	};
}
