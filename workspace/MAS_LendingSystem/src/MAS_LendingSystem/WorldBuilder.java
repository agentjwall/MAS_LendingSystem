package MAS_LendingSystem;

import cern.jet.random.Normal;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.*;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.*;
import repast.simphony.context.space.graph.*;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
	public static final String PARAMETER_NUM_YEARS = "numYears";
	
	// network parameters
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";
    public static final String PARAMETER_REWIRING_PROBABILITY = "rewiringProbability";
    public static final String PARAMETER_MEAN_DEGREE = "meanDegree";

    // consumer parameters
    public static final String PARAMETER_COST_OF_LIVING = "costOfLiving";
    public static final String PARAMETER_MAX_INCOME = "maxIncome";
    public static final String PARAMETER_MEAN_ASSETS = "meanAssets";
    public static final String PARAMETER_MEAN_INCOME = "meanForIncomeDist";
    public static final String PARAMETER_MEAN_SPENDING = "meanSpending";
    public static final String PARAMETER_CONSUMER_RISK = "meanConsumerRisk";
    public static final String PARAMETER_CONSUMER_DESIRE = "meanConsumerDesire";
    
    // banker parameters
    public static final String PARAMETER_BANKER_RISK = "meanBankerRisk";

    
	public Context<Object> build(Context<Object> context) {
		context.setId(context_id);
		 
		final Parameters parameters = RunEnvironment.getInstance().getParameters();

		final int bankerCount = ((Integer) parameters.getValue(PARAMETER_BANKER_COUNT)).intValue();
		final int consumerCount = ((Integer) parameters.getValue(PARAMETER_CONSUMER_COUNT)).intValue();
		final double rewiringProbability = ((Double) parameters.getValue(PARAMETER_REWIRING_PROBABILITY)).doubleValue(); 
		final int meanDegree = ((Integer) parameters.getValue(PARAMETER_MEAN_DEGREE)).intValue(); 
		final int numYears = ((Integer) parameters.getValue(PARAMETER_NUM_YEARS)).intValue();
		final double costOfLiving = ((Double) parameters.getValue(PARAMETER_COST_OF_LIVING)).doubleValue();
		final double maxIncome = ((Double) parameters.getValue(PARAMETER_MAX_INCOME)).doubleValue();
		final double meanAssets = ((Double) parameters.getValue(PARAMETER_MEAN_ASSETS)).doubleValue();
		final double meanIncome = ((Double) parameters.getValue(PARAMETER_MEAN_INCOME)).doubleValue();
		final double meanSpending = ((Double) parameters.getValue(PARAMETER_MEAN_SPENDING)).doubleValue();
		final double meanConsumerRisk = ((Double) parameters.getValue(PARAMETER_CONSUMER_RISK)).doubleValue();
		final double meanConsumerDesire = ((Double) parameters.getValue(PARAMETER_CONSUMER_DESIRE)).doubleValue();
		final double meanBankerRisk = ((Double) parameters.getValue(PARAMETER_BANKER_RISK)).doubleValue();
		
		 for (int i = 0; i < bankerCount; i++) {
			 double riskThreshold = getNormalDist(0, 1, meanBankerRisk);
			 double assets = getNormalDist(100000, 200000, meanAssets);
			 context.add(new Banker(assets, riskThreshold));
		 }
		 
		 for (int i = 0; i < consumerCount; i++) {
			 // Normal distribution with variable min, max, and mean used to approximate a Burr distribution
			 double income = getNormalDist(costOfLiving, maxIncome, meanIncome);
			 double spending = getNormalDist(costOfLiving, income, meanSpending);
			 double risk = getNormalDist(0, 1, meanConsumerRisk);
			 double desire = getNormalDist(0, 1, meanConsumerDesire);
			 
			 context.add(new Consumer(income, spending, risk, desire));
		 }

		// rewiringProbability = probability that a node in a clustered will be rewired to some other random node
		WattsBetaSmallWorldGenerator<Object> generator = new WattsBetaSmallWorldGenerator<Object>(
				rewiringProbability, meanDegree, false);

		RunEnvironment.getInstance().endAt(numYears * 12);
		NetworkFactoryFinder
			.createNetworkFactory(null)
			.createNetwork(jnetwork_id, context, generator, false);
	
		context.add(new ScheduleDispatcher());
		return context;
	}

	public double getNormalDist(double min, double max, double mean) {
		Normal n = RandomHelper.createNormal(mean, 1);
		double val = n.nextDouble();
		
		if (val < 0) {
			val = 0;
		} else if (val > 1){
			val = 1;
		}
		
		return val * (max - min) + min;
	}
	
	
}
