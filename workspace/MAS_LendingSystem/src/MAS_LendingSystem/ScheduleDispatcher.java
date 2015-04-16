package MAS_LendingSystem;

import java.util.Collection;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.valueLayer.ValueLayer;

public class ScheduleDispatcher {
	
	public int prevPositiveBankAssets;
	public int prevConsumerSpending;
	public int monthsInDecline;

	/* A single step function is used for the entire world in order
	 * to facilitate synchronization of actions.
	 * Called synchronously on every tick of the simulation. 
	 */
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {
		Context<Object> context = ScheduleDispatcher.getContext();
		if (context == null) {
			return;
		}
         IndexedIterable<Object> bankers = context.getObjects(Banker.class);
         IndexedIterable<Object> consumers = context.getObjects(Consumer.class);

         for (int i = 0; i < consumers.size(); i++) {
        	 Consumer c = (Consumer) consumers.get(i);
        	 c.beforeBanker();
         }
         double assets = 0;
         for (int i = 0; i < bankers.size(); i++) {
        	 Banker b = (Banker) bankers.get(i);
        	 assets += b.getAssets();
        	b.processLoans();
         }
         System.out.println(assets);
         for (int i = 0; i < consumers.size(); i++) {
        	 Consumer c = (Consumer) consumers.get(i);
        	c.afterBanker();
         }
         
         //this.updateBackground(bankers, consumers);
         		
	}
	
	// TODO Maddy
	public void updateBackground(IndexedIterable<Object> bankers, IndexedIterable<Object> consumers) {
		Context<Object> context = ScheduleDispatcher.getContext();
		if (context == null) {
			return;
		}

		Collection<ValueLayer> layers = context.getValueLayers(); 
		
		// figure out if consumer spending has increased or decreased
		int totalConsumerSpending = 0;
		for (int i = 0; i < consumers.size(); i++) {
        	 	Consumer c = (Consumer) consumers.get(i);
        	 	totalConsumerSpending += (c.spending + c.currentSplurge);
         	}
		int percentChangeCS = (totalConsumerSpending - prevConsumerSpending)/prevConsumerSpending;
		
		
	}
	
		// returns the context if simulation is started & correctly initialized
		// otherwise returns null
		static public Context<Object> getContext() {
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
}
