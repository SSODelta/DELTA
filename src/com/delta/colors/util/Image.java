package com.delta.colors.util;

import java.awt.image.BufferedImage;

import com.delta.colors.common.Color;
import com.delta.colors.common.RGB;
import com.delta.colors.filters.ImageFilter;

/**
 * Represents an Image object. This class is immutable.
 * @author ssodelta
 *
 */
public final class Image {

	protected final Color[][] data;
	private int w, h;
	
	/**
	 * Creates an empty Image
	 */
	public Image(){
		data = new Color[0][0];
	}
	
	/**
	 * Creates an Image object based on a 2D-array of Colors.
	 * @param data The data to use in this Image.
	 */
	public Image(Color[][] data){
		this.h = data[0].length;
		this.w = data.length;
		this.data = data;
	}
	
	/**
	 * Constructs an Image object from a BufferedImage
	 * @param img A BufferedImage
	 */
	public Image(BufferedImage img){
		
		this.w = img.getWidth();
		this.h = img.getHeight();
		
		data = new Color[w][h];
		
		for(int y=0; y<h; y++)
			for(int x=0; x<w; x++)
				data[x][y] = new RGB(img.getRGB(x, y));
				
	}
	
	/**
	 * Applies a filter to this image.
	 * @param filter The ImageFilter to apply.
	 * @return A new image representing this image as seen through the filter 'filter'.
	 */
	public Image applyFilter(ImageFilter filter){
		Color[][] newData = new Color[w][h];

		for(int y=0; y<h; y++)
			for(int x=0; x<w; x++)
				newData[x][y] = filter.filter(x, y, data);
				
		return new Image(newData);
	}
	
	/**
	 * Converts this Image object to a BufferedImage.
	 * @return A BufferedImage representing this Image.
	 */
	public BufferedImage getBufferedImage(){
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for(int y=0; y<h; y++)
			for(int x=0; x<w; x++)
				img.setRGB(x, y, data[x][y].toInteger());
		
		return img;
	}

}
