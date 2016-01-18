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
	
	public DImage(BufferedImage img){
		data = new Colour[img.getWidth()][img.getHeight()];
		
		width  = img.getWidth();
		height = img.getHeight();
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			data[x][y] = new Colour(img.getRGB(x, y));
		
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

		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			this.data[x][y].blend(other.data[x][y], mode);
		
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
