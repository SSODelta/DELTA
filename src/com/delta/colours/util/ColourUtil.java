package com.delta.colours.util;

public class ColourUtil {
		
	public static final double ALMOST_ZERO = 0.00001;
	
	/**
	 * Checks if a given double is close to being zero.
	 * @param d
	 * @return
	 */
	public static boolean almostZero(double d){
		return Math.abs(d) <= ALMOST_ZERO;
	}
	
	/**
	 * Restricts a double x to an interval [min, max].
	 * @param x The input parameter
	 * @param min The minimum accepted value of 'x'. 
	 * @param max The maximum accepted value of 'x'. 
	 * @return
	 */
	public static double bound(double x, double min, double max){
		if(x<min)return min;
		if(x>max)return max;
		return x;
	}
	
	/**
	 * Returns the minimum value of some list of doubles.
	 * @param vals
	 * @return
	 */
	public static double min(double... vals){
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
	public static double max(double... vals){
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
	public static double cos(double deg){
		return Math.cos(Math.toRadians(deg));
	}
	
	/**
	 * Computes arccosine for some cos(x). 
	 * @param deg cos(x)
	 * @return The angle represented by arccos(cos(x)) in degrees.
	 */
	public static double acos(double deg){
		return Math.toDegrees(Math.acos(deg));
	}

}