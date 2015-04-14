package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.Hashtable;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Banker {
	double assets = 0; //Total assets currently held by the bank
	double defaultedAssets = 0; //Total amount of defaulted assets
	double notesReceivable = 0; //Assets currently loaned out 
	double riskThreshold = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently loaned out 
	boolean defaulted = false;
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {
		checkIfDefaulted();
		if (!this.defaulted) {
			// reqs = receive loan requests from consumers
			// acceptLoanRequests(reqs);
		}
	}
	
	//TODO 
	private ArrayList<Loan> acceptLoanRequests(ArrayList<LoanRequest> reqs) {
		ArrayList<Loan> accept = new ArrayList<Loan>();
		Hashtable<LoanRequest, Double> loanValues = new Hashtable<LoanRequest, Double>();
		for (int i=0; i < reqs.size(); i++) {
			if (reqs.get(i).requesterRisk > this.riskThreshold) {
				reqs.remove(i);
			} else {
				LoanRequest lr = reqs.get(i);
				loanValues.put(lr, lr.amount * (1- lr.requesterRisk));
			}
		}
		
		for (int i = 0; i < reqs.size(); i++) {
			
		}
		//Loan newLoan = new Loan(reqs.get(i).amount);
		//accept.add(newLoan);
		return accept;
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
