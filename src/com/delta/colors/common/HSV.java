package com.delta.colors.common;

import com.delta.colors.obscure.HueColor;

public final class HSV extends HueColor {

	private static final long serialVersionUID = 2624579081396060353L;

	@Override
	public HSV toHSV() {
		return new HSV(getData());
	}
	
	public HSV(double[] vals) {
		super("hsv", vals);
	}
	
	/**
	 * Constructs a new HSV Color object.
	 * @param h Hue
	 * @param s Saturation
	 * @param v Value
	 */
	public HSV(double h, double s, double v){
		this(new double[]{h,s,v});
	}

	/**
	 * Converts a HSV Color object to an RGB Color object.
	 * Uses the algorithm described at:
	 * 
	 * 	https://en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB
	 * 
	 * 
	 */
	@Override
	public RGB toRGB() {
		double  r = 0.0,
				g = 0.0,
				b = 0.0;
		
		double  c = get('v') * get('s'),
			   hh = get('h')/60.0,
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
		
		double m = get('v') - c;
		
		return new RGB(r + m,
					   g + m,
					   b + m);
	}

}
