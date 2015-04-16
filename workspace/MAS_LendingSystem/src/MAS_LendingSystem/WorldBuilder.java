package MAS_LendingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cern.jet.random.Normal;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.*;
import repast.simphony.parameter.Parameters;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.*;
import repast.simphony.space.grid.*;
import repast.simphony.util.collections.IndexedIterable;
import repast.simphony.context.space.grid.GridFactoryFinder;

public class WorldBuilder implements ContextBuilder<Object> {	
	//final GridValueLayer foodValueLayer = new GridValueLayer();
	//public static ValueLayer worldStyle =  (ValueLayer) new WorldStyle();
	
	public int monthsDeclining = 0;
	
	public static final String PARAMETER_NUM_YEARS = "numYears";
	// network parameters
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";

    // consumer parameters
    public static final String PARAMETER_COST_OF_LIVING = "costOfLiving";
    public static final String PARAMETER_MAX_INCOME = "maxIncome";
    public static final String PARAMETER_MEAN_ASSETS = "meanAssets";
    public static final String PARAMETER_MEAN_INCOME = "meanForIncomeDist";
    public static final String PARAMETER_MEAN_SPENDING = "meanSpending";
    public static final String PARAMETER_CONSUMER_RISK = "meanConsumerRisk";
    public static final String PARAMETER_CONSUMER_DESIRE = "meanConsumerDesire";

    // banker parameters
    public static final String PARAMETER_BANKER_RISK = "meanBankerRisk";
	    
    //TODO: add parameters
    public static final int neighborhoodCount = 12;
    public static final double sharedNeighborhoodProbability = 0.04;
    
	public Context<Object> build(Context<Object> context) {
		context.setId(Constants.CONTEXT_ID);
		//context.addValueLayer(worldStyle);
		
		 /* *** Get parameters *** */		
		final Parameters parameters = RunEnvironment.getInstance().getParameters();
		final int bankerCount = ((Integer) parameters.getValue(PARAMETER_BANKER_COUNT)).intValue();
		final int consumerCount = ((Integer) parameters.getValue(PARAMETER_CONSUMER_COUNT)).intValue();
		final int numYears = ((Integer) parameters.getValue(PARAMETER_NUM_YEARS)).intValue();
		final double costOfLiving = ((Double) parameters.getValue(PARAMETER_COST_OF_LIVING)).doubleValue();
		final double maxIncome = ((Double) parameters.getValue(PARAMETER_MAX_INCOME)).doubleValue();
		final double meanAssets = ((Double) parameters.getValue(PARAMETER_MEAN_ASSETS)).doubleValue();
		final double meanIncome = ((Double) parameters.getValue(PARAMETER_MEAN_INCOME)).doubleValue();
		final double meanSpending = ((Double) parameters.getValue(PARAMETER_MEAN_SPENDING)).doubleValue();
		final double meanConsumerRisk = ((Double) parameters.getValue(PARAMETER_CONSUMER_RISK)).doubleValue();
		final double meanConsumerDesire = ((Double) parameters.getValue(PARAMETER_CONSUMER_DESIRE)).doubleValue();
		final double meanBankerRisk = ((Double) parameters.getValue(PARAMETER_BANKER_RISK)).doubleValue();
		
		int consumersPerNeighborhood = consumerCount / neighborhoodCount;
		int consumersPerNeighborhoodR = consumerCount - consumersPerNeighborhood * neighborhoodCount;
		int bankersPerNeighborhood = bankerCount / neighborhoodCount;
		int bankersPerNeighborhoodR = bankerCount - bankersPerNeighborhood * neighborhoodCount;
		
		int[] gridDim = getGridDim(bankerCount, consumerCount);
		int[] neighborhoodDim = getNeighborhoodDim(bankerCount, consumerCount);
		
		/* *** Create grid *** */
		Grid<Object> grid = GridFactoryFinder.createGridFactory(null)
								.createGrid(Constants.GRID_ID, context, 
										new GridBuilderParameters<Object>(
												new StrictBorders(),				// no wrap-around on corners of grid
												new SimpleGridAdder<Object>(),		// simply adds an object to the grid w/o other action
												true, 								// each cell is multi-occupancy
												gridDim)); 							// int[] grid dimensions
		
		for (int i=0; i < neighborhoodCount; i++) {
			//TODO: does this have to be the size of the grid or neighborhood?
			Neighborhood n = new Neighborhood("neighborhood_"+i, false, gridDim); 
			
			
			for (int j=0; j < neighborhoodDim[0]; j++) {
				for (int k=0; k < neighborhoodDim[1]; k++) {
					int x = (i * neighborhoodDim[0]) % gridDim[0] + j;
					int y = ((i * neighborhoodDim[0]) / gridDim[0]) * neighborhoodDim[1] + k;
					n.addCell(new Cell(x, y, n));
				}
			}
			
			// if there are still remainder consumers, add one extra to this neighborhood
			// ensures that remainder gets evenly spaced out among neighborhoods
			int consumers = consumersPerNeighborhood;
			if (consumersPerNeighborhoodR > 0) {
				consumers++;
				consumersPerNeighborhoodR--;
			}
			
			for (int j=0; j < consumers; j++) {
				double income = getNormalDist(costOfLiving, maxIncome, meanIncome);
				double spending = getNormalDist(costOfLiving, income, meanSpending);
				double risk = getNormalDist(0, 1, meanConsumerRisk);
				double desire = getNormalDist(0, 1, meanConsumerDesire);
				Consumer c = new Consumer(income, spending, risk, desire);
				Cell cell = n.getEmptyCell();
				cell.setAgent(c);
				c.setUnderlyingCell(cell);
				context.add(c);
				int[] coords = cell.getCoordinates();
				grid.moveTo(c, coords[0], coords[1]);
			}
			
			// if there are still remainder bankers, add one extra to this neighborhood
			// ensures that remainder gets evenly spaced out among neighborhoods
			int bankers = bankersPerNeighborhood;
			if (bankersPerNeighborhoodR > 0) {
				bankers++;
				bankersPerNeighborhoodR--;
			}
			
			for (int j=0; j < bankers; j++) {
				double riskThreshold = getNormalDist(0, 1, meanBankerRisk);
				double assets = getNormalDist(100000, 200000, meanAssets);
				Banker b = new Banker(assets, riskThreshold);
				Cell  cell = n.getEmptyCell();
				cell.setAgent(b);
				b.setUnderlyingCell(cell);
				context.add(b);
				int[] coords = cell.getCoordinates();
				grid.moveTo(b, coords[0], coords[1]);
			}
			
			context.add(n);
		}
		
		//Add shared neighborhoods
		Iterable<Object> consumers = context.getObjects(Consumer.class);
		
		for (Object o: consumers) {
			Consumer c = (Consumer) o;
			if (RandomHelper.nextDoubleFromTo(0,1) < sharedNeighborhoodProbability) {
				
				Cell cell = c.getUnderlyingCell();
				Grid<Object> g = (Grid<Object>) context.getProjection(Constants.GRID_ID);
				int[] coords = cell.getCoordinates();
				List<AgentClass> adjacent = new ArrayList<AgentClass>();
				
				if (coords[0] < gridDim[0] && (AgentClass)g.getObjectAt(coords[0]+1, coords[1]) != null) {
					adjacent.add((AgentClass)g.getObjectAt(coords[0]+1, coords[1]));
				}
				if (coords[0] > 0 && (AgentClass)g.getObjectAt(coords[0]-1, coords[1]) != null) {
					adjacent.add((AgentClass)g.getObjectAt(coords[0]-1, coords[1]));
				}
				if (coords[1] < gridDim[1] && (AgentClass)g.getObjectAt(coords[0], coords[1]+1) != null) {
					adjacent.add((AgentClass)g.getObjectAt(coords[0], coords[1]+1));
				}
				if (coords[1] > 0 && (AgentClass)g.getObjectAt(coords[0], coords[1]-1) != null) {
					adjacent.add((AgentClass)g.getObjectAt(coords[0], coords[1]-1));
				}
//				Cell cell = c.getUnderlyingCell();
//				Grid<Object> g = (Grid<Object>) context.getProjection(Constants.GRID_ID);
//				List<GridCell<Cell>> adjacent = new GridCellNgh<Cell>(g, g.getLocation(cell), Cell.class,1,1).getNeighborhood(false);
//				
//				for (GridCell<Cell> adj: adjacent) {
//					adj.
//					
//					for (Cell gc: adj.) {
						
				for (AgentClass adj: adjacent) {
						
					Cell adjCell = adj.getUnderlyingCell();
					Set<Neighborhood> adjN = adjCell.getNeighborhoods();
					
					for (Neighborhood n: adjN) {
					
						if (!cell.isInNeighborhood(n)) {
							cell.addNeighborhood(n);
							System.out.println("Added neighborhood!");
						}
					}
				}
				
			}
		}
		
		RunEnvironment.getInstance().endAt(numYears * 12);
		context.add(new ScheduleDispatcher());
		return context;
	}


	public double getNormalDist(double min, double max, double mean) {
		double trueMean = min + (mean * (max-min));
		Normal n = RandomHelper.createNormal(trueMean, 1);
		double val = n.nextDouble();
		
		if (val < min) {
			val = min;
		} else if (val > max){
			val = max;
		}
		return val;
	}
	
	
	private int[] getGridDim(int bankerCount, int consumerCount) {
		int elements = neighborhoodCount;
		int[] neighborhood = getNeighborhoodDim(bankerCount, consumerCount);
		int[] grid = getDim(elements);

		int x = neighborhood[0] * grid[0];
		int y = neighborhood[1] * grid[1];
		
		return new int[]{x, y};
	}
	
	
	private int[] getNeighborhoodDim(int bankerCount, int consumerCount) {
		int elements = (bankerCount + consumerCount) / neighborhoodCount; 
		if ((bankerCount % neighborhoodCount) != 0) {
			elements++;
		}
		if ((consumerCount % neighborhoodCount) != 0) {
			elements++;
		}
		
		return getDim(elements);
	}
	
	// gets box dimension required for the provided number of elements 
	private int[] getDim(int elements) {
		int xDim = (int) Math.ceil(Math.sqrt(elements));
		int yDim = xDim; 
		while (xDim * (yDim - 1) >= elements) {
			yDim--;
		}
		return new int[]{xDim, yDim};
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
