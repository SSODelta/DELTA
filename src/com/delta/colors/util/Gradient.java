package com.delta.colors.util;

import com.delta.colors.common.Color;

/**
 * An interface representing a linear gradient between two Colors.
 * @author ssodelta
 *
 */
public interface Gradient {

	/**
	 * Interpolates linearly between the two colors, where t=0 represents 'from' and t=1 represents 'to'.
	 * @param t
	 * @return
	 */
	public Color interpolate(double t);
	
}
