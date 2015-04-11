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
import repast.simphony.space.SpatialException;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

@GeneratedByReLogoBuilder
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class ReLogoTurtle extends BaseTurtle{

	/**
	 * Makes a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> hatchUserTurtles(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.hatch(number,closure,"UserTurtle");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle){
				result.add((mas_lendingsystem.relogo.UserTurtle)t);
			}
		} 
		return result;
	}

	/**
	 * Makes a number of new userTurtles and then executes a set of commands on the
	 * created userTurtles.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created userTurtles
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> hatchUserTurtles(int number) {
		return hatchUserTurtles(number,null);
	}

	/**
	 * Returns an agentset of userTurtles from the patch of the caller.
	 * 
	 * @return agentset of userTurtles from the caller's patch
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtlesHere(){
	  Grid grid = getMyObserver().getGrid();
	  GridPoint gridPoint = grid.getLocation(this);
	  AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<mas_lendingsystem.relogo.UserTurtle>();
	  for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"userTurtle")){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle)
			result.add((mas_lendingsystem.relogo.UserTurtle)t);
		}
		return result;
	}

	/**
	 * Returns the agentset of userTurtles on the patch at the direction (ndx, ndy) from the
	 * caller.
	 * 
	 * @param nX
	 *            a number
	 * @param nY
	 *            a number
	 * @returns agentset of userTurtles at the direction (nX, nY) from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserTurtle")
	public AgentSet<mas_lendingsystem.relogo.UserTurtle> userTurtlesAt(Number nX, Number nY){
		double dx = nX.doubleValue();
		double dy = nY.doubleValue();
		double[] displacement = {dx,dy};

		try{
		GridPoint gridPoint = Utility.getGridPointAtDisplacement(getTurtleLocation(),displacement,getMyObserver());
		AgentSet<mas_lendingsystem.relogo.UserTurtle> result = new AgentSet<mas_lendingsystem.relogo.UserTurtle>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"userTurtle")){
			if (t instanceof mas_lendingsystem.relogo.UserTurtle)
			result.add((mas_lendingsystem.relogo.UserTurtle)t);
		}
		return result;
		}
		catch(SpatialException e){
			return new AgentSet<mas_lendingsystem.relogo.UserTurtle>();
		}
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
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),getMyObserver(),"userTurtle")){
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
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),getMyObserver(),"userTurtle")){
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
		for (Object e : this.getMyObserver().getContext().getObjects(mas_lendingsystem.relogo.UserTurtle.class)) {
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
		Turtle turtle = Utility.turtleU(number.intValue(), getMyObserver());
		if (turtle instanceof mas_lendingsystem.relogo.UserTurtle)
			return (mas_lendingsystem.relogo.UserTurtle) turtle;
		return null;
	}

	/**
	 * Makes a number of new consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> hatchConsumers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.hatch(number,closure,"Consumer");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Consumer){
				result.add((mas_lendingsystem.relogo.Consumer)t);
			}
		} 
		return result;
	}

	/**
	 * Makes a number of new consumers and then executes a set of commands on the
	 * created consumers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created consumers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> hatchConsumers(int number) {
		return hatchConsumers(number,null);
	}

	/**
	 * Returns an agentset of consumers from the patch of the caller.
	 * 
	 * @return agentset of consumers from the caller's patch
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumersHere(){
	  Grid grid = getMyObserver().getGrid();
	  GridPoint gridPoint = grid.getLocation(this);
	  AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<mas_lendingsystem.relogo.Consumer>();
	  for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"consumer")){
			if (t instanceof mas_lendingsystem.relogo.Consumer)
			result.add((mas_lendingsystem.relogo.Consumer)t);
		}
		return result;
	}

	/**
	 * Returns the agentset of consumers on the patch at the direction (ndx, ndy) from the
	 * caller.
	 * 
	 * @param nX
	 *            a number
	 * @param nY
	 *            a number
	 * @returns agentset of consumers at the direction (nX, nY) from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Consumer")
	public AgentSet<mas_lendingsystem.relogo.Consumer> consumersAt(Number nX, Number nY){
		double dx = nX.doubleValue();
		double dy = nY.doubleValue();
		double[] displacement = {dx,dy};

		try{
		GridPoint gridPoint = Utility.getGridPointAtDisplacement(getTurtleLocation(),displacement,getMyObserver());
		AgentSet<mas_lendingsystem.relogo.Consumer> result = new AgentSet<mas_lendingsystem.relogo.Consumer>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"consumer")){
			if (t instanceof mas_lendingsystem.relogo.Consumer)
			result.add((mas_lendingsystem.relogo.Consumer)t);
		}
		return result;
		}
		catch(SpatialException e){
			return new AgentSet<mas_lendingsystem.relogo.Consumer>();
		}
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
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),getMyObserver(),"consumer")){
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
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),getMyObserver(),"consumer")){
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
		for (Object e : this.getMyObserver().getContext().getObjects(mas_lendingsystem.relogo.Consumer.class)) {
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
		Turtle turtle = Utility.turtleU(number.intValue(), getMyObserver());
		if (turtle instanceof mas_lendingsystem.relogo.Consumer)
			return (mas_lendingsystem.relogo.Consumer) turtle;
		return null;
	}

	/**
	 * Makes a number of new bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> hatchBankers(int number, Closure closure) {
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<>();
		AgentSet<Turtle> createResult = this.hatch(number,closure,"Banker");
		for (Turtle t : createResult){
			if (t instanceof mas_lendingsystem.relogo.Banker){
				result.add((mas_lendingsystem.relogo.Banker)t);
			}
		} 
		return result;
	}

	/**
	 * Makes a number of new bankers and then executes a set of commands on the
	 * created bankers.
	 * 
	 * @param number
	 *            a number
	 * @param closure
	 *            a set of commands
	 * @return created bankers
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> hatchBankers(int number) {
		return hatchBankers(number,null);
	}

	/**
	 * Returns an agentset of bankers from the patch of the caller.
	 * 
	 * @return agentset of bankers from the caller's patch
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankersHere(){
	  Grid grid = getMyObserver().getGrid();
	  GridPoint gridPoint = grid.getLocation(this);
	  AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<mas_lendingsystem.relogo.Banker>();
	  for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"banker")){
			if (t instanceof mas_lendingsystem.relogo.Banker)
			result.add((mas_lendingsystem.relogo.Banker)t);
		}
		return result;
	}

	/**
	 * Returns the agentset of bankers on the patch at the direction (ndx, ndy) from the
	 * caller.
	 * 
	 * @param nX
	 *            a number
	 * @param nY
	 *            a number
	 * @returns agentset of bankers at the direction (nX, nY) from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.Banker")
	public AgentSet<mas_lendingsystem.relogo.Banker> bankersAt(Number nX, Number nY){
		double dx = nX.doubleValue();
		double dy = nY.doubleValue();
		double[] displacement = {dx,dy};

		try{
		GridPoint gridPoint = Utility.getGridPointAtDisplacement(getTurtleLocation(),displacement,getMyObserver());
		AgentSet<mas_lendingsystem.relogo.Banker> result = new AgentSet<mas_lendingsystem.relogo.Banker>();						
		for (Turtle t : Utility.getTurtlesOnGridPoint(gridPoint,getMyObserver(),"banker")){
			if (t instanceof mas_lendingsystem.relogo.Banker)
			result.add((mas_lendingsystem.relogo.Banker)t);
		}
		return result;
		}
		catch(SpatialException e){
			return new AgentSet<mas_lendingsystem.relogo.Banker>();
		}
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
		for (Turtle t : Utility.getTurtlesOnGridPoint(p.getGridLocation(),getMyObserver(),"banker")){
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
		for (Turtle tt : Utility.getTurtlesOnGridPoint(Utility.ndPointToGridPoint(t.getTurtleLocation()),getMyObserver(),"banker")){
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
		for (Object e : this.getMyObserver().getContext().getObjects(mas_lendingsystem.relogo.Banker.class)) {
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
		Turtle turtle = Utility.turtleU(number.intValue(), getMyObserver());
		if (turtle instanceof mas_lendingsystem.relogo.Banker)
			return (mas_lendingsystem.relogo.Banker) turtle;
		return null;
	}

	/**
	 * Makes a directed userLink from a turtle to the caller then executes a set of
	 * commands on the created userLink.
	 * 
	 * @param t
	 *            a turtle
	 * @param closure
	 *            a set of commands
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink createUserLinkFrom(Turtle t, Closure closure){
		mas_lendingsystem.relogo.UserLink link = (mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(t,this);
		if (closure != null){
			this.ask(link,closure);
		}
		return link;
	}

	/**
	 * Makes a directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink createUserLinkFrom(Turtle t){
			return createUserLinkFrom(t,null);
	}

	/**
	 * Makes directed userLinks from a collection to the caller then executes a set
	 * of commands on the created userLinks.
	 * 
	 * @param a
	 *            a collection
	 * @param closure
	 *            a set of commands
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> createUserLinksFrom(Collection<? extends Turtle> a, Closure closure){
		AgentSet<mas_lendingsystem.relogo.UserLink> links = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for(Turtle t : a){
			links.add((mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(t,this));
		}
		if (closure != null){
			this.ask(links,closure);
		}
		return links;
	}

	/**
	 * Makes directed userLinks from a collection to the caller.
	 * 
	 * @param a
	 *            a collection
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> createUserLinksFrom(Collection<? extends Turtle> a){
		return createUserLinksFrom(a,null);
	}

	/**
	 * Makes a directed userLink to a turtle from the caller then executes a set of
	 * commands on the created userLink.
	 * 
	 * @param t
	 *            a turtle
	 * @param closure
	 *            a set of commands
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink createUserLinkTo(Turtle t, Closure closure){
		mas_lendingsystem.relogo.UserLink link = (mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(this,t);
		if (closure != null){
			this.ask(link,closure);
		}
		return link;
	}

	/**
	 * Makes a directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return created userLink
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink createUserLinkTo(Turtle t){
			return createUserLinkTo(t,null);
	}

	/**
	 * Makes directed userLinks to a collection from the caller then executes a set
	 * of commands on the created userLinks.
	 * 
	 * @param a
	 *            a collection
	 * @param closure
	 *            a set of commands
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> createUserLinksTo(Collection<? extends Turtle> a, Closure closure){
		AgentSet<mas_lendingsystem.relogo.UserLink> links = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for(Object t : a){
			if (t instanceof Turtle){
				links.add((mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").addEdge(this,(Turtle)t));
			}
		}
		if (closure != null){
			this.ask(links,closure);
		}
		return links;
	}

	/**
	 * Makes directed userLinks to a collection from the caller.
	 * 
	 * @param a
	 *            a collection
	 * @return created userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> createUserLinksTo(Collection<? extends Turtle> a){
		return createUserLinksTo(a,null);
	}

	/**
	 * Queries if there is a directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return true or false based on whether there is a directed userLink from
	 *         turtle t to the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public boolean inUserLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isPredecessor(t, this);
	}

	/**
	 * Returns the agentset with directed userLinks to the caller.
	 * 
	 * @return agentset with directed userLinks to the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet inUserLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getPredecessors(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns the directed userLink from a turtle to the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return directed userLink from turtle t to the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink inUserLinkFrom(Turtle t){
		return (mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").getEdge(t,this);
	}

	/**
	 * Returns an agentset of directed userLinks from other turtles to the caller.
	 * 
	 * @return agentset of directed userLinks from other turtles to the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> myInUserLinks(){
		AgentSet<mas_lendingsystem.relogo.UserLink> result = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for(Object o :  this.getMyObserver().getNetwork("UserLink").getInEdges(this)){
			if (o instanceof mas_lendingsystem.relogo.UserLink){
				result.add((mas_lendingsystem.relogo.UserLink) o);
			}
		}
		return result;
	}

	/**
	 * Returns an agentset of directed userLinks to other turtles from the caller.
	 * 
	 * @return agentset of directed userLinks to other turtles from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> myOutUserLinks(){
		AgentSet<mas_lendingsystem.relogo.UserLink> result = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for(Object o :  this.getMyObserver().getNetwork("UserLink").getOutEdges(this)){
			if (o instanceof mas_lendingsystem.relogo.UserLink){
				result.add((mas_lendingsystem.relogo.UserLink) o);
			}
		}
		return result;
	}

	/**
	 * Queries if there is a directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return true or false based on whether there is a directed userLink to
	 *         turtle t from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public boolean outUserLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isPredecessor(this, t);
	}

	/**
	 * Returns the agentset with directed userLinks from the caller.
	 * 
	 * @return agentset with directed userLinks from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet outUserLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getSuccessors(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns the directed userLink to a turtle from the caller.
	 * 
	 * @param t
	 *            a turtle
	 * @return directed userLink to turtle t from the caller
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public mas_lendingsystem.relogo.UserLink outUserLinkTo(Turtle t){
		return (mas_lendingsystem.relogo.UserLink)this.getMyObserver().getNetwork("UserLink").getEdge(this, t);
	}

	/**
	 * Reports true if there is a userLink connecting t and the caller.
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public boolean userLinkNeighborQ(Turtle t){
		return this.getMyObserver().getNetwork("UserLink").isAdjacent(this, t);
	}

	/**
	 * Returns the agentset of all turtles found at the other end of
	 * userLinks connected to the calling turtle.
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet userLinkNeighbors(){
		AgentSet result = new AgentSet();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getAdjacent(this)){
			result.add(o);
		}
		return result;
	}

	/**
	 * Returns an agentset of the caller's userLinks.
	 * 
	 * @return agentset of the caller's userLinks
	 */
	@ReLogoBuilderGeneratedFor("mas_lendingsystem.relogo.UserLink")
	public AgentSet<mas_lendingsystem.relogo.UserLink> myUserLinks(){
		AgentSet<mas_lendingsystem.relogo.UserLink> result = new AgentSet<mas_lendingsystem.relogo.UserLink>();
		for(Object o : this.getMyObserver().getNetwork("UserLink").getEdges(this)){
			if (o instanceof mas_lendingsystem.relogo.UserLink){
				result.add((mas_lendingsystem.relogo.UserLink)o);
			}
		}
		return result;
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
		for (Object e : this.getMyObserver().getContext().getObjects(mas_lendingsystem.relogo.UserLink.class)) {
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
		return (mas_lendingsystem.relogo.UserLink)(this.getMyObserver().getNetwork("UserLink").getEdge(turtle(oneEnd),turtle(otherEnd)));
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