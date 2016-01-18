package com.delta2.colours.colourspace;

import com.delta2.colours.util.ColourUtil;

final class CMYK implements ColourSpace {

	@Override
	public double[] fromRGB(double[] data) {
		double r = data[0],
			   g = data[1],
			   b = data[2];
			
		double k = 1-ColourUtil.max(r,g,b);
			
		double c = (1-r-k)/(1-k),
			   m = (1-g-k)/(1-k),
			   y = (1-b-k)/(1-k);
		
		return new double[]{c,m,y,k};
	}

	@Override
	public double[] toRGB(double[] data) {
		
		double negk = 1-data[3];
		
		double r = (1-data[0]) * negk;
		double g = (1-data[1]) * negk;
		double b = (1-data[2]) * negk;
		
		return new double[]{r,g,b};
	}

}
