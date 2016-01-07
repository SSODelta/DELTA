package com.delta.colours.filters;

import com.delta.colours.common.Colour;

/**
 * A ConvolutionFilter represents a filter, that uses a coefficient matrix to compute the new value of a given pixel based on the neighboring pixels.
 * @author ssodelta
 *
 */
public class ConvolutionFilter implements ImageFilter {

	private double[][] matrix;
	private double factor;
	private boolean relative;
	
	/**
	 * Constructs a new ConvolutionFilter.
	 * @param matrix The coefficient matrix.
	 * @param factor A coefficient which is used to scale every pixel. Note that the results may vary depending on the actual subtype.
	 * @param preserveAverage Preserve the same average lightness on every pixel after applying the filter (should all pixels be downscaled by the sum of the cells in 'matrix'?)
	 */
	public ConvolutionFilter(double[][] matrix, double factor, boolean preserveAverage){
		this.matrix = matrix;
		this.factor = factor;
		this.relative = preserveAverage;
	}
	
	/**
	 * Constructs a new ConvolutionFilter with factor=1.0 and preserveAverage=true.
	 * @param matrix The coefficient.
	 */
	public ConvolutionFilter(double[][] matrix){
		this(matrix, 1.0, true);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		int w = matrix.length,
			h = matrix[0].length;
		
		for(int y=0; y<h; y++){
			for(int x=0; x<w; x++){
				sb.append(matrix[x][y]);
				if(x!=w-1)
					sb.append(' ');
			}
			if(y!=h-1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {
		
		int imageWidth  = raster.length,
			imageHeight = raster[0].length;
		
		int filterWidth  = matrix.length,
			filterHeight = matrix[0].length;

		Colour res = null;
		
		double f = 0;

		for(int filterX=0; filterX<filterWidth;  filterX++)
		for(int filterY=0; filterY<filterHeight; filterY++){
			int imageX = x - filterWidth/2  + filterX,
				imageY = y - filterHeight/2 + filterY;
			
			if(imageX < 0 || imageY < 0 || imageX>=imageWidth || imageY>=imageHeight)
				continue;
			
			double scale = matrix[filterX][filterY];
			
			if(relative)
				f += scale;
			
			Colour next = raster[imageX][imageY];
			
			res = res==null ? next.scale(scale) : res.add(next.scale(scale));
			
		}
		
		Colour result = res.scale(factor/(relative ? f : 1));
		
		return result;
	}

}
