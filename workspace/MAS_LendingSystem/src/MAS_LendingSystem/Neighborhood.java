package MAS_LendingSystem;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repast.simphony.random.RandomHelper;
import repast.simphony.util.SimUtilities;
import repast.simphony.valueLayer.GridValueLayer;

public class Neighborhood extends GridValueLayer implements Iterable<Cell> {
	
	private List<Cell> cells  = new ArrayList<Cell>();

	public Neighborhood(String name, boolean dense, int[] dimensions) {
		super(name, dense, dimensions);
	}

	public Iterator<Cell> iterator() {
		return this.cells.iterator();
	}
	
	public List<AgentClass> getAgents() {
		List<AgentClass> set = new ArrayList<AgentClass>();
		for (Cell c: this) {
			if (!c.isEmpty()) {
				set.add(c.getAgent());
			}
		}
		return set;
	}
	
	public List<Consumer> getConsumers() {
		List<Consumer> set = new ArrayList<Consumer>();
		for (Cell c: this) {
			if (c.isConsumer()) {
				set.add((Consumer) c.getAgent());
			}
		}
		return set;
	}
	
	public List<Banker> getBankers() {
		List<Banker> set = new ArrayList<Banker>();
		for (Cell c: this) {
			if (c.isBanker()) {
				set.add((Banker) c.getAgent());
			}
		}
		return set;
	}
	
	public Cell getCell() {
		SimUtilities.shuffle(this.cells, RandomHelper.getUniform());
		for (Cell c: this.cells) {
			return c;
		}
		return null;
	}
	
	public Cell getEmptyCell() {
		SimUtilities.shuffle(this.cells, RandomHelper.getUniform());
		for (Cell c: this.cells) {
			if (c.isEmpty()) {
				return c;
			}
		}
		return null;
	}
	
	public int countEmptyCells() {
		int ct = 0;
		
		for (Cell c: this.cells) {
			if (c.isEmpty()) {
				ct++;
			}
		}
		return ct;
	}
	
	public boolean addCell(Cell cell) {
		if (!this.contains(cell)) {
			this.cells.add(cell);
			return true;
		}
		return false;
	}
	
	public boolean updateCell(Cell cell) {
		for (Cell c: this) {
			if (c.getCoordinates() == cell.getCoordinates()) {
				this.cells.remove(c);
				this.cells.add(cell);
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Cell cell) {
		for (Cell c: this) {
			if (c.getCoordinates() == cell.getCoordinates()) {
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
