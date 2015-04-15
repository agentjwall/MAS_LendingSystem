package MAS_LendingSystem;

import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.DistributionsAdapter;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.UndirectedJungNetwork;
import repast.simphony.context.space.graph.NetworkFactory;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.context.space.graph.WattsBetaSmallWorldGenerator;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";
    public static final String PARAMETER_REWIRING_PROBABILITY = "rewiringProbability";
    public static final String PARAMETER_MEAN_DEGREE = "meanDegree";

    
	
	public Context<Object> build(Context<Object> context) {
		context.setId(context_id);
		 
		final Parameters parameters = RunEnvironment.getInstance().getParameters();

		final int bankerCount = ((Integer) parameters.getValue(PARAMETER_BANKER_COUNT)).intValue();
		final int consumerCount = ((Integer) parameters.getValue(PARAMETER_CONSUMER_COUNT)).intValue();
		final double rewiringProbability = ((Double) parameters.getValue(PARAMETER_REWIRING_PROBABILITY)).doubleValue(); 
		final int meanDegree = ((Integer) parameters.getValue(PARAMETER_MEAN_DEGREE)).intValue(); 

		 DistributionsAdapter distributionGenerator = new DistributionsAdapter(DistributionsAdapter.makeDefaultGenerator());
		 Normal n = RandomHelper.createNormal(0.5, 1);
		 
		 for ( int i = 0; i < bankerCount; i++) {
			 double riskThreshold = RandomHelper.nextDoubleFromTo(0, 1);
			 double assets = n.nextDouble();
			 
			 
			 
			 context.add(new Banker(assets, riskThreshold));
		 }
		 
		 for (int i = 0; i < consumerCount; i++) {
			 double income = distributionGenerator.nextBurr2(.5,5,12);
			 
			 context.add(new Consumer());
		 }

		// rewiringProbability = probability that a node in a clustered will be rewired to some other random node
		WattsBetaSmallWorldGenerator<Object> generator = new WattsBetaSmallWorldGenerator<Object>(
				rewiringProbability, meanDegree, false);
		
		NetworkFactoryFinder
			.createNetworkFactory(null)
			.createNetwork(jnetwork_id, context, generator, false);
	
		return context;
	}

}
