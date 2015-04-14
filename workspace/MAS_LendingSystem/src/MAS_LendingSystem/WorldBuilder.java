package MAS_LendingSystem;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.random.RandomHelper;

public class WorldBuilder implements ContextBuilder<Object> {

	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("MAS_LendingSystem");
		 int bankerCount = 10;
		 for ( int i = 0; i < bankerCount ; i++) {
			 context.add(new Banker());
		 }
		 
		 int consumerCount = 100;
		 for ( int i = 0; i < consumerCount ; i++) {
			 context.add(new Consumer());
		 }
		return context;
	}

}
