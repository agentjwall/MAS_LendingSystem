package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import repast.simphony.parameter.Parameter;
import repast.simphony.query.space.grid.*;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.*;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.Context;


public class Consumer extends AgentClass {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int maximumSplurge = 100;
	static double loanPaymentPercentage = .2; //percent of disposable income that will be payed towards loans
	
	double income = 0; //Income gained per tick
	double cash = 0; //Net cash of the 
	double spending = 0; //Money spent per tick
	double deltaNetWorth = 0;
	double currentSplurge = 0;
	double valueOfDefaults = 0;
	double risk = 0; //0-1 percent risk of defaulting
	double desire = 0; //0-1 percent desire for more netWorth 
	double assets = 0; //cash value of non-cash assets
	Banker loanPending = null;
	Boolean loanAccepted = null;
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
		this.receiveNeighborsSplurging();
		this.deltaNetWorth = this.netWorth() - netWorth;
		if (this.doesSplurge()) {
			this.currentSplurge = this.splurgeAmount();
			
			if (this.splurgeAmount() < this.cash) { //Pay for splurge purchase if possible
				
				this.cash -= this.splurgeAmount();
				System.out.println("splurge bought!");
			} else {
				
				Banker b = this.getNearestAvalibleBank();
				boolean success = this.requestLoan(b);
				
			}
		} else {
			this.currentSplurge = 0;
		}
	}
	
	public void afterBanker() {
		if (this.loanAccepted != null) {
			
			if (this.loanAccepted) {
				this.loanPending = null;
				this.loanAccepted = null;
				this.assets += this.splurgeAmount();
				this.observedSplurges = new ArrayList<Double>();
				this.rejectedBanks.clear();
			} else {
				this.rejectedBanks.add(this.loanPending);
				this.loanPending = null;
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
		for (Loan l: this.loans) {
			this.makeLoanPayment(l);  //TODO should this return whether any payments defaulted?
		}
	}
	
	private boolean makeLoanPayment(Loan l) {
		l.accrueInterest();
		
		double payment = l.getPayment();
		
		if (this.cash > payment && this.risk < RandomHelper.nextDoubleFromTo(0, 1)) {			
			this.cash -= payment;
			l.makePayment(payment);
			
			if (l.principle == 0) { //Loan is paid off
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
	

	private int splurgeDesire() {
		return this.observedSplurges.size() + 1;
	}
	
	private double splurgeThreshold() {
		return Consumer.maximumSplurge * this.desire + 1;
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
	
	private List<Double> receiveNeighborsSplurging() {
		List<Double> list = new ArrayList<Double>();
		/*
		 * TODO replace with grid
		Context<Object> context = (Context<Object>) ContextUtils.getContext(this);
		Network network = (Network) context.getProjection(WorldBuilder.jnetwork_id);
		Iterable<Object> consumers = network.getAdjacent(this);
		
		for (Object c : consumers) {
			if (c.getClass() == Consumer.class) {
				list.add(((Consumer) c).currentSplurge);
			}
		}
		*/
		return list;
	}
	
	

	private Banker getNearestAvalibleBank() {
		//List<repast.simphony.query.space.grid.GridCell<Banker>> cells = new GridCellNgh<Banker>(
        //         getGrid(), here, Banker.class, 0, 0).getNeighborhood(false);
	/*	Set<Neighborhood> neighborhoods = this.getNeighborhoods();
		List<Banker> banks = new ArrayList<Banker>();
		for (Neighborhood n: neighborhoods) {
			banks.addAll(n.getBankers());
		}
		if (banks.size() > 0) {
			SimUtilities.shuffle(banks, RandomHelper.getUniform());
			return banks.get(0);
		} else {
			Context<Object> context = getContext();
			IndexedIterable<Object> bankers = context.getObjects(Banker.class);
			
		} */
		 
//		int ct = ScheduleDispatcher.idCount();
//		System.out.println(ct);
//		boolean[] visited = new boolean[ct];
//		return this.getNearestAvalibleBank(this, visited); 

		/* 
		 * TODO replace with grid implementation
		Context<Object> context = ScheduleDispatcher.getContext();
		if (context == null) {
			return null;
		}
        Network network = (Network) context.getProjection(WorldBuilder.jnetwork_id);
        for (int dist=10; dist < 30; dist+= 10) {
        	NetPathWithin npw = new NetPathWithin(network, this, dist);
        	
        	for (Object o: npw.query()) {
        		if (o instanceof Banker) {
        			return (Banker) o;
        		}
        	}
        }
        */
        return null;
	}

	/*
	 * TODO replace with grid implementation
	private Banker getNearestAvalibleBank(Object o, boolean[] visited) {
		Banker nearestBank = null;
		
		visited[this.id] = true;
		Context<Object> context = (Context<Object>) ContextUtils.getContext(this);
		Network network = (Network) context.getProjection(WorldBuilder.jnetwork_id);
		Iterable<Object> banks = network.getAdjacent(o);
		System.out.println();
		
		for(Object b : banks) {
			if (b instanceof Banker && !this.rejectedBanks.contains((Banker) b)) {
				nearestBank = (Banker) b;
				break;
			}
		}
		
		if (nearestBank == null) {
			for(Object b : banks) {
				if (b instanceof Consumer && visited[((Consumer) b).id] != true) {
					nearestBank = this.getNearestAvalibleBank(b, visited);
					if (nearestBank != null) {
						break;
					}
				}
			}
		}
		
		return nearestBank;
	} */
	
	// Consumer requests a loan from banker
	private boolean requestLoan(Banker bank) {
		if (bank == null) {
			return false;
		}
		
		LoanRequest req = new LoanRequest(this.desiredLoanAmount(), this.desiredPaymentAmount(), this.risk, this, bank);
		bank.receiveLoanRequests(req);
		this.loanPending = bank;
		
		return true;
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
