package com.delta2.colours;

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
	public static final double bound(double x, double min, double max){
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

}