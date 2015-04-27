package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import repast.simphony.parameter.Parameter;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.*;
import repast.simphony.util.SimUtilities;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.Context;


public class Consumer extends AgentClass {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int maximumSplurge = 20;
	static double loanPaymentPercentage = .2; //percent of disposable income that will be paid towards loans
	
	double income = 0; //Income gained per tick
	double cash = 0; //Net cash of the 
	double spending = 0; //Money spent per tick
	double deltaNetWorth = 0;
	double currentSplurge = 19000;
	double valueOfDefaults = 0;
	double risk = 0; //0-1 percent risk of defaulting
	double desire = 0; //0-1 percent desire for more netWorth 
	double assets = 0; //cash value of non-cash assets
	
	private Banker bankPending = null;
	private Loan loanPending = null;
	private Boolean loanAccepted = false;
	
	List<Double> observedSplurges = new ArrayList<Double>();
	List<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent
	List<Banker> rejectedBanks = new ArrayList<Banker>();
	
	public Consumer(double income, double spending, double risk, double desire) {
		this.income = income;
		this.spending = spending;
		this.risk = risk;
		this.desire = desire;
	}
	
	public void beforeBanker() {
		double netWorth = this.netWorth();
		this.receiveIncome();
		this.spendMoney();
		this.makeLoanPayments();
		this.observedSplurges = this.receiveNeighborsSplurging();
		this.currentSplurge = this.nextSplurgeAmount(this.currentSplurge);
		this.deltaNetWorth = this.netWorth() - netWorth;
		if (this.doesSplurge()) {
			if (this.currentSplurge < this.cash) { //Pay for splurge purchase if possible
				
				this.cash -= this.currentSplurge;

				this.assets += this.currentSplurge;
				System.out.println("Cash Splurge: "+this.currentSplurge+", ("+this.cash+" remaining)");
			} else {
				Banker b = this.getNearestAvalibleBank();
				boolean success = this.requestLoan(b);
				
			}
		} /*else {
			this.currentSplurge = 0;
		} */
	}
	
	public void afterBanker() {
		if (this.bankPending != null) {

			if (this.loanPending != null) {
				System.out.println("I have a loan");
				this.loans.add(loanPending);
				
				// reset comm variables
				this.bankPending = null;
				this.loanAccepted = null;
				this.loanPending = null;
				this.observedSplurges = new ArrayList<Double>();
				
				// make the purchase!
				this.assets += this.currentSplurge;
				this.rejectedBanks.clear();
			} else {
				this.rejectedBanks.add(this.bankPending);
				this.bankPending = null;
				this.loanAccepted = null;
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
		if (this.loans.size() != 0) {
			
		}
		
		List<Loan> ls = new ArrayList<Loan>(this.loans);
		for (Loan l: ls) {
			System.out.println("makeLoanPayments");
			this.makeLoanPayment(l);  //TODO should this return whether any payments defaulted?
		}
		this.loans = ls;
	}
	
	private boolean makeLoanPayment(Loan l) {
		l.accrueInterest();
		
		double payment = l.getPayment();
		System.out.println(payment);
		boolean fail = this.risk > RandomHelper.nextDoubleFromTo(0, 1);
			this.cash -= payment;
			l.makePayment(payment);
		
		if (l.principle <= 0) { //Loan is paid off
				this.updateRisk(l);
				this.loans.remove(l);
				return true;
		}	
		
		if (fail) { //default 	
			l.defaulted = true;
			this.valueOfDefaults += l.principle;
			this.updateRisk(l);	
			return false;
		}
		return true;
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
	

	private int splurgeDesire() {
		return this.observedSplurges.size() + 1;
	}
	
	private double splurgeThreshold() {
		return Consumer.maximumSplurge * this.desire + 1;
	}
	
	private boolean doesSplurge() { 
	//System.out.println(this.splurgeDesire()+" / "+this.splurgeThreshold());
	if (this.splurgeThreshold() > 0 && RandomHelper.nextDoubleFromTo(0, 1) < (this.splurgeDesire() /  this.splurgeThreshold())) {
		//System.out.println("splurge");
		return true; 
	 } else {
		 return false;
	 }
	}
	
	private double nextSplurgeAmount(double prevSplurge) {
		// TODO magic number
	//	double splurgeAmount = 19000;
		double neighborsSplurge = 0;
		for (Double splurge: this.observedSplurges) {
			neighborsSplurge += splurge;
		}
		
		double modifier = RandomHelper.nextDoubleFromTo(-1, 1);
		double averageSplurge = 0;
		if (this.observedSplurges.size() != 0) {
			 averageSplurge = neighborsSplurge / this.observedSplurges.size();
		}
		
		// increase or decrease the next splurge amount by the average of neighbor's observed splurges
		return prevSplurge + averageSplurge * this.adjustedDesire(modifier); 
		// TODO magic number for testing
		//splurgeAmount += 15000;
	}
		
	private double desiredLoanAmount() {
		return this.currentSplurge - this.cash;
	}  
	
	private double desiredPaymentAmount() {
		System.out.println("payment requested: " + this.disposableIncome() * Consumer.loanPaymentPercentage);
		
		return this.disposableIncome() * Consumer.loanPaymentPercentage;
		
	}
	
	private List<Double> receiveNeighborsSplurging() {
		List<Double> splurges = new ArrayList<Double>();
		
		Set<Neighborhood> neighborhoods = this.getNeighborhoods();
		List<Consumer> consumers = new ArrayList<Consumer>();
		
		for (Neighborhood n: neighborhoods) {
			consumers.addAll(n.getConsumers());
		}

		for (Consumer c: consumers) {
			if (c != this) {
				splurges.add(c.currentSplurge);
			}
		}
		return splurges;
	}
	
	

	private Banker getNearestAvalibleBank() {
		Set<Neighborhood> neighborhoods = this.getNeighborhoods();
		List<Banker> banks = new ArrayList<Banker>();
		Banker nearestBank = null;
		for (Neighborhood n: neighborhoods) {
			banks.addAll(n.getBankers());
		}
		// if there are any banks in my neighborhood, get a random one of those
		while (banks.size() > 0 && nearestBank == null) {
			SimUtilities.shuffle(banks, RandomHelper.getUniform());
			if (!this.rejectedBanks.contains(banks.get(0))) {
				nearestBank = banks.get(0);
			} else {
				banks.remove(0);
			}
		}
		
		// none in my neighborhood that work; traverse all banks to find the nearest one
		if (nearestBank == null) {
			
			Context<Object> context = getContext();
			IndexedIterable<Object> bankers = context.getObjects(Banker.class);
			double shortestDist = Double.POSITIVE_INFINITY;
			Grid<Object> grid = getGrid();
			for (Object b: bankers) {
				Banker cast_b = (Banker) b;
				double dist = grid.getDistance(this.getGridPoint(), cast_b.getGridPoint());
				if (dist < shortestDist && !this.rejectedBanks.contains(cast_b)) {
					shortestDist = dist;
					nearestBank = cast_b;
				}
			}
		} 
     return nearestBank;
	}
	
	// Consumer requests a loan from banker
	private boolean requestLoan(Banker bank) {
		if (bank == null) {
			return false;
		}
		
		double amount = this.desiredLoanAmount();
		double payment = this.desiredPaymentAmount();
		
		LoanRequest req = new LoanRequest(amount, payment, this.risk, this, bank);
		bank.receiveLoanRequests(req);
		this.bankPending = bank;
		
		return true;
	}
	
	public void setBankPending(Banker bankPending) {
		this.bankPending = bankPending;
	}

	public void setLoanPending(Loan loanPending) {
		this.loanPending = loanPending;
	}

	public void setLoanAccepted(Boolean loanAccepted) {
		this.loanAccepted = loanAccepted;
	}

	
	private double adjustedDesire(double value) {
		if (value < 0) {
			return 1-this.desire;
		} else {
			return this.desire;
		}
	} 
	
	@Parameter(displayName = "Net Worth", usageName = "netWorth")
	public double getNetWorth() {
		return this.netWorth();
	};

	public void setNetWorth() {
		// needs to exist but not do anything (for the parameter)
	}
	
	public double economicIndicator() {
		return this.deltaNetWorth + this.spending + this.currentSplurge; 
	}
}
