package com.delta2.colours.filters.image;

import com.delta2.colours.common.Colour;

/**
 * This class represents an ImageFilter.
 * @author ssodelta
 *
 */
public interface DImageFilter {
	
	/**
	 * Apply to this filter to a single pixel.
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @param raster The array of Colors in the image as a whole.
	 * @return This colored as perceived through this filter.
	 */
	public Colour filter(int x, int y, Colour[][] raster);
	
	public static final DImageFilter IDENTITY = (x,y,raster) -> raster[x][y];
}
