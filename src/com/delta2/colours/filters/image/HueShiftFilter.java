package com.delta2.colours.filters.image;

import com.delta2.colours.Colour;
import com.delta2.colours.colourspace.ColourSpace;

public class HueShiftFilter implements DImageFilter {

	private double dhue;
	
	public HueShiftFilter(double hueChange){
		this.dhue = hueChange/360;
	}
	
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {
		return raster[x][y].convert(ColourSpace.HSV).add(dhue,0,0);
	}

}
