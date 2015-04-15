package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import repast.simphony.random.RandomHelper;
import repast.simphony.valueLayer.GridValueLayer;

public class Neighborhood extends GridValueLayer implements Iterable<Cell> {
	
	private Set<Cell> cells  = new HashSet<Cell>();

	public Neighborhood(String name, boolean dense, int[] dimensions) {
		super(name, dense, dimensions);
	}

	public Iterator<Cell> iterator() {
		return this.cells.iterator();
	}
	
	public Set<AgentClass> getAgents() {
		Set<AgentClass> set = new HashSet<AgentClass>();
		for (Cell c: this) {
			if (!c.isEmpty()) {
				set.add(c.getAgent());
			}
		}
		return set;
	}
	
	public Set<Consumer> getConsumers() {
		Set<Consumer> set = new HashSet<Consumer>();
		for (Cell c: this) {
			if (c.isConsumer()) {
				set.add((Consumer) c.getAgent());
			}
		}
		return set;
	}
	
	public Set<Banker> getBankers() {
		Set<Banker> set = new HashSet<Banker>();
		for (Cell c: this) {
			if (c.isBanker()) {
				set.add((Banker) c.getAgent());
			}
		}
		return set;
	}
	
	public Cell getCell() {
		int r = RandomHelper.nextIntFromTo(0, this.cells.size());
		
		for (Cell c: this.cells) {
			if (r >= 0) {
				return c;
			}
			r--;
		}
		return null;
	}
	
	public Cell getEmptyCell() {
		int r = RandomHelper.nextIntFromTo(0, this.cells.size());
		
		Cell empty = null;
		
		for (Cell c: this.cells) {
			if (c.isEmpty()) {
				empty = c;
			}
			if (r >= 0 && empty != null) {
				return c;
			}
			r--;
		}
		return null;
	}
	
	public void addCell(Cell cell) {
		this.cells.add(cell);
	}
	
	public boolean contains(Cell cell) {
		for (Cell c: this) {
			if (c.equals(cell)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(AgentClass agent) {
		for (Cell c: this) {
			if (c.getAgent().equals(agent)) {
				return true;
			}
		}
		return false;
	}

}
