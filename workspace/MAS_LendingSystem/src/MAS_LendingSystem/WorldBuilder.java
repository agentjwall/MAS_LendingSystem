package MAS_LendingSystem;

import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;
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
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.UndirectedJungNetwork;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.space.graph.*;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
	//final GridValueLayer foodValueLayer = new GridValueLayer();
	//public static ValueLayer worldStyle =  (ValueLayer) new WorldStyle();
	
	public int monthsDeclining = 0;
	
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";
    public static final String PARAMETER_REWIRING_PROBABILITY = "rewiringProbability";
    public static final String PARAMETER_MEAN_DEGREE = "meanDegree";
    public static final String PARAMETER_NUM_YEARS = "numYears";
    
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
		
		final int costOfLiving = 1000;
		final int maxIncome = 20000;
		
		DistributionsAdapter distributionGenerator = new DistributionsAdapter(DistributionsAdapter.makeDefaultGenerator());

		 for ( int i = 0; i < bankerCount; i++) {
			 double riskThreshold = RandomHelper.nextDoubleFromTo(0, 1);
			 double assets = getNormalDist(100000, 200000);

			 context.add(new Banker(assets, riskThreshold));
		 }
		 
		 for (int i = 0; i < consumerCount; i++) {
			 double income = distributionGenerator.nextBurr2(.5,5,12);
			 double spending = getNormalDist(costOfLiving, income);
			 double risk = RandomHelper.nextDoubleFromTo(0, 1);
			 double desire = RandomHelper.nextDoubleFromTo(0, 1);
			 
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
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void updateBackground() {
		 
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
	
	
}
