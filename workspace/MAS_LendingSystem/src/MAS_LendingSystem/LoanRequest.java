package MAS_LendingSystem;

public class LoanRequest {
	double amount;
	double payment;
	double requesterRisk;
	Consumer requester;
	Banker bank;
	
	public LoanRequest(double amount, double payment, double requesterRisk, Consumer requester, Banker bank) {
		this.requester = requester;
		this.bank = bank;

	}
}
