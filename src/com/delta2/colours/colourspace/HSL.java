package com.delta2.colours.colourspace;

import com.delta2.colours.ColourUtil;

final class HSL implements ColourSpace {

	@Override
	public double[] fromRGB(double[] data) {
		double r = data[0],
			   g = data[1],
			   b = data[2];
		
		double M = ColourUtil.max(r, g, b),
			   m = ColourUtil.min(r, g, b);
		
		double C = M - m;
		
		double h = ColourUtil.getHue(r,g,b);
		
		//The Lightness L component is defined as (src: https://en.wikipedia.org/wiki/HSL_and_HSV#Hue_and_chroma)
		double L = 0.5*(M + m);
		
		//In HSL, the Saturation S is defined as (src: https://en.wikipedia.org/wiki/HSL_and_HSV#Saturation)
		
		double s = 0.0;
		
		if(C != 0.0)
			s = C / (1 - Math.abs((2*L) - 1));
		
		
		return new double[]{h,s,L};
	}

	@Override
	public double[] toRGB(double[] data) {
		double  r = 0.0,
				g = 0.0,
				b = 0.0;

		double  c = ColourUtil.bound(data[2],0,1) * ColourUtil.bound(data[1],0,1),
			   hh = (data[0] % 1) * 6.0,
			    x = c * (1 - Math.abs((hh % 2) - 1));
		
		if(0 <= hh && hh < 1){
			r = c;
			g = x;
		} else if(1 <= hh && hh < 2){
			r = x;
			g = c;
		} else if(2 <= hh && hh < 3){
			g = c;
			b = x;
		} else if(3 <= hh && hh < 4){
			g = x;
			b = c;
		} else if(4 <= hh && hh < 5){
			r = x;
			b = c;
		} else if(5 <= hh && hh < 6){
			r = c;
			b = x;
		}
		
		double m = ColourUtil.bound(data[2],0,1) - c;
		
		return new double[]{r + m, g + m, b + m};
	}

}
