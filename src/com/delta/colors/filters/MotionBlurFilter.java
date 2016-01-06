package com.delta.colors.filters;

public class MotionBlurFilter extends ConvolutionFilter {

	public MotionBlurFilter(double direction, int scale){
		super(getMotionBlurFilter(direction, scale));
	}
	
	private static double[][] getMotionBlurFilter(double direction, int size){
		
		if(size<1)
			throw new IllegalArgumentException("Error, the size must be a positive integer.");
		
		//Special cases
		if(direction == 0 || direction == Math.PI)
			return getHorizontal(size);

		if(direction == Math.PI/2 || direction == 3/2*Math.PI)
			return getVertical(size);

		if(direction == Math.PI/4 || direction == 5/4*Math.PI)
			return getDiagonalUp(size);

		if(direction == 3/4*Math.PI || direction == 7/4*Math.PI)
			return getDiagonalDown(size);
		
		double slope = Math.tan(-direction);
		
		int x0,y0,x1,y1;
		
		if(slope>=1){
			y0 = 0;
			y1 = size-1;
			
			x0 = (int)Math.round(size/2 - size/(2*slope));
			x1 = (int)Math.round(size/2 + size/(2*slope));
		} else {
			x0 = 0;
			x1 = size-1;
			
			y0 = (int)Math.round(size/2 - size*slope/2);
			y1 = (int)Math.round(size/2 + size*slope/2);
		}
		
		return getMotionBlurFilter(x0,y0,x1,y1,size);
	}
	

	private static double[][] getDiagonalDown(int size){
		double[][] matrix = new double[size][size];
		
		for(int i=0; i<size; i++)
			matrix[i][i]=1;
		
		return matrix;
	}

	private static double[][] getDiagonalUp(int size){
		double[][] matrix = new double[size][size];
		
		for(int i=0; i<size; i++)
			matrix[i][size-1-i]=1;
		
		return matrix;
	}
	
	private static double[][] getVertical(int size){
		double[][] matrix = new double[size][size];
		
		int x = size/2;
		for(int y=0; y<size; y++)
			matrix[x][y]=1;
		
		return matrix;
	}

	
	private static double[][] getHorizontal(int size){
		double[][] matrix = new double[size][size];
		
		int y = size/2;
		for(int x=0; x<size; x++)
			matrix[x][y]=1;
		
		return matrix;
	}
	
	private static double[][] getMotionBlurFilter(int x0, int y0, int x1, int y1, int size){

		double[][] matrix = new double[size][size];
		
		double dx   = x1-x0,
			   dy   = y1-y0,
			   err  = 0,
			   dErr = Math.abs(dy/dx);
		
		int y = y0;
		
		for(int x=x0; x<=x1; x++){
			
			if(y>=0 && y<size)
				matrix[x][y] = 1;
			err += dErr;
			
			while(err >= 0.5){
				if(y>=0 && y<size)
					matrix[x][y] = 1;
				y += Math.signum(dy);
				err--;
			}
		}
		
		return matrix;
	}
	
}
