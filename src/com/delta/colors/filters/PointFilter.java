package com.delta.colors.filters;

import com.delta.colors.common.Color;

/**
 * A PointFilter represents a filter, where the new value of any given pixel does *not* depend on its neighbors.
 * @author ssodelta
 */
public abstract class PointFilter implements ImageFilter {


	public Color filter(int x, int y, Color[][] raster){
		return filter(x,y,raster[x][y]);
	}
	
	/**
	 * Applies this filter to a single pixel.
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @param col A Color object representing the color of the pixel at [x,y].
	 * @return The new color
	 */
	public abstract Color filter(int x, int y, Color col);
}
