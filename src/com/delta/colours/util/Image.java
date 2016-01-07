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
	private double alpha;
	
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
	public Image(double alpha, Colour[][] data){
		this.h = data[0].length;
		this.w = data.length;
		this.data = data;
		this.alpha = alpha;
	}
	
	public Image(Colour[][] data){
		this(1.0, data);
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
	
	public Image setAlpha(double alpha){
		return new Image(alpha, data);
	}
	
	public double getAlpha(){
		return alpha;
	}

	
	public final Image processImage(Function<Colour, Colour> map){
		Colour[][] newData = new Colour[w][h];
		
		for(int x=0; x<w; x++)
		for(int y=0; y<h; y++)
			newData[x][y] = map.apply(data[x][y]);
		
		return new Image(newData);
	}

	public final Image processImage(Image img, FunctionColours map){
		Colour[][] newData = new Colour[w][h];
		
		for(int x=0; x<w; x++)
		for(int y=0; y<h; y++)
			newData[x][y] = map.apply(data[x][y], img.data[x][y]);
		
		return new Image(newData);
	}
	
	public final Image blend(Image img, BlendMode mode){
		double newAlpha = this.getAlpha() + img.getAlpha() - this.getAlpha()*img.getAlpha();
		return processImage(img, (c1,c2) -> c1.scale(this.getAlpha())
											  .blend( c2.convert(c1.getClass()).scale(img.getAlpha()), mode)
				 							  .scale(1.0 / newAlpha));
	}
	
	public final Image blend(Image img){
		return blend(img, Colour.BLEND_MODE);
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
