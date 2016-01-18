package com.delta2.colours;

import java.awt.image.BufferedImage;

import com.delta2.colours.filters.image.DImageFilter;

public final class DImage {

	private final Colour[][] data;
	private final int width, height;
	
	private double alpha = 1.0;
	
	public DImage(DImage img){
		
		this.width  = img.width;
		this.height = img.height;
		
		data = new Colour[width][height];
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			data[x][y] = new Colour(img.data[x][y]);
	}
	
	public DImage(Colour[][] raster){
		this.data = raster;
		
		this.width  = raster.length;
		this.height = raster[0].length;
	}
	
	public DImage(int width, int height){
		
		this.width  = width;
		this.height = height;
		
		data = new Colour[width][height];

	}
	
	public DImage(BufferedImage img){
		width  = img.getWidth();
		height = img.getHeight();
		
		data = new Colour[width][height];
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			data[x][y] = new Colour(img.getRGB(x, y));
		
	}
	
	public double getAlpha(){
		return alpha;
	}
	
	//---Mutators
	
	public void setAlpha(double a){
		this.alpha = a;
	}
	
	public DImage applyFilter(DImageFilter filter){
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			data[x][y] = filter.filter(x, y, data);
		
		return this;
	}
	
	public DImage blend(DImage other, BlendMode mode){
		if(this.width!=other.width || this.height!=other.height)
			throw new IllegalArgumentException("cannot blend two images of different dimensions.");

		double a1 = this.getAlpha(), a2 = other.getAlpha();
		double newAlpha = a1 + a2 - a1*a2;
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++){
			Colour col = this.data[x][y],
				   bl  = other.data[x][y];
			
			col.scale(a1);
			bl.scale(a2);
			
			col.blend(bl, mode);
			
			col.scale(1.0 / newAlpha);
			
		}
		return this;
	}
	
	//---Accessors
	
	public BufferedImage toBufferedImage(){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			img.setRGB(x, y, data[x][y].to24bits());
		
		return img;
	}
	
	
}
