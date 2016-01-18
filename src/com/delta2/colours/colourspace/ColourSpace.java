package com.delta2.colours.colourspace;

public interface ColourSpace {
	
	public double[] fromRGB(double[] data);
	public double[] toRGB(double[] data);
	
	public static final ColourSpace HSL  = new HSL(),
							        HSV  = new HSV(),
							        RGB  = new RGB(),
							        CMYK = new CMYK();
} 
