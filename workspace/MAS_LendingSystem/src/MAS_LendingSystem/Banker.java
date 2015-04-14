package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Banker {
	double assets = 0; //Total assets currently held by the bank
	double defaultedAssets = 0; //Total amount of defaulted assets
	double notesReceivable = 0; //Assets currently loaned out 
	double riskThreshold = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently loaned out 
	boolean defaulted = false;
	
	public void step() {
		checkIfDefaulted();
		if (!this.defaulted) {
			ArrayList<LoanRequest> reqs = this.receiveLoanRequests();
			this.acceptLoanRequests(reqs);
			this.monitorLoans();
		}
	}
	
	//TODO: implement
	private ArrayList<LoanRequest> receiveLoanRequests() {
		//get all lon requests for this bank
		return null;
	}
	
	private void acceptLoanRequests(ArrayList<LoanRequest> reqs) {
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
			
			if (this.assets > l.amount) {
				this.loans.add(new Loan(l));
				this.assets -= l.amount;
				reqs.remove(l);
			} else {
				accept = false;
			}
			
		} while (accept);
	}
	
	private void monitorLoans() {
		for (Loan l: this.loans) {
			this.acceptPayment(l);
			this.handleDefault(l);
		}
	}
	
	//TODO: implement
	private void acceptPayment(Loan l) {
		
	}
	
	
	//TODO: implement
	private void handleDefault(Loan l) {
		
	}
	
	private double valueLoan(LoanRequest req) {
		double riskC = req.requesterRisk;
		return riskC < this.riskThreshold? riskC * req.amount: 0;
	}
	
	public boolean checkIfDefaulted() {
		if (assets < defaultedAssets) {
			this.defaulted = true;
			return true;
		} else {
			this.defaulted = false;
			return false;
		}
	}
	


}
