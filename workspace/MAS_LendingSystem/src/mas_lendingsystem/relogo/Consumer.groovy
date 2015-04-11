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

class Consumer extends ReLogoTurtle {
	
	def income = 0; //Income gained per tick
	def assets = 0; //Total cash assets of the assets
	def spending = 0; //Money spent per tick
	def durables = 0; //0-1 Percent of goods bought that get added to net worth
	def desiredLoanAmount = 0; //Money wanted from loan in the current tick
	def risk = 0; //0-1 percent risk of defaulting
	def netWorth = 0; //Sum of agent's net worth
	def loans = new ArrayList<Loan>(); //Loans currently held by agent
	
	
	
}
