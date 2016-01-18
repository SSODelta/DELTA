package com.delta2.colours.filters.image;

public class BlurFilter extends ConvolutionFilter {

	public BlurFilter(int sizeX, int sizeY) {
		super(getBlurFilter(sizeX, sizeY));
	}
	
	private static double[][] getBlurFilter(int sizeX, int sizeY){
		if(sizeX<1 || sizeY<1)
			throw new IllegalArgumentException("Error, the size must be a positive integer.");
		
		double[][] matrix = new double[sizeX][sizeY];
		
		for(int x=0; x<sizeX; x++)
		for(int y=0; y<sizeY; y++){
			double norm = Math.pow(((double) x / (double) sizeX) - 0.5, 2)
					    + Math.pow(((double) y / (double) sizeY) - 0.5, 2);
			matrix[x][y] = norm <= 1 ? 1 : 0;
		}
		
		return matrix;
	}

}
