package MAS_LendingSystem;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.util.collections.IndexedIterable;

public class ScheduleDispatcher {

	/* A single step function is used for the entire world in order
	 * to facilitate synchronization of actions.
	 * Called synchronously on every tick of the simulation. 
	 */
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() {
		Context<Object> context = this.getContext();
		if (context == null) {
			return;
		}
         IndexedIterable<Object> bankers = context.getObjects(Banker.class);
         IndexedIterable<Object> consumers = context.getObjects(Consumer.class);
         
         for (int i = 0; i < consumers.size(); i++) {
        	 Consumer c = (Consumer) consumers.get(i);
        	 //c.beforeBanker();
         }
         for (int i = 0; i < bankers.size(); i++) {
        	 Banker b = (Banker) bankers.get(i);
        	// b.processLoans();
         }
         for (int i = 0; i < consumers.size(); i++) {
        	 Consumer c = (Consumer) consumers.get(i);
        	// c.afterBanker();
         }
         
         this.updateBackground(bankers, consumers);
         		
	}
	
	// TODO Maddy
	public void updateBackground(IndexedIterable<Object> bankers, IndexedIterable<Object> consumers) {
		// figure out if consumer spending has increased or decreased
		// see pseudocode on the whiteboard
		
		/*
		 *   for (int i = 0; i < consumers.size(); i++) {
        	 	Consumer c = (Consumer) consumers.get(i);
        	 	// call whatever you need on consumer to figure out if economy is in decline
         	}
		 */
		
		// get rid of the bankers parameter if you don't need it
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
