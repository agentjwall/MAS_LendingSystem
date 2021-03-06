package MAS_LendingSystem;

import java.util.ArrayList;

public class Banker extends AgentClass {
	
	double assets = 0; //Total assets currently held by the bank
	double initAssets = 0; //Assets at the beginning of the experiment
	double defaultedAssets = 0; //Total amount of defaulted assets
	double notesReceivable = 0; //Assets currently loaned out 
	double riskThreshold = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	ArrayList<LoanRequest> loanReqs = new ArrayList<LoanRequest>();
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently loaned out 
	boolean defaulted = false;
	
	public Banker(double assets, double riskThreshold) {
		this.initAssets = this.assets = assets;
		this.riskThreshold = riskThreshold;
	}
	
	public void processLoans() {
		if (!this.defaulted) {
			this.acceptLoanRequests(this.loanReqs);
		}
		
		this.monitorLoans();
	}
	
	// Banker accepts communication of a loan request from a consumer agent
	public void receiveLoanRequests(LoanRequest req) {
		this.loanReqs.add(req);
	}
	
	private void acceptLoanRequests(ArrayList<LoanRequest> reqs_arg) {
		ArrayList<LoanRequest> reqs;
		if (reqs_arg == null) {
			reqs = new ArrayList<LoanRequest>();
		} else {
			reqs = new ArrayList<LoanRequest>(reqs_arg);
		}
		
		System.out.println("Loan requests received: " + reqs.size());

		boolean accept = true;
		int i=0;
		do {
			LoanRequest l = null;
			Double lVal = null;
			
			System.out.println("checking " + reqs.size() + " requests");
			for (LoanRequest req: reqs) {
				//System.out.println("Loan requested, val = " + this.valueLoan(req));
				if (this.valueLoan(req) != 0 && (lVal == null || this.valueLoan(req) > lVal)) {		
					l = req;
					lVal = this.valueLoan(req);
				}
			}
			
			
			if (l != null && this.assets > l.amount) {
				//System.out.println("Loan accepted!");
				Loan newLoan = new Loan(l);
				this.loans.add(newLoan);
				this.assets -= l.amount;
				l.requester.setLoanPending(newLoan);
				l.requester.setBankPending(this);
				reqs.remove(l);
				i++;
			} else {
				accept = false;
			}
			
		} while (accept);
		
		System.out.println(i + " Loans accepted");
		
		for (LoanRequest req: reqs) {
			req.requester.setLoanAccepted(false);
		}
		
		this.loanReqs = new ArrayList<LoanRequest>();

	}
	
	private void monitorLoans() {
		ArrayList<Loan> ls1 = new ArrayList<Loan>(this.loans);
		ArrayList<Loan> ls2 = new ArrayList<Loan>(this.loans);
		for (Loan l: ls1) {
			boolean b1 = this.acceptPayment(l);
			boolean b2 = this.handleDefault(l);
			
			if (b1 || b2) {
				ls2.remove(l);
			}
		}
		this.loans = ls2;
	}
	
	private boolean acceptPayment(Loan l) {
		this.assets += l.disbursePayment();
		
		if (l.principle <= 0) { //Loan is paid off
			return true;
		}
		return false;
	}
	
	private boolean handleDefault(Loan l) {
		if (l.defaulted && l.principle > 0) {
			System.out.println("Default: " + l.principle);
			this.defaultedAssets += l.principle;
			return true;
		}
		return false;
	}
	
	private double valueLoan(LoanRequest req) {
		double riskC = req.requesterRisk;

		//System.out.println("Risk c: " + riskC);
		//System.out.println("risk threshold: " + this.riskThreshold);
		return riskC < this.riskThreshold? riskC * req.amount: 0;
	}
	
	public boolean getDefaulted() {
		if (this.initAssets < this.defaultedAssets) {
			this.defaulted = true;
			return true;
		} else {
			this.defaulted = false;
			return false;
		}
	}
	
	public double getAssets() {
		return this.assets;
	}
	public void setAssets (double newAssets) {
		this.assets = newAssets;
	}
	
	public double getDefaultedAssets() {
		return this.defaultedAssets;
	}
	
	public double getNetAssets() {
		double netAssets = this.assets;
		ArrayList<Loan> loans = new ArrayList<Loan>(this.loans);
		
		for (Loan l: loans) {
			if (l.principle > 0) {
				netAssets += l.principle;
			}
		}
		
		return netAssets;
	}
	


}
