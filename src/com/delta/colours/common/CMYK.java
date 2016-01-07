package com.delta.colours.common;

import com.delta.colours.rare.DefaultColour;

public final class CMYK extends DefaultColour {

	private static final long serialVersionUID = -8275148542153552423L;

	@Override
	public CMYK toCMYK(){
		return new CMYK(getData());
	}
	
	public CMYK(double[] vals) {
		super("cmyk", vals);
	}
	
	/**
	 * Constructs a new CMYK Color object.
	 * @param c Cyan
	 * @param m Magenta
	 * @param y Yellow
	 * @param k Key
	 */
	public CMYK(double c, double m, double y, double k){
		this(new double[]{c,m,y,k});
	}

	/**
	 * Converts this CMYK Color object into an RGB Color object
	 * Uses the formulae at this webpage:
	 * 
	 * 	http://www.rapidtables.com/convert/color/cmyk-to-rgb.htm
	 */
	@Override
	public RGB toRGB() {
		double r = (1-get('c')) * (1 - get('k'));
		double g = (1-get('m')) * (1 - get('k'));
		double b = (1-get('y')) * (1 - get('k'));
		
		return new RGB(r,g,b);
	}

}
