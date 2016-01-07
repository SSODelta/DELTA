package com.delta.colors.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.delta.colors.common.Color;

public class MedianFilter implements ImageFilter {

	private final int size;
	
	public MedianFilter(int size){
		this.size = size;
	}
	
	@Override
	public Color filter(int x, int y, Color[][] raster) {
		
		if(raster==null || raster.length==0 || raster[0].length==0)
			throw new IllegalArgumentException("Error: empty raster detected");
		
		Color sample = raster[0][0];
		int n = sample.getDimensions();
		
		@SuppressWarnings("unchecked")
		List<Double>[] channels = (List<Double>[]) new List[n];
		
		for(int i=0; i<n; i++)
			channels[i] = new ArrayList<Double>();

		int imageWidth  = raster.length,
			imageHeight = raster[0].length;
		
		for(int filterX=0; filterX<size; filterX++)
		for(int filterY=0; filterY<size; filterY++){
			int imageX = x - size/2 + filterX,
				imageY = y - size/2 + filterY;
				
				if(imageX < 0 || imageY < 0 || imageX>=imageWidth || imageY>=imageHeight)
					continue;
				
				Color col = raster[imageX][imageY];
				double[] data = col.getData();
				
				if(col.getClass() != sample.getClass())
					throw new IllegalArgumentException("Error: raster must only contain colors of same subclass. Expected "+sample.getClass()+" but got "+col.getClass()+" instead.");
				
				if(data.length != n)
					throw new IllegalArgumentException("Error: raster must only contain colors of same dimensionality. Expected "+n+" but got "+data.length+" instead.");
				
				for(int i=0; i<n;i++)
					channels[i].add(data[i]);
		}
		
		for(int i=0; i<n; i++)
			Collections.sort(channels[i]);
		
		double[] finalColor = new double[n];
		
		for(int i=0; i<n; i++){
			
			int s = channels[i].size();
			
			if(s%2 == 0)
				finalColor[i] = (channels[i].get(s/2-1) + channels[i].get(s/2))/2;
			else
				finalColor[i] = channels[i].get(s/2);
			
		}
		
		return Color.fromClass(sample.getClass(), finalColor);
	}

}
