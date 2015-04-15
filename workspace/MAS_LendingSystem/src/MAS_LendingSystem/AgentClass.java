package MAS_LendingSystem;

import java.util.HashSet;
import java.util.Set;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.space.grid.*;
import repast.simphony.util.ContextUtils;

public class AgentClass {
	public Cell getUnderlyingCell() {
        final GridPoint location = getGrid().getLocation(this);
        final Iterable<Object> objects = getGrid().getObjectsAt(
                        location.getX(), location.getY());

        for (Object object : objects) {
                if (object instanceof Cell) {
                        return (Cell) object; 
                }
        }

        return null;
	}
	
	@SuppressWarnings("unchecked")
	public Grid<Object> getGrid() {
        return (Grid<Object>) ContextUtils.getContext(this)
                        .getProjection(Constants.GRID_ID);
	}
	
	public Set<Neighborhood> getNeighborhoods() {
		Cell cell = this.getUnderlyingCell();
		return cell.getNeighborhoods();
	}
	
	public GridPoint getGridPoint() {
		Grid grid = getGrid();
		return grid.getLocation(this);
	}
	
	// returns the context if simulation is started & correctly initialized
	// otherwise returns null
	public Context<Object> getContext() {
		final RunState runState = RunState.getInstance();

        // If simulation is not yet started or initialized correctly
        if (null == runState) {
        	return null;
        }

        @SuppressWarnings("unchecked")
        final Context<Object> masterContext = runState.getMasterContext();

        // If simulation is not initialized correctly and there is no root context
        if (null == masterContext) {
        	return null;
        }
        return masterContext;
	}
	
}
