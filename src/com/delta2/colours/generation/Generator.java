package com.delta2.colours.generation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.delta2.colours.Animation;
import com.delta2.colours.BlendMode;
import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;
import com.delta2.colours.DImage;
import com.delta2.colours.DImageIO;
import com.delta2.thirdparty.GIFSequenceWriter;

public abstract class Generator {

	private long begin;
	
	private int width, height;

	
	protected Generator(int width, int height){
		begin = System.currentTimeMillis();
		this.width= width;
		this.height = height;
	}
	
	private String time(){
		return "[" +
					ColourUtil.pad((int)Math.floor((System.currentTimeMillis() - begin) / 1000f),5)
			 + "]: ";
	}
	
	public Colour[][] generateRaster(double t){
		Colour[][] raster = new Colour[width][height];
		
		int max = Math.max(width, height);
		
		for(int xx=0; xx<width;  xx++)
		for(int yy=0; yy<height; yy++)
			raster[xx][yy] = generate((double)xx/(double)(max-1), (double)(yy)/(double)(max-1), t);

		
		return raster;
	}
	
	public DImage generateImage(String tag, double t){
		DImage img = generateImage(t);
		return tag.isEmpty() ? img : ColourUtil.writeToImage(img, tag);
	}
	
	public DImage generateImage(double t){
		return new DImage(generateRaster(t));
	}
	
	public DImage generateImage(){
		return generateImage(0);
	}

	
	public void exportAnimation(String filename, String tag, int images, int fps, boolean print) throws IOException{
		if(images<1)
			throw new RuntimeException("Empty animation");
		
		ImageOutputStream outStream = new FileImageOutputStream(new File(filename));
		GIFSequenceWriter gifWriter = null;
		DImage prev = null;

		for(int i=0; i<images; i++){
			if(print)tag(i,images,tag);
			DImage img = generateImage(tag, (double)i/(double)(images-1));
			
			if(prev!=null){
				prev.setAlpha(0.825);
				img.blend(prev, BlendMode.SOFT_LIGHT);
				img.setAlpha(1.0);
			}
			
			prev = new DImage(img);
			
			if(i==0){
				String newFilename = filename.substring(0,filename.lastIndexOf(".")) + "_sample" + filename.substring(filename.lastIndexOf("."));
				DImageIO.write(img, newFilename);
			}
			
			if(gifWriter==null)
				gifWriter = new GIFSequenceWriter(outStream, BufferedImage.TYPE_INT_RGB, 1000/fps, true);
			
			gifWriter.writeToSequence(img.toBufferedImage());
		}

		System.out.println();
		gifWriter.close();
	}

	public Animation generateAnimation(String tag, int images, boolean print){
		Animation a = new Animation();
		
		for(int t=0; t<images; t++){
			if(print)tag(t,images,tag);
			a.addImage(generateImage(tag, (double)t/(double)(images-1)));
		}
		
		return a;
	}

	
	
	private final void tag(int t, int imgs,String tag){
		System.out.println(time()+"Image "+(t+1)+"/"+imgs + "\t"+tag);
		
		if(t!=0){
			int dt        = (int)Math.floor((System.currentTimeMillis() - begin)/1000);
			int remaining = (int)Math.floor(dt * ((double)(imgs-t) / (double)t));
					
			System.out.print("        Remaining time: ~"+remaining+" seconds");
			
			LocalDateTime ldt = LocalDateTime.now().plusSeconds(remaining);
			
			System.out.println("\t@"+ColourUtil.pad(ldt.getHour(),2)
													  +":"+ColourUtil.pad(ldt.getMinute(),2)
													  +":"+ColourUtil.pad(ldt.getSecond(),2)
													  +" ("+ColourUtil.pad(ldt.getDayOfMonth(),2)
													    +"/"+ColourUtil.pad(ldt.getMonthValue(),2)
														+"/"+ColourUtil.pad(ldt.getYear(),4)
														+")");
			if(t!=imgs-1)System.out.print("\n");
		}
	}
	
	/**
	 * Generates a color object for a given (x,y) pixel
	 * @param x The x-coordinate (in the interval [0, 1]) of the point.
	 * @param y The y-coordinate (in the interval [0, 1]) of the point.
	 * @param t The time variable (also in the interval [0, 1]).
	 * @return A Colour object to be painted at (x,y) in the final image.
	 */
	protected abstract Colour generate(double x, double y, double t);
	
}
