package com.delta2.colours;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta2.colours.filters.image.DImageFilter;

public final class DImageIO {


	
	public static final void filter(String input, DImageFilter filter) throws IOException{
		write(read(new File(input)).applyFilter(filter), "out_"+input);
	}
	
	public static final void filter(File input, String output, DImageFilter filter) throws IOException{
		write(read(input).applyFilter(filter), output);
	}

	public static final void write(DImage img, String path) throws IOException{
		if(!path.contains("."))
			throw new IllegalArgumentException("output path must have a file extension");
		ImageIO.write(img.toBufferedImage(), path.substring(path.lastIndexOf(".")+1).toLowerCase(), new File(path));
	}
	

	public static final DImage read(String s) throws IOException{
		return new DImage(ImageIO.read(new File(s)));
	}
	
	public static final DImage read(File f) throws IOException{
		return new DImage(ImageIO.read(f));
	}
	
}
