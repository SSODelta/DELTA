package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colors.common.BlendMode;
import com.delta.colors.filters.MedianFilter;
import com.delta.colors.filters.MotionBlurFilter;
import com.delta.colors.util.Image;

public class Main {

	public static void main(String[] args) {

	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg"))),
				  f   = img.blend(img.applyFilter(new MotionBlurFilter(Math.PI/4,9)).applyFilter(new MedianFilter(11)).xor(img), BlendMode.SCREEN);
			
			ImageIO.write(f.getBufferedImage(), "png", new File("lets try2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
