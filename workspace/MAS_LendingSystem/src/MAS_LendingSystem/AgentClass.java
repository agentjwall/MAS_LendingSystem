package MAS_LendingSystem;

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
	
}
