package MAS_LendingSystem;

import java.util.ArrayList;

public class Banker {
	int assets = 0; //Total assets currently held by the bank
	int defaultedAssets = 0; //Total amount of defaulted assets
	int loanedAssets = 0; //Assets currently loaned out 
	int risk = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	ArrayList<Loan> loans = new ArrayList<Loan>(); //Loans currently loaned out 
}
