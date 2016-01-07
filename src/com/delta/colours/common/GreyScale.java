package com.delta.colours.common;

import com.delta.colours.rare.DefaultColour;

public class GreyScale extends DefaultColour {

	private static final long serialVersionUID = -2056807355453529773L;

	@Override
	public GreyScale toGreyScale(){
		return new GreyScale(get('v'));
	}
	
	public GreyScale(double v) {
		super("v", new double[]{v});
	}

	@Override
	public RGB toRGB() {
		return new HSV(0,0,get('v')).toRGB();
	}

}
