package com.delta.colours.filters;

import com.delta.colours.common.Colour;

public abstract class VignetteFilter implements ImageFilter {

	private char channel;
	private double exp;
	
	protected VignetteFilter(char c, double exp){
		this.channel = c;
		this.exp     = exp;
	}
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {

		int w  = raster.length,
			h  = raster[0].length;
		
		return raster[x][y].toHSL().set(channel, vignette(x,y,w,h));
	}
	
	private double vignette(int x, int y, int w, int h){
		int cx = w/2, cy = h/2;
		
		double v = Math.hypot(x-cx, (double)w/(double)h*(double)(y-cy))
				   / Math.hypot(cx, cy);
		
		return 1-Math.pow(v*2-1, exp);
	}

}
