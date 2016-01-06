package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colors.filters.MedianFilter;
import com.delta.colors.util.Image;

public class Main {

	public static void main(String[] args) {

	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg"))),
				  f   = img.applyFilter(new MedianFilter(11));
			
			ImageIO.write(f.getBufferedImage(), "png", new File("median.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
