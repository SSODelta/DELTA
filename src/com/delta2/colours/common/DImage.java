package com.delta2.colours.common;

import java.awt.image.BufferedImage;

import com.delta2.colours.filters.image.DImageFilter;

public final class DImage {

	private final Colour[][] data;
	private final int width, height;
	
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
	
	public BufferedImage toBufferedImage(){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			img.setRGB(x, y, data[x][y].to24bits());
		
		return img;
	}
	
	public DImage applyFilter(DImageFilter filter){
		
		for(int x=0; x<width;  x++)
		for(int y=0; y<height; y++)
			data[x][y] = filter.filter(x, y, data);
		
		return this;
	}
	
}
