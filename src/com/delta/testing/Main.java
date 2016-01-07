package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colours.common.BlendMode;
import com.delta.colours.filters.ImageFilter;
import com.delta.colours.filters.MedianFilter;
import com.delta.colours.filters.MotionBlurFilter;
import com.delta.colours.util.Animation;
import com.delta.colours.util.Image;

public class Main {

	public static void main(String[] args) {
		
	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg")));
			
			Animation a = new Animation(150, img);
			a.processAnimation(40, 
					(i,t)->
						i.applyFilter((x,y,raster)->raster[x][y].toHSV().add((x+y)/200.0+t,0,0))).export(new File("trippy fugl.gif"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
