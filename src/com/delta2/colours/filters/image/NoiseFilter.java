package com.delta2.colours.filters.image;

import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;

public class NoiseFilter implements DImageFilter {

	private double maxNoise;
	
	public NoiseFilter(double noise){
		this.maxNoise = noise;
	}

	private double rand(){
		return maxNoise*ColourUtil.rand();
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
