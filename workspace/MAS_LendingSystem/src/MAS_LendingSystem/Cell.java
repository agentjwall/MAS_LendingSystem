package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Set;

import repast.simphony.valueLayer.GridValueLayer;

public class Cell {

	private AgentClass agent = null;
	
	private Set<Neighborhood> neighborhood = new HashSet<Neighborhood>();
	private final int x;
	private final int y;
	
	public Cell(int x, int y, Neighborhood n) {
		this.x = x;
		this.y = y;
		this.neighborhood.add(n);
	}
	
	public Cell(int x, int y, Neighborhood n, AgentClass agent) {
		this.x = x;
		this.y = y;
		this.neighborhood.add(n);
		this.agent = agent;
	}
	
	public boolean setAgent(AgentClass agent) {
		if (this.agent != null) {
			this.agent = agent;
			return true;
		}
		return false;
	}
	
	public AgentClass getAgent() {
		return this.agent;
	}
	
	public int[] getCoordinates() {
		return new int[] {this.x, this.y};
	}
	
	public boolean isEmpty(){
		if (this.agent == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isConsumer() {
		if (this.agent instanceof Consumer) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBanker() {
		if (this.agent instanceof Banker) {
			return true;
		} else {
			return false;
		}
	}
	
}
