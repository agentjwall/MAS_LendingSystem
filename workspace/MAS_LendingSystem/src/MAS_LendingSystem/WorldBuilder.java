package MAS_LendingSystem;

import cern.jet.random.Normal;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.UndirectedJungNetwork;
import repast.simphony.valueLayer.GridValueLayer;
import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.context.space.graph.NetworkFactory;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.context.space.graph.WattsBetaSmallWorldGenerator;
import repast.simphony.engine.environment.*;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.*;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.space.graph.*;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
	public static final String PARAMETER_NUM_YEARS = "numYears";
	
	//final GridValueLayer foodValueLayer = new GridValueLayer();
	//public static ValueLayer worldStyle =  (ValueLayer) new WorldStyle();
	
	public int monthsDeclining = 0;
	
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

    private static int uniqueId = 0;
	
	public Context<Object> build(Context<Object> context) {
		context.setId(context_id);
		//context.addProjection(grid);
		//context.addValueLayer(worldStyle);
		
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
			 context.add(new Banker(stampId(), assets, riskThreshold));
		 }
		 
		 for (int i = 0; i < consumerCount; i++) {
			 // Normal distribution with variable min, max, and mean used to approximate a Burr distribution
			 double income = getNormalDist(costOfLiving, maxIncome, meanIncome);
			 double spending = getNormalDist(costOfLiving, income, meanSpending);
			 double risk = getNormalDist(0, 1, meanConsumerRisk);
			 double desire = getNormalDist(0, 1, meanConsumerDesire);
			 
			 context.add(new Consumer(stampId(), income, spending, risk, desire));
		 }

		// rewiringProbability = probability that a node in a clustered will be rewired to some other random node
		WattsBetaSmallWorldGenerator<Object> generator = new WattsBetaSmallWorldGenerator<Object>(
				rewiringProbability, meanDegree, false);

		RunEnvironment.getInstance().endAt(numYears * 12);
		NetworkFactoryFinder
			.createNetworkFactory(null)
			.createNetwork(jnetwork_id, context, generator, false);
	
		context.add(new ScheduleDispatcher(uniqueId));
		return context;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void updateBackground() {
		 
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
	

	// returns the context if simulation is started & correctly initialized
	// otherwise returns null
	public Context<Object> getContext() {
		final RunState runState = RunState.getInstance();

        // If simulation is not yet started or initialized correctly
        if (null == runState) {
        	return null;
        }

        @SuppressWarnings("unchecked")
        final Context<Object> masterContext = runState.getMasterContext();

        // If simulation is not initialized correctly and there is no root context
        if (null == masterContext) {
        	return null;
        }
        return masterContext;
	}
	
	
	
	private static int stampId() {
		uniqueId ++;
		return uniqueId - 1;	
	}
	
}
