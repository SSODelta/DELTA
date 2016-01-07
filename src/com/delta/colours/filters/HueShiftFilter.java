package com.delta.colours.filters;

import com.delta.colours.common.Colour;

public class HueShiftFilter implements ImageFilter {

	private double dhue;
	
	public HueShiftFilter(double hueChange){
		this.dhue = hueChange;
	}
	
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {
		return raster[x][y].toHSV().add(dhue,0,0);
	}

}
