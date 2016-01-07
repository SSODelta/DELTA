package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colours.common.BlendMode;
import com.delta.colours.filters.MedianFilter;
import com.delta.colours.util.Image;

public class Main {

	public static void main(String[] args) {

	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg"))),
				  f   = img.blend(img.applyFilter( new MedianFilter(13)).processImage(c->c.invertLightness()),
						  		  BlendMode.SCREEN);
			
			ImageIO.write(f.getBufferedImage(), "png", new File("lets see.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
