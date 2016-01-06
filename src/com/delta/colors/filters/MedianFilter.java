package com.delta.colors.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.delta.colors.common.Color;

public class MedianFilter implements ImageFilter {

	private final int size;
	private final Comparator<Color> comp;
	
	public MedianFilter(Comparator<Color> comp, int size){
		this.size = size;
		this.comp = comp;
	}
	
	@Override
	public Color filter(int x, int y, Color[][] raster) {
		List<Color> colors = new ArrayList<Color>();

		int imageWidth  = raster.length,
			imageHeight = raster[0].length;
		
		for(int filterX=0; filterX<size; filterX++)
		for(int filterY=0; filterY<size; filterY++){
			int imageX = x - size/2  + filterX,
				imageY = y - size/2 + filterY;
				
				if(imageX < 0 || imageY < 0 || imageX>=imageWidth || imageY>=imageHeight)
					continue;
				
				colors.add(raster[imageX][imageY]);
		}
		
		Collections.sort(colors, comp);
		
		return colors.get(colors.size()/2);
	}

}
