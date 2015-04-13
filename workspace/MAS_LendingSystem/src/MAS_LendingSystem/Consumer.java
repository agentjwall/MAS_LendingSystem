package MAS_LendingSystem;

import java.util.ArrayList;

// GOAL: to actually spend their desired spending amount.
public class Consumer {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int costOfLiving = 1000;  // 1000/ month = 12k annually 
	
	int income = 0; //Income gained per tick
	double spending; //Money spent per tick
	double desiredLoanAmount; //Money wanted from loan in the current tick
	double desiredSpending;
	int risk = 0; //0-1 percent risk of defaulting	
	double netWorth = 0; 
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent

	public Consumer() {
		//this.desiredSpending = Dist(min(costOfLiving, income), income); 
		//this.income = Dist(Consumer.costOfLiving, 20000)
	}
	
	public void step() {
		double neighborsAvg = receiveNeighborsAvgSpending(); //TODO implement communication  
		this.updateDesiredSpending(neighborsAvg);
		double moneySpent = this.spendMoney();
		this.netWorth = this.netWorth + this.income - moneySpent + (moneySpent * Consumer.percentDurable);
	}
	
	// returns amount of money spent
	private double spendMoney() {
		double actualSpent;
		if (this.desiredSpending < income) {
			actualSpent = this.desiredSpending;
		} else {
			actualSpent = Consumer.costOfLiving + ((this.income - Consumer.costOfLiving) * this.risk);
		}
		return actualSpent;
	}
	
	private void updateDesiredSpending(double neighbors_avg) {
		int r;
		if (this.desiredSpending - neighbors_avg < this.desiredSpending) {
			r = 1 - this.risk;
		} else {
			r = this.risk;
		}
		this.desiredSpending = this.desiredSpending + (r * (neighbors_avg - this.desiredSpending)); 
	}
	
	//TODO implement
	public double receiveNeighborsAvgSpending() {
		// ask all neighbors what they are spending
		// return the average of that
		return 0;
	}
	
	public double getNetWorth() {
		return this.netWorth;
	};
}
