package com.delta.colours.rare;

/**
 * Represents any Color object where the first dimension is a Hue, given in the interval [0, 1].
 * The Hue will be mapped to the interval [0,360] wrapping around as needed, so 361 -> 1, and -1 -> 359.
 * @author ssodelta
 */
public abstract class HueColour extends DefaultColour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6401813606726957419L;

	protected HueColour(String labels, double[] vals) {
		super(labels, wrapHue(vals));
	}
	
	private static double[] wrapHue(double[] vals){
		double[] newVals = new double[vals.length];
		
		newVals[0] = vals[0];
		while(newVals[0] < 0)
			newVals[0]+=1;
		
		newVals[0] %= 1;
		
		for(int i=1; i<vals.length; i++)
			newVals[i] = vals[i];
		
		return newVals;
	}
	
	public double getHue(){
		return get('h')*360;
	}
}