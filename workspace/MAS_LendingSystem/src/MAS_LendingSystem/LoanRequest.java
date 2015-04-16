package MAS_LendingSystem;

public class LoanRequest {
	double amount;
	double payment;
	double requesterRisk;
	Consumer requester;
	Banker bank;
	Loan loan;
	
	public LoanRequest(double amount, double payment, double requesterRisk, Consumer requester, Banker bank) {
		this.requester = requester;
		this.bank = bank;
		this.requesterRisk = requesterRisk;
		this.bank = bank;
		this.amount = amount;
	}
}
