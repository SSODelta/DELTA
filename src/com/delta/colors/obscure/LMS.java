package com.delta.colors.obscure;

import com.delta.colors.common.RGB;

public final class LMS extends DefaultColor {

	private static final long serialVersionUID = 7996571440309394710L;

	@Override
	public LMS toLMS(){
		return new LMS(getData());
	}
	
	public LMS(double[] vals) {
		super("lms", vals);
	}
	
	public LMS(double l, double m, double s){
		this(new double[]{l,m,s});
	}
	
	@Override
	public LMS toProtanopeColor(){
		double //l = get('l'),
			   m = get('m'),
			   s = get('s');
		
		double lp = 2.02344*m - 2.52581*s,
			   mp = m,
			   sp = s;
		
		return new LMS(lp, mp, sp);
	}

	@Override
	public RGB toRGB() {
		double l = get('l'),
			   m = get('m'),
			   s = get('s');
			   
		double r =  0.0809*l - 0.1305*m + 0.1167*s,
			   g = -0.0102*l + 0.0540*m - 0.1136*s,
			   b = -0.0003*l - 0.0041*m + 0.6935*s;
		
		return new RGB(r,g,b);
	}

}
