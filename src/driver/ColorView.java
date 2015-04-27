package driver;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ColorView {
	static final int RED = 0;
	static final int BLUE = 1;
	static final int GREEN =2;
	static final int YELLOW=3;
	
	static final String redColorPath = "file:../../res/red.jpg";
	static final String blueColorPath = "file:../../res/blue.jpg";
	static final String greenColorPath = "file:../../res/green.jpg";
	static final String yellowColorPath = "file:../../res/yellow.jpg";
	
	
	public static void setColor(ImageView image, int color){
		
		if(color==RED) image.setImage(new Image(redColorPath));
		if(color==BLUE) image.setImage(new Image(blueColorPath));
		if(color==GREEN) image.setImage(new Image(greenColorPath));
		if(color==YELLOW) image.setImage(new Image(yellowColorPath));
		
		
	}
	
	public static String getColorName(int color){
		
		switch(color){
		case 0: return "RED";
		case 1: return "BLUE";
		case 2: return "GREEN";
		case 3: return "YELLOW";
		default: return null;
		}
		
	}
	
	
}
