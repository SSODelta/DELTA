package com.delta.colours.filters;

import com.delta.colours.common.Colour;

public class NoiseFilter implements ImageFilter {

	private double maxNoise;
	
	public NoiseFilter(double noise){
		this.maxNoise = noise;
	}

	private double rand(){
		return maxNoise*(Math.random()*2-1);
	}
	
	private double[] rand(int size){
		double[] d = new double[size];
		for(int i=0; i<size; i++)
			d[i]=rand();
		return d;
	}
	
	@Override
	public Colour filter(int x, int y, Colour[][] raster) {
		Colour col = raster[x][y];
		return col.add(rand(col.getDimensions()));
	}
	
	
}
