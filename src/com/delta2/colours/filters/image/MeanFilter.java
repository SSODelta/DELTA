package com.delta2.colours.filters.image;

public class MeanFilter extends ConvolutionFilter {

	public MeanFilter(int size){
		super(getFilter(size));
	}

	private static double[][] getFilter(int size){
		double[][] matrix = new double[size][size];

		for(int x=0; x<size; x++)
		for(int y=0; y<size; y++)
			matrix[x][y]=1;
		
		return matrix;
	}
}
