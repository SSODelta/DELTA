package com.delta2.colours.generation;

import java.awt.image.BufferedImage;

import com.delta2.colours.Animation;
import com.delta2.colours.Colour;
import com.delta2.colours.DImage;

public abstract class Generator {

	protected Generator(){}
	
	public Colour[][] generateRaster(int width, int height, double t){
		Colour[][] raster = new Colour[width][height];
		
		for(int xx=0; xx<width;  xx++)
		for(int yy=0; yy<height; yy++){
			raster[xx][yy] = generate((double)xx/(double)(width-1), (double)yy/(double)(height-1), t);
		}
		
		return raster;
	}
	
	public DImage generateImage(int width, int height){
		return new DImage(generateRaster(width, height, 0));
	}
	
	public DImage generateImage(int width, int height, double t){
		return new DImage(generateRaster(width, height, t));
	}
	
	public BufferedImage generateBufferedImage(int width, int height, double t){
		return generateImage(width, height, t).toBufferedImage();
	}
	
	public Animation generateAnimation(int width, int height, int images){
		Animation a = new Animation();
		
		for(int t=0; t<images; t++)
			a.addImage(generateImage(width, height, (double)t/(double)(images-1)));
		
		return a;
	}
	
	protected abstract Colour generate(double x, double y, double t);
	
}
