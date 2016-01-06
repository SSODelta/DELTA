package com.delta.colors.obscure;

/**
 * Represents any Color object where the first dimension is a Hue, given in degrees.
 * The Hue will be mapped to the interval [0,360] wrapping around as needed, so 361 -> 1, and -1 -> 359.
 * @author ssodelta
 */
public abstract class HueColor extends DefaultColor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6401813606726957419L;

	protected HueColor(String labels, double[] vals) {
		super(labels, wrapHue(vals));
	}
	
	private static double[] wrapHue(double[] vals){
		double[] newVals = new double[vals.length];
		
		newVals[0] = vals[0];
		while(newVals[0] < 0)
			newVals[0]+=360;
		
		newVals[0] %= 360;
		
		for(int i=1; i<vals.length; i++)
			newVals[i] = vals[i];
		
		return newVals;
	}
}