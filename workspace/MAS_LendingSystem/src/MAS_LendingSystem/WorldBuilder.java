package MAS_LendingSystem;

import cern.jet.random.Normal;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.engine.environment.*;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.*;
import repast.simphony.space.grid.*;
import repast.simphony.context.space.grid.GridFactoryFinder;

public class WorldBuilder implements ContextBuilder<Object> {	
	//final GridValueLayer foodValueLayer = new GridValueLayer();
	//public static ValueLayer worldStyle =  (ValueLayer) new WorldStyle();
	
	public int monthsDeclining = 0;
	
	public static final String PARAMETER_NUM_YEARS = "numYears";
	// network parameters
    public static final String PARAMETER_BANKER_COUNT = "bankerCount";
    public static final String PARAMETER_CONSUMER_COUNT = "consumerCount";
    public static final String PARAMETER_REWIRING_PROBABILITY = "rewiringProbability";
    public static final String PARAMETER_MEAN_DEGREE = "meanDegree";

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
	
    private static int uniqueId = 0;
    
    public static final int neighborhoodCount = 12;
    
	public Context<Object> build(Context<Object> context) {
		context.setId(Constants.CONTEXT_ID);
		//context.addProjection(grid);
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
		// TODO remove these parameters
		final double rewiringProbability = ((Double) parameters.getValue(PARAMETER_REWIRING_PROBABILITY)).doubleValue(); 
		final int meanDegree = ((Integer) parameters.getValue(PARAMETER_MEAN_DEGREE)).intValue(); 
		
		
		int consumersPerNeighborhood = consumerCount / neighborhoodCount;
		int consumersPerNeighborhoodR = consumersPerNeighborhood * neighborhoodCount - consumerCount;
		
		int bankersPerNeighborhood = bankerCount / neighborhoodCount;
		int bankersPerNeighborhoodR = bankersPerNeighborhood * neighborhoodCount - bankerCount;
		
		int[] gridDim = getGridDim(parameters);
		int[] neighborhoodDim = getNeighborhoodDim(parameters);
		
		/* *** Create grid *** */
		Grid<Object> grid = GridFactoryFinder.createGridFactory(null)
								.createGrid(Constants.GRID_ID, context, 
										new GridBuilderParameters<Object>(
												new StrictBorders(),				// no wrap-around on corners of grid
												new SimpleGridAdder<Object>(),		// simply adds an object to the grid w/o other action
												true, 								// each cell is multi-occupancy
												gridDim)); 							// int[] grid dimensions
		
		for (int i=0; i < neighborhoodCount; i++) {
			Neighborhood n = new Neighborhood("neighborhood_"+i, false, gridDim); //TODO: does this have to be the size of the grid or neighborhood?
			
			for (int j=0; j < gridDim[0]; j++) {
				for (int k=0; k < gridDim[1]; k++) {
					int x = (i * gridDim[0]) + j % neighborhoodDim[0];
					int y = neighborhoodDim[0] / (i * gridDim[0]) + k;
					n.addCell(new Cell(x, y, n));
				}
			}
			
			if (i == neighborhoodCount - 1) {
				consumersPerNeighborhood = consumersPerNeighborhoodR;
			}
			
			for (int j=0; j < consumersPerNeighborhood; j++) {
				double income = getNormalDist(costOfLiving, maxIncome, meanIncome);
				double spending = getNormalDist(costOfLiving, income, meanSpending);
				double risk = getNormalDist(0, 1, meanConsumerRisk);
				double desire = getNormalDist(0, 1, meanConsumerDesire);
				Consumer c = new Consumer(stampId(), income, spending, risk, desire);
				Cell  cell = n.getEmptyCell();
				context.add(c);
				grid.moveTo(c, cell.getCoordinates());
			}
			
			if (i == neighborhoodCount - 1) {
				bankersPerNeighborhood = bankersPerNeighborhoodR;
			}
			
			for (int j=0; j < bankersPerNeighborhood; j++) {
				double riskThreshold = getNormalDist(0, 1, meanBankerRisk);
				double assets = getNormalDist(100000, 200000, meanAssets);
				Banker b = new Banker(stampId(), assets, riskThreshold);
				Cell  cell = n.getEmptyCell();
				context.add(b);
				grid.moveTo(b, cell.getCoordinates());
				
			}
			
			context.add(n);
		}								
		
		RunEnvironment.getInstance().endAt(numYears * 12);
		context.add(new ScheduleDispatcher(uniqueId));
		return context;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void updateBackground() {
		 
	}


	public double getNormalDist(double min, double max, double mean) {
		Normal n = RandomHelper.createNormal(mean, 1);
		double val = n.nextDouble();
		
		if (val < 0) {
			val = 0;
		} else if (val > 1){
			val = 1;
		}
		
		return val * (max - min) + min;
	}
	
	
	private int[] getGridDim(Parameters parameters) {
		int elements = neighborhoodCount;
		int[] neighborhood = getNeighborhoodDim(parameters);
		int[] grid = getDim(elements);

		int x = neighborhood[0] * grid[0];
		int y = neighborhood[1] * grid[1];
		
		return new int[]{x, y};
	}
	
	
	private int[] getNeighborhoodDim(Parameters parameters) {
		int bankers = ((Integer) parameters.getValue(PARAMETER_BANKER_COUNT)).intValue();
		int consumers = ((Integer) parameters.getValue(PARAMETER_CONSUMER_COUNT)).intValue();
		int elements = (bankers + consumers) / neighborhoodCount; 
		return getDim(elements);
	}
	
	private int[] getDim(int elements) {
		int xDim = (int) Math.ceil(Math.sqrt(elements));
		int yDim = xDim; 
			while (xDim * (yDim - 1) > elements) {
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
	
	
	
	private static int stampId() {
		uniqueId ++;
		return uniqueId - 1;	
	}
	
}
