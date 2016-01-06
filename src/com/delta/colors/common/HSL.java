package com.delta.colors.common;

import com.delta.colors.obscure.*;

public final class HSL extends HueColor {

	private static final long serialVersionUID = 6958273792182812813L;

	@Override
	public HSL toHSL() {
		return new HSL(getData());
	}
	
	public HSL(double[] vals) {
		super("hsl", vals);
	}
	
	/**
	 * Constructs a new HSL Color object.
	 * @param h Hue
	 * @param s Saturation
	 * @param l Lightness
	 */
	public HSL(double h, double s, double l){
		this(new double[]{h%360,s,l});
	}

	/**
	 * Converts an HSL Color object to an RGB Color object.
	 * Uses the implementation at:
	 * 
	 * 	https://en.wikipedia.org/wiki/HSL_and_HSV#From_HSL
	 * 
	 */
	@Override
	public RGB toRGB() {
		double S  = get('s'),
			   L  = get('l'),
			   C  = (1 - Math.abs(2*L - 1)) * S,
			   hh = get('h') / 60,
			   X  = C * (1 - Math.abs((hh % 2) - 1)),
			   r = 0, g = 0, b = 0;
		
		if(0 <= hh && hh < 1){
			r = C;
			g = X;
		} else if(1 <= hh && hh < 2){
			r = X;
			g = C;
		} else if(2 <= hh && hh < 3){
			g = C;
			b = X;
		} else if(3 <= hh && hh < 4){
			g = X;
			b = C;
		} else if(4 <= hh && hh < 5){
			r = X;
			b = C;
		} else if(5 <= hh && hh < 6){
			r = C;
			b = X;
		}
		
		double m = L - 0.5*C;
		
		return new RGB(r + m,
					   g + m,
					   b + m);
	}


}
