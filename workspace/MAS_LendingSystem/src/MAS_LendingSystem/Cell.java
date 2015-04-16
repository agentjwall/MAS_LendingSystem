package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Set;

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
		if (this.agent == null) {
			this.agent = agent;
			return true;
		}
		return false;
	}
	
	public boolean addNeighborhood(Neighborhood n) {
		if (!this.neighborhood.contains(n)) {
			this.neighborhood.add(n);
			return true;
		}
		return false;
	}
	
	public AgentClass getAgent() {
		return this.agent;
	}
	
	public Set<Neighborhood> getNeighborhoods() {
		return this.neighborhood;
	}
	
	public int[] getCoordinates() {
		return new int[] {this.x, this.y};
	}
	
	public boolean isInNeighborhood(Neighborhood neighborhood) {
		for (Neighborhood n: this.neighborhood) {
			if (n.equals(neighborhood)) {
				return true;
			}
		}
		return false;
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
