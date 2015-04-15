package MAS_LendingSystem;

import java.awt.Color;
import java.awt.color.*;



import repast.simphony.visualization.editedStyle.EditedValueLayerStyleData;


public class WorldStyle extends EditedValueLayerStyleData{
	
	public WorldStyle(){
		Color white = Color.WHITE;
		Color lightGray = Color.LIGHT_GRAY;
		Color darkGRay = Color.DARK_GRAY;
		Color black = Color.BLACK;
		float[] floatArray = null;
		float[] whiteNums = white.getColorComponents(floatArray);
		float[] lightGrayNums = lightGray.getColorComponents(floatArray);
		float[] darkGrayNums = darkGRay.getColorComponents(floatArray);
		float[] blackNums = black.getColorComponents(floatArray);
		setColor(whiteNums);
		setColor(lightGrayNums);
		setColor(darkGrayNums);
		setColor(blackNums);
	}
	
	//public void darkens()

}
