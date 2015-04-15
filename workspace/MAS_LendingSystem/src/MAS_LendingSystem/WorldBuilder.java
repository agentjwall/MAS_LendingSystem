package MAS_LendingSystem;

import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.*;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.*;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.UndirectedJungNetwork;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.space.graph.*;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";
    public static final String PARAMETER_REWIRING_PROBABILITY = "rewiringProbability";
    public static final String PARAMETER_MEAN_DEGREE = "meanDegree";
    public static final String PARAMETER_NUM_YEARS = "numYears";
    
	
	public Context<Object> build(Context<Object> context) {
		context.setId(context_id);
		 
		final Parameters parameters = RunEnvironment.getInstance().getParameters();

		final int bankerCount = ((Integer) parameters.getValue(PARAMETER_BANKER_COUNT)).intValue();
		final int consumerCount = ((Integer) parameters.getValue(PARAMETER_CONSUMER_COUNT)).intValue();
		final double rewiringProbability = ((Double) parameters.getValue(PARAMETER_REWIRING_PROBABILITY)).doubleValue(); 
		final int meanDegree = ((Integer) parameters.getValue(PARAMETER_MEAN_DEGREE)).intValue(); 
		final int numYears = ((Integer) parameters.getValue(PARAMETER_NUM_YEARS)).intValue();
		
		
		DistributionsAdapter distributionGenerator = new DistributionsAdapter(DistributionsAdapter.makeDefaultGenerator());

		 for ( int i = 0; i < bankerCount; i++) {
			 double riskThreshold = RandomHelper.nextDoubleFromTo(0, 1);
			 double assets = getNormalDist(100000, 200000);

			 context.add(new Banker(assets, riskThreshold));
		 }
		 
		 for (int i = 0; i < consumerCount; i++) {
			 double income = distributionGenerator.nextBurr2(.5,5,12);
			 context.add(new Consumer());
		 }

		// rewiringProbability = probability that a node in a clustered will be rewired to some other random node
		WattsBetaSmallWorldGenerator<Object> generator = new WattsBetaSmallWorldGenerator<Object>(
				rewiringProbability, meanDegree, false);

		RunEnvironment.getInstance().endAt(numYears * 12);
		
		NetworkFactoryFinder
			.createNetworkFactory(null)
			.createNetwork(jnetwork_id, context, generator, false);
	
		return context;
	}

	public double getNormalDist(double min, double max) {
		Normal n = RandomHelper.createNormal(0.5, 1);
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

        // If simulation is not initialized correctly and there is no root
        // context
        if (null == masterContext) {
        	return null;
        }
        return masterContext;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {
		Context<Object> context = this.getContext();
		if (context == null) {
			return;
		}
		
	}
	
}
