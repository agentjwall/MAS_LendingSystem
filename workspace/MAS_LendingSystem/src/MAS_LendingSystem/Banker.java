package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Banker {
	double assets = 0; //Total assets currently held by the bank
	double defaultedAssets = 0; //Total amount of defaulted assets
	double notesReceivable = 0; //Assets currently loaned out 
	double riskThreshold = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	ArrayList<LoanRequest> loanReqs = new ArrayList<LoanRequest>();
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently loaned out 
	boolean defaulted = false;
	
	public Banker(double assets, double riskThreshold) {
		this.assets = assets;
		this.riskThreshold = riskThreshold;
	}
	
	//TODO: depricate
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {
		if (!this.defaulted) {
			this.acceptLoanRequests(loanReqs);
			this.monitorLoans();
		}
	}
	
	public void processLoans() {
		if (!this.defaulted) {
			this.acceptLoanRequests(loanReqs);
			this.monitorLoans();
		}
	}
	
	public void receiveLoanRequests(LoanRequest req) {
		this.loanReqs.add(req);
	}
	
	private void acceptLoanRequests(ArrayList<LoanRequest> reqs_arg) {
		ArrayList<LoanRequest> reqs;
		if (reqs_arg == null) {
			reqs = new ArrayList<LoanRequest>();
		} else {
			reqs = reqs_arg;
		}
		Hashtable<Double, LoanRequest> loanValues = new Hashtable<Double, LoanRequest>();
		boolean accept = true;
		do {
			LoanRequest l = null;
			Double lVal = null;
			
			for (LoanRequest req: reqs) {
				if (this.valueLoan(req) != 0 && (lVal == null || this.valueLoan(req) < lVal)) {		
					l = req;
				}
			}
			
			if (l != null && this.assets > l.amount) {
				this.loans.add(new Loan(l));
				this.assets -= l.amount;
				l.requester.loanAccepted = true;
				this.loanReqs.remove(l);
			} else {
				accept = false;
			}
			
		} while (accept);
		
		for (LoanRequest req: reqs) {
			req.requester.loanAccepted = false;
			reqs.remove(req);
		}
	}
	
	private void monitorLoans() {
		for (Loan l: this.loans) {
			this.acceptPayment(l);
			this.handleDefault(l);
		}
	}
	
	private void acceptPayment(Loan l) {
		this.assets += l.acceptPayment();
	}
	
	private void handleDefault(Loan l) {
		if (l.defaulted) {
			this.defaultedAssets += l.principle;
			this.loans.remove(l);
		}
	}
	
	private double valueLoan(LoanRequest req) {
		double riskC = req.requesterRisk;
		return riskC < this.riskThreshold? riskC * req.amount: 0;
	}
	
	public boolean getDefaulted() {
		if (this.assets < this.defaultedAssets) {
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
	
	public double getDefaultedAssets() {
		return this.defaultedAssets;
	}
	


}
