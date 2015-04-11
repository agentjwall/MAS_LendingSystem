package mas_lendingsystem;

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import groovy.lang.Closure;
import repast.simphony.relogo.*;
import repast.simphony.relogo.builder.GeneratedByReLogoBuilder;
import repast.simphony.relogo.builder.ReLogoBuilderGeneratedFor;

@GeneratedByReLogoBuilder
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class ReLogoObserver extends BaseObserver{

	/**
	 * Makes a number of randomly oriented userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> createUserTurtles(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.crt(number,closure,"UserTurtle");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle){
				result.add((mas_lendingsystem.relogo.UserTurtle)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of randomly oriented userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> createUserTurtles(int number) {
		return createUserTurtles(number,null);
	}

	/**
	 * Makes a number of uniformly fanned userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> createOrderedUserTurtles(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.cro(number,closure,"UserTurtle");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle){
				result.add((mas_lendingsystem.relogo.UserTurtle)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of uniformly fanned userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> createOrderedUserTurtles(int number) {
		return createOrderedUserTurtles(number,null);
	}

	/**
	 * Queries if object is a userTurtle.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a userTurtle
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public boolean isUserTurtleQ(Object o){
		return (o instanceof mas_lendingsystem.relogo.UserTurtle);
	}

	/**
	 * Returns an agentset containing all userTurtles.
	 * 
	 * @return agentset of all userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtles(){
		AgentSet<mas_lendingsystem.relogo.UserTurtle> a = new AgentSet<mas_lendingsystem.relogo.UserTurtle>();
		for (Object e : this.getContext().getObjects(mas_lendingsystem.relogo.UserTurtle.class)) {
			if (e instanceof mas_lendingsystem.relogo.UserTurtle){
				a.add((mas_lendingsystem.relogo.UserTurtle)e);
			}
		}
		return a;
	}

	/**
	 * Returns the userTurtle with the given who number.
	 * 
	 * @param number
	 *            a number
	 * @return turtle number
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public mas_lendingsystem.relogo.UserTurtle userTurtle(Number number){
		Turtle turtle = Utility.turtleU(number.intValue(), this);
		if (turtle instanceof mas_lendingsystem.relogo.UserTurtle)
			return (mas_lendingsystem.relogo.UserTurtle) turtle;
		return null;
	}

	/**
	 * Returns an agentset of userTurtles on a given patch.
	 * 
	 * @param p
	 *            a patch
	 * @return agentset of userTurtles on patch p
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtlesOn(Patch p){
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<mas_lendingsystem.relogo.UserTurtle>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),this,"userTurtle")){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle)
			result.add((mas_lendingsystem.relogo.UserTurtle)t);
		}
		return result;
	}

	/**
	 * Returns an agentset of userTurtles on the same patch as a turtle.
	 * 
	 * @param t
	 *            a turtle
	 * @return agentset of userTurtles on the same patch as turtle t
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtlesOn(Turtle t){
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<mas_lendingsystem.relogo.UserTurtle>();						
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),this,"userTurtle")){
			if (tt instanceof mas_lendingsystem.relogo.UserTurtle)
			result.add((mas_lendingsystem.relogo.UserTurtle)tt);
		}
		return result;
	}

	/**
	 * Returns an agentset of userTurtles on the patches in a collection or on the patches
	 * that a collection of turtles are.
	 * 
	 * @param a
	 *            a collection
	 * @return agentset of userTurtles on the patches in collection a or on the patches
	 *         that collection a turtles are
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtlesOn(Collection c){

		if (c == null || c.isEmpty()){
			return new AgentSet<mas_lendingsystem.relogo.UserTurtle>();
		}

		Set<mas_lendingsystem.relogo.UserTurtle> total = new HashSet<mas_lendingsystem.relogo.UserTurtle>();
		if (c.iterator().next() instanceof Turtle){
			for (Object o : c){
				if (o instanceof Turtle){
					Turtle t = (Turtle) o;
					total.addAll(userTurtlesOn(t));
				}
			}
		}
		else {
			for (Object o : c){
				if (o instanceof Patch){
					Patch p = (Patch) o;
					total.addAll(userTurtlesOn(p));
				}
			}
		}
		return new AgentSet<mas_lendingsystem.relogo.UserTurtle>(total);
	}

	/**
	 * Makes a number of randomly oriented consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> createConsumers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.crt(number,closure,"Consumer");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Consumer){
				result.add((mas_lendingsystem.relogo.Consumer)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of randomly oriented consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> createConsumers(int number) {
		return createConsumers(number,null);
	}

	/**
	 * Makes a number of uniformly fanned consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> createOrderedConsumers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.cro(number,closure,"Consumer");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Consumer){
				result.add((mas_lendingsystem.relogo.Consumer)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of uniformly fanned consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> createOrderedConsumers(int number) {
		return createOrderedConsumers(number,null);
	}

	/**
	 * Queries if object is a consumer.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a consumer
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public boolean isConsumerQ(Object o){
		return (o instanceof mas_lendingsystem.relogo.Consumer);
	}

	/**
	 * Returns an agentset containing all consumers.
	 * 
	 * @return agentset of all consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumers(){
		AgentSet<mas_lendingsystem.relogo.Consumer> a = new AgentSet<mas_lendingsystem.relogo.Consumer>();
		for (Object e : this.getContext().getObjects(mas_lendingsystem.relogo.Consumer.class)) {
			if (e instanceof mas_lendingsystem.relogo.Consumer){
				a.add((mas_lendingsystem.relogo.Consumer)e);
			}
		}
		return a;
	}

	/**
	 * Returns the consumer with the given who number.
	 * 
	 * @param number
	 *            a number
	 * @return turtle number
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public mas_lendingsystem.relogo.Consumer consumer(Number number){
		Turtle turtle = Utility.turtleU(number.intValue(), this);
		if (turtle instanceof mas_lendingsystem.relogo.Consumer)
			return (mas_lendingsystem.relogo.Consumer) turtle;
		return null;
	}

	/**
	 * Returns an agentset of consumers on a given patch.
	 * 
	 * @param p
	 *            a patch
	 * @return agentset of consumers on patch p
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumersOn(Patch p){
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<mas_lendingsystem.relogo.Consumer>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),this,"consumer")){
			if (t instanceof mas_lendingsystem.relogo.Consumer)
			result.add((mas_lendingsystem.relogo.Consumer)t);
		}
		return result;
	}

	/**
	 * Returns an agentset of consumers on the same patch as a turtle.
	 * 
	 * @param t
	 *            a turtle
	 * @return agentset of consumers on the same patch as turtle t
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumersOn(Turtle t){
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<mas_lendingsystem.relogo.Consumer>();						
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),this,"consumer")){
			if (tt instanceof mas_lendingsystem.relogo.Consumer)
			result.add((mas_lendingsystem.relogo.Consumer)tt);
		}
		return result;
	}

	/**
	 * Returns an agentset of consumers on the patches in a collection or on the patches
	 * that a collection of turtles are.
	 * 
	 * @param a
	 *            a collection
	 * @return agentset of consumers on the patches in collection a or on the patches
	 *         that collection a turtles are
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumersOn(Collection c){

		if (c == null || c.isEmpty()){
			return new AgentSet<mas_lendingsystem.relogo.Consumer>();
		}

		Set<mas_lendingsystem.relogo.Consumer> total = new HashSet<mas_lendingsystem.relogo.Consumer>();
		if (c.iterator().next() instanceof Turtle){
			for (Object o : c){
				if (o instanceof Turtle){
					Turtle t = (Turtle) o;
					total.addAll(consumersOn(t));
				}
			}
		}
		else {
			for (Object o : c){
				if (o instanceof Patch){
					Patch p = (Patch) o;
					total.addAll(consumersOn(p));
				}
			}
		}
		return new AgentSet<mas_lendingsystem.relogo.Consumer>(total);
	}

	/**
	 * Makes a number of randomly oriented bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> createBankers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.crt(number,closure,"Banker");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Banker){
				result.add((mas_lendingsystem.relogo.Banker)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of randomly oriented bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> createBankers(int number) {
		return createBankers(number,null);
	}

	/**
	 * Makes a number of uniformly fanned bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> createOrderedBankers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.cro(number,closure,"Banker");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Banker){
				result.add((mas_lendingsystem.relogo.Banker)t);
			}
		} 
		return result; 
	}

	/**
	 * Makes a number of uniformly fanned bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> createOrderedBankers(int number) {
		return createOrderedBankers(number,null);
	}

	/**
	 * Queries if object is a banker.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a banker
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public boolean isBankerQ(Object o){
		return (o instanceof mas_lendingsystem.relogo.Banker);
	}

	/**
	 * Returns an agentset containing all bankers.
	 * 
	 * @return agentset of all bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankers(){
		AgentSet<mas_lendingsystem.relogo.Banker> a = new AgentSet<mas_lendingsystem.relogo.Banker>();
		for (Object e : this.getContext().getObjects(mas_lendingsystem.relogo.Banker.class)) {
			if (e instanceof mas_lendingsystem.relogo.Banker){
				a.add((mas_lendingsystem.relogo.Banker)e);
			}
		}
		return a;
	}

	/**
	 * Returns the banker with the given who number.
	 * 
	 * @param number
	 *            a number
	 * @return turtle number
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public mas_lendingsystem.relogo.Banker banker(Number number){
		Turtle turtle = Utility.turtleU(number.intValue(), this);
		if (turtle instanceof mas_lendingsystem.relogo.Banker)
			return (mas_lendingsystem.relogo.Banker) turtle;
		return null;
	}

	/**
	 * Returns an agentset of bankers on a given patch.
	 * 
	 * @param p
	 *            a patch
	 * @return agentset of bankers on patch p
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankersOn(Patch p){
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<mas_lendingsystem.relogo.Banker>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),this,"banker")){
			if (t instanceof mas_lendingsystem.relogo.Banker)
			result.add((mas_lendingsystem.relogo.Banker)t);
		}
		return result;
	}

	/**
	 * Returns an agentset of bankers on the same patch as a turtle.
	 * 
	 * @param t
	 *            a turtle
	 * @return agentset of bankers on the same patch as turtle t
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankersOn(Turtle t){
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<mas_lendingsystem.relogo.Banker>();						
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),this,"banker")){
			if (tt instanceof mas_lendingsystem.relogo.Banker)
			result.add((mas_lendingsystem.relogo.Banker)tt);
		}
		return result;
	}

	/**
	 * Returns an agentset of bankers on the patches in a collection or on the patches
	 * that a collection of turtles are.
	 * 
	 * @param a
	 *            a collection
	 * @return agentset of bankers on the patches in collection a or on the patches
	 *         that collection a turtles are
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankersOn(Collection c){

		if (c == null || c.isEmpty()){
			return new AgentSet<mas_lendingsystem.relogo.Banker>();
		}

		Set<mas_lendingsystem.relogo.Banker> total = new HashSet<mas_lendingsystem.relogo.Banker>();
		if (c.iterator().next() instanceof Turtle){
			for (Object o : c){
				if (o instanceof Turtle){
					Turtle t = (Turtle) o;
					total.addAll(bankersOn(t));
				}
			}
		}
		else {
			for (Object o : c){
				if (o instanceof Patch){
					Patch p = (Patch) o;
					total.addAll(bankersOn(p));
				}
			}
		}
		return new AgentSet<mas_lendingsystem.relogo.Banker>(total);
	}

	/**
	 * Queries if object is a userLink.
	 * 
	 * @param o
	 *            an object
	 * @return true or false based on whether the object is a userLink
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public boolean isUserLinkQ(Object o){
		return (o instanceof mas_lendingsystem.relogo.UserLink);
	}

	/**
	 * Returns an agentset containing all userLinks.
	 * 
	 * @return agentset of all userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> userLinks(){
		AgentSet<mas_lendingsystem.relogo.UserLink> a = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for (Object e : this.getContext().getObjects(mas_lendingsystem.relogo.UserLink.class)) {
			if (e instanceof mas_lendingsystem.relogo.UserLink){
				a.add((mas_lendingsystem.relogo.UserLink)e);
			}
		}
		return a;
	}

	/**
	 * Returns the userLink between two turtles.
	 * 
	 * @param oneEnd
	 *            an integer
	 * @param otherEnd
	 *            an integer
	 * @return userLink between two turtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink userLink(Number oneEnd, Number otherEnd) {
		return (mas_lendingsystem.relogo.UserLink)(this.getNetwork("UserLink").getEdge(turtle(oneEnd),turtle(otherEnd)));
	}

	/**
	 * Returns the userLink between two turtles.
	 * 
	 * @param oneEnd
	 *            a turtle
	 * @param otherEnd
	 *            a turtle
	 * @return userLink between two turtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink userLink(Turtle oneEnd, Turtle otherEnd) {
		return userLink(oneEnd.getWho(), otherEnd.getWho());
	}


}