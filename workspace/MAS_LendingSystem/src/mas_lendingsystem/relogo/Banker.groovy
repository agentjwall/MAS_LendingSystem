package mas_lendingsystem.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.Plural;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;
import repast.simphony.relogo.schedule.Go;
import repast.simphony.relogo.schedule.Setup;
import mas_lendingsystem.ReLogoTurtle;

class Banker extends ReLogoTurtle {
	
	def assets = 0; //Total assets currently held by the bank
	def defaultedAssets = 0; //Total amount of defaulted assets
	def loanedAssets = 0; //Assets currently loaned out 
	def risk = 0; //0-1 Soft threshold for taking on loans of equal or lower risk
	def loans = new ArrayList<Loan>(); //Loans currently loaned out 

}
