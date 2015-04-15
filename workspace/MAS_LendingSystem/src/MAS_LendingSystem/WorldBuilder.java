package MAS_LendingSystem;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.UndirectedJungNetwork;
import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.context.space.graph.NetworkFactory;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.context.space.graph.WattsBetaSmallWorldGenerator;

public class WorldBuilder implements ContextBuilder<Object> {
	public static final String jnetwork_id = "jung_network";
	public static final String context_id = "MAS_LendingSystem";
	public static final ValueLayer worldStyle =  (ValueLayer) new WorldStyle();
	public int monthsDeclining = 0;
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId(context_id);
		context.addValueLayer(worldStyle);
		
		int bankerCount = 40;
		 for (int i = 0; i < bankerCount; i++) {
			 context.add(new Banker());
		 }
		 
		 int consumerCount = 400;
		 for (int i = 0; i < consumerCount; i++) {
			 context.add(new Consumer());
		 }
	
		int watts_degree = 12;
		double rewiring_probability = .04; // probability that a node in a clustered will be rewired to some other random node
		WattsBetaSmallWorldGenerator<Object> generator = new WattsBetaSmallWorldGenerator<Object>(
				rewiring_probability, watts_degree, false);
		
		NetworkFactoryFinder
			.createNetworkFactory(null)
			.createNetwork(jnetwork_id, context, generator, false);
	
		
		
		return context;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void updateBackground() {
		 
	}


}
