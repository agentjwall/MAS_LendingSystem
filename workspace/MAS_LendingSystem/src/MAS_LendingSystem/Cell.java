package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Set;

import repast.simphony.valueLayer.GridValueLayer;

public class Cell {

	private AgentClass agent = null;
	
	private Set<GridValueLayer> neighborhood = new HashSet<GridValueLayer>();
	private final int x;
	private final int y;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean setAgent(AgentClass agent) {
		if (this.agent != null) {
			this.agent = agent;
			return true;
		}
		return false;
	}
	
	public Object getAgent() {
		return this.agent;
	}
	
	public boolean isEmpty(){
		if (this.agent != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Class getType() {
		if (this.agent instanceof Banker) {
			return Banker.class;
		} else if (this.agent instanceof Consumer) {
			return Consumer.class;
		} else {
			return null;
		}
	}
	
}
