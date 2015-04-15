package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Set;

import repast.simphony.valueLayer.GridValueLayer;

public class Cell {

	private Banker banker = null;
	private Consumer consumer = null;
	
	private Set<GridValueLayer> neighborhood = new HashSet<GridValueLayer>();
	private final int x;
	private final int y;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean setContents(Banker banker) {
		if (this.banker == null && this.consumer == null) {
			this.banker = banker;
			return true;
		}
		return false;
	}
	
	public boolean setContents(Consumer consumer) {
		if (this.banker == null && this.consumer == null) {
			this.consumer = consumer;
			return true;
		}
		return false;
	}
	
	public boolean isEmpty(){
		if (this.banker == null && this.consumer == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Class getType() {
		if (this.banker != null) {
			return Banker.class;
		} else if (this.consumer != null) {
			return Consumer.class;
		} else {
			return null;
		}
	}
	
	public Object getContents() {
		if (this.banker != null) {
			return this.banker;
		} else if (this.consumer != null) {
			return this.consumer;
		} else {
			return null;
		}
	}
	
}
