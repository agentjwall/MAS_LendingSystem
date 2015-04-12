package MAS_LendingSystem;

import java.util.ArrayList;

public class Consumer {
	int income = 0; //Income gained per tick
	int assets = 0; //Total cash assets of the assets
	int spending = 0; //Money spent per tick
	int durables = 0; //0-1 Percent of goods bought that get added to net worth
	int desiredLoanAmount = 0; //Money wanted from loan in the current tick
	int risk = 0; //0-1 percent risk of defaulting
	int netWorth = 0; //Sum of agent's net worth
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently held by agent
	
}
