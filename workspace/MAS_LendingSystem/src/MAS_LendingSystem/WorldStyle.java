package MAS_LendingSystem;

import java.awt.Color;
import java.awt.color.*;

import repast.simphony.visualization.editedStyle.EditedEdgeStyle2D;
import repast.simphony.visualization.editedStyle.EditedValueLayerStyleData;


public class WorldStyle extends EditedValueLayerStyleData{
	private Color white = Color.WHITE;
	private Color lightGray = Color.LIGHT_GRAY;
	private Color darkGRay = Color.DARK_GRAY;
	private Color black = Color.BLACK;
	private float[] floatArray = null;
	private float[] whiteNums = white.getColorComponents(floatArray);
	private float[] lightGrayNums = lightGray.getColorComponents(floatArray);
	private float[] darkGrayNums = darkGRay.getColorComponents(floatArray);
	private float[] blackNums = black.getColorComponents(floatArray);
	private float[][] colorArray = {whiteNums,lightGrayNums, darkGrayNums, blackNums};
	private int maxStage = 4;
	/*
	public WorldStyle(){
		
	}
	*/
	
	public void changeColor(int prevStage){
		if(prevStage < maxStage){
			return;
		}
		else{
			setColor(colorArray[prevStage+1]);
		}
	}

}
