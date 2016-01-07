package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colours.common.BlendMode;
import com.delta.colours.common.Colour;
import com.delta.colours.common.HSV;
import com.delta.colours.filters.MedianFilter;
import com.delta.colours.filters.MotionBlurFilter;
import com.delta.colours.util.Image;

public class Main {

	public static void main(String[] args) {

	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg"))),
				  f   = img.applyFilter( (x,y,raster) -> raster[x][y].toHSV().add((x+y)/20.0, 0, 0));
			
			ImageIO.write(f.getBufferedImage(), "png", new File("hue change.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
