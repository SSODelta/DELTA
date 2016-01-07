package com.delta.colours.rare;

import com.delta.colours.common.RGB;

public final class HCY extends HueColour {
	
	private static final long serialVersionUID = -2999988629541817923L;

	@Override
	public HCY toHCY(){
		return new HCY(getData());
	}
	
	public HCY(double[] vals){
		super("hcy", vals);
	}
	
	public HCY(double h, double c, double y){
		this(new double[]{h,c,y});
	}

	@Override
	public RGB toRGB() {
		
		double hh = getHue()/60,
			   c  = get('c'),
			   x  = c*(1-Math.abs((hh%2)-1));
		double r=0.0, g=0.0, b=0.0;
		
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
		
		double m = get('y') - (0.3*r + 0.59*g + 0.11*b);
		
		return new RGB(r + m, 
				       g + m, 
				       b + m);
	}

}
