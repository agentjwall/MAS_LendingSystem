package MAS_LendingSystem;

public class LoanRequest {
	final double amount;
	final double payment;
	final double requesterRisk;
	final Consumer requester;
	final Banker bank;
	
	public LoanRequest(double amount, double payment, double requesterRisk, Consumer requester, Banker bank) {
		this.requester = requester;
		this.bank = bank;
		this.requesterRisk = requesterRisk;
		this.amount = amount;
		this.payment = payment;
	}
}
