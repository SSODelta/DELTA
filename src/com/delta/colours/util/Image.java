package com.delta.colours.util;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import com.delta.colours.common.BlendMode;
import com.delta.colours.common.Colour;
import com.delta.colours.common.RGB;
import com.delta.colours.filters.ImageFilter;

/**
 * Represents an Image object. This class is immutable.
 * @author ssodelta
 *
 */
public final class Image {

	private final Colour[][] data;
	private int w, h;
	
	/**
	 * Creates an empty Image
	 */
	public Image(){
		data = new Colour[0][0];
	}
	
	/**
	 * Creates an Image object based on a 2D-array of Colours.
	 * @param data The data to use in this Image.
	 */
	public Image(Colour[][] data){
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
		
		data = new Colour[w][h];
		
		for(int y=0; y<h; y++)
			for(int x=0; x<w; x++)
				data[x][y] = new RGB(img.getRGB(x, y));
				
	}
	
	public final Image processImage(Function<Colour, Colour> map){
		Colour[][] newData = new Colour[w][h];
		
		for(int x=0; x<w; x++)
		for(int y=0; y<h; y++)
			newData[x][y] = map.apply(data[x][y]);
		
		return new Image(newData);
	}

	public final Image processImage(Image img, Function2 map){
		Colour[][] newData = new Colour[w][h];
		
		for(int x=0; x<w; x++)
		for(int y=0; y<h; y++)
			newData[x][y] = map.apply(data[x][y], img.data[x][y]);
		
		return new Image(newData);
	}
	
	public final Image blend(Image img, BlendMode mode){
		return processImage(img, (c1,c2) -> c1.blend(c2, mode));
	}
	
	public final Image blend(Image img){
		return processImage(img, (c1,c2) -> c1.blend(c2));
	}
	
	/**
	 * Applies a filter to this image.
	 * @param filter The ImageFilter to apply.
	 * @return A new image representing this image as seen through the filter 'filter'.
	 */
	public final Image applyFilter(ImageFilter filter){
		Colour[][] newData = new Colour[w][h];

		for(int y=0; y<h; y++)
		for(int x=0; x<w; x++)
			newData[x][y] = filter.filter(x, y, data);
				
		return new Image(newData);
	}
	
	/**
	 * Converts this Image object to a BufferedImage.
	 * @return A BufferedImage representing this Image.
	 */
	public final BufferedImage getBufferedImage(){
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for(int y=0; y<h; y++)
		for(int x=0; x<w; x++)
			img.setRGB(x, y, data[x][y].toInteger());
		
		return img;
	}
	
	public final static BufferedImage applyFilter(ImageFilter filter, BufferedImage bufferedImage){
		Image img = new Image(bufferedImage);
		return img.applyFilter(filter).getBufferedImage();
	}

}
