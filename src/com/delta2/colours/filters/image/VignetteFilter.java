package com.delta2.colours.filters.image;

import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.common.Colour;

public abstract class VignetteFilter implements DImageFilter {

	private int channel;
	private double exp;
	
	protected VignetteFilter(int c, double exp){
		this.channel = c;
		this.exp     = exp;
	}
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {

		int w  = raster.length,
			h  = raster[0].length;
		
		return raster[x][y].convert(ColourSpace.HSL).set(channel, vignette(x,y,w,h));
	}
	
	private double vignette(int x, int y, int w, int h){
		int cx = w/2, cy = h/2;
		
		double v = Math.hypot(x-cx, (double)w/(double)h*(double)(y-cy))
				   / Math.hypot(cx, cy);
		
		return 1-Math.pow(v*2-1, exp);
	}

}
