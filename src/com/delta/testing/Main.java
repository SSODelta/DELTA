package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colours.util.Animation;
import com.delta.colours.util.ColourUtil;
import com.delta.colours.util.Image;

public class Main {

	final static double chance = 0.2;
	
	static void nothing(){};
	
	public static void main(String[] args) {
		
		
		
	    try {
	    	
			Image img = new Image(ImageIO.read(new File("bird.jpg")));
			Animation a = new Animation(150, img);
			a.processAnimation(40, 
					(i,t)-> 
						i
						).export(
								new File("trippy bird.gif")
							);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}