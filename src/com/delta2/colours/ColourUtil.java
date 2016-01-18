package com.delta2.colours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ColourUtil {
		
	/**
	 * Calculates the hue component based on R,G,B
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static double getHue(double r, double g, double b){
		double M = ColourUtil.max(r,g,b),
			   m = ColourUtil.min(r,g,b),
			   C = M - m,
			   hh = -1; //Error value
		
		if(C==0){
			hh = 0;
		} else if(M == r){
			hh = ((g - b) / C) % 6;
		} else if (M == g){
			hh = ((b - r) / C) + 2;
		} else if (M == b){
			hh = ((r - g) / C) + 4;
		}
		
		return hh / 6;
	}
	
	public static int to8bits(double d){
		if(d<0)d=0;
		if(d>1)d=0;
		
		return (int)Math.floor(d*255);
	}
	
	public static final double ALMOST_ZERO = 0.00001;
	
	public static final double rand(){
		return Math.random()*2-1;
	}
	
	/**
	 * Checks if a given double is close to being zero.
	 * @param d
	 * @return
	 */
	public static final boolean almostZero(double d){
		return Math.abs(d) <= ALMOST_ZERO;
	}
	
	/**
	 * Restricts a double x to an interval [min, max].
	 * @param x The input parameter
	 * @param min The minimum accepted value of 'x'. 
	 * @param max The maximum accepted value of 'x'. 
	 * @return
	 */
	public static final double bound(double xx, double min, double max){
		double x = xx;
		if(x<min)return min;
		if(x>max)return max;
		return x;
	}
	
	/**
	 * Returns the minimum value of some list of doubles.
	 * @param vals
	 * @return
	 */
	public static final double min(double... vals){
		double min = Double.MAX_VALUE;
		
		for(double v : vals)
			if(v < min)
				min = v;
		
		return min;
	}
	
	/**
	 * Returns the maximum value of some list of doubles.
	 * @param vals
	 * @return
	 */
	public static final double max(double... vals){
		double max = Double.MIN_VALUE;
		
		for(double v : vals)
			if(v > max)
				max = v;
		
		return max;
	}
	
	/**
	 * Computes cosine for some angle represented in degrees [0, 360].
	 * @param deg An angle in degrees.
	 * @return 
	 */
	public static final double cos(double deg){
		return Math.cos(Math.toRadians(deg));
	}
	
	/**
	 * Computes arccosine for some cos(x). 
	 * @param deg cos(x)
	 * @return The angle represented by arccos(cos(x)) in degrees.
	 */
	public static final double acos(double deg){
		return Math.toDegrees(Math.acos(deg));
	}
	
	public static List<Colour> getMaximumContrastList(){
		List<Colour> cols = new ArrayList<Colour>();
		
		for(Colour c : KELLY_LIST_MAXIMUM_CONTRAST)
			cols.add(c);
		
		Collections.shuffle(cols);
		return cols;
	}
	
	private static final Colour[] KELLY_LIST_MAXIMUM_CONTRAST  = new Colour[] {
				new Colour(0xFFB300), //Vivid Yellow
				new Colour(0x803E75), //Strong Purple
				new Colour(0xFF6800), //Vivid Orange
				new Colour(0xA6BDD7), //Very Light Blue
				new Colour(0xC10020), //Vivid Red
				new Colour(0xCEA262), //Grayish Yellow
				new Colour(0x817066), //Medium Gray

				//The following will not be good for people with defective color vision
				new Colour(0x007D34), //Vivid Green
				new Colour(0xF6768E), //Strong Purplish Pink
				new Colour(0x00538A), //Strong Blue
				new Colour(0xFF7A5C), //Strong Yellowish Pink
				new Colour(0x53377A), //Strong Violet
				new Colour(0xFF8E00), //Vivid Orange Yellow
				new Colour(0xB32851), //Strong Purplish Red
				new Colour(0xF4C800), //Vivid Greenish Yellow
				new Colour(0x7F180D), //Strong Reddish Brown
				new Colour(0x93AA00), //Vivid Yellowish Green
				new Colour(0x593315), //Deep Yellowish Brown
				new Colour(0xF13A13), //Vivid Reddish Orange
				new Colour(0x232C16)};//Dark Olive Green;	

}