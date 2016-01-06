package com.delta.colors.filters;

import com.delta.colors.common.Color;

/**
 * This class represents an ImageFilter.
 * @author ssodelta
 *
 */
public interface ImageFilter {
	
	/**
	 * Apply to this filter to a single pixel.
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @param raster The array of Colors in the image as a whole.
	 * @return This colored as perceived through this filter.
	 */
	public Color filter(int x, int y, Color[][] raster);
	
}
