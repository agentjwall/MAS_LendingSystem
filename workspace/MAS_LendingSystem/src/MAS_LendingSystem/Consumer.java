package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.parameter.Parameter;
import repast.simphony.context.Context;


public class Consumer {
	static double percentDurable = .7; //0-1 Percent of goods bought that get added to net worth
	static int maximumSplurge = 100;
	static double loanPaymentPercentage = .2; //percent of disposable income that will be payed towards loans
	
	int id;
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
	
	public Consumer(int id, double income, double spending, double risk, double desire) {
		this.id = id;
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
			
			} else {
				
				Banker b = this.getNearestAvailableBank();
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
			boolean success = this.makeLoanPayment(l);
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
		
		Context<Object> context = (Context<Object>) ContextUtils.getContext(this);
		Network network = (Network) context.getProjection(WorldBuilder.jnetwork_id);
		Iterable<Object> consumers = network.getAdjacent(this);
		
		for (Object c : consumers) {
			if (c.getClass() == Consumer.class) {
				list.add(((Consumer) c).currentSplurge);
			}
		}
		
		return list;
	}
	
	
	private Banker getNearestAvailableBank() {
		Context<Object> context = ScheduleDispatcher.getContext();
		if (context == null) {
			return null;
		}
		
		IndexedIterable<Object> wbs = context.getObjects(WorldBuilder.class);
		if (wbs.size() == 0) {
			return null;
		} else {
			int ct = ((WorldBuilder) wbs.get(0)).idCt();
			return this.getNearestAvalibleBank(this, new boolean[ct]); 
		}
	}
	
	private Banker getNearestAvalibleBank(Object o, boolean[] visited) {
		Banker nearestBank = null;
		
		Context<Object> context = (Context<Object>) ContextUtils.getContext(this);
		Network network = (Network) context.getProjection(WorldBuilder.jnetwork_id);
		Iterable<Object> banks = network.getAdjacent(o);
		
		for(Object b : banks) {
			if (b.getClass() == Banker.class && !this.rejectedBanks.contains((Banker) b)) {
				nearestBank = (Banker) b;
				break;
			} else {
				visited[((Consumer) b).id] = true;
			}
		}
		
		if (nearestBank == null) {
			for(Object b : banks) {
				if (visited[((Consumer) b).id] != true) {
					nearestBank = this.getNearestAvalibleBank(b, visited);
					if (nearestBank != null) {
						break;
					}
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
