package MAS_LendingSystem;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;

public class BankerStyle extends DefaultStyleOGL2D {

		@Override
	    public Color getColor(final Object agent) {
		if (agent instanceof Banker) {
	        Banker banker = (Banker) agent;
	        if (banker.getDefaulted()) {
	        	return Color.RED;
	        } else {
	        	return Color.GREEN;
	        }
		} else {
			return super.getColor(agent);
		}
	}
		
		@Override
	    public float getScale(final Object agent) {
		if (agent instanceof Banker) {
	        return 2;
		} else {
			return super.getScale(agent);
		}
	}

}
