package com.delta2.colours.generation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.delta2.colours.Animation;
import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;
import com.delta2.colours.DImage;
import com.delta2.colours.DImageIO;
import com.delta2.thirdparty.GIFSequenceWriter;

public abstract class Generator {

	private long begin;
	
	protected Generator(){
		begin = System.currentTimeMillis();
	}
	
	private String time(){
		return "[" +
					ColourUtil.pad((int)Math.floor((System.currentTimeMillis() - begin) / 1000f),5)
			 + "]: ";
	}
	
	public Colour[][] generateRaster(int width, int height, double t){
		Colour[][] raster = new Colour[width][height];
		
		int max = Math.max(width, height);
		
		for(int xx=0; xx<width;  xx++)
		for(int yy=0; yy<height; yy++)
			raster[xx][yy] = generate((double)xx/(double)(max-1), (double)yy/(double)(max-1), t);

		
		return raster;
	}
	
	public DImage generateImage(int width, int height){
		return new DImage(generateRaster(width, height, 0));
	}
	
	public DImage generateImage(int width, int height, double t){
		return new DImage(generateRaster(width, height, t));
	}
	
	public DImage generateImage(String tag, int width, int height, double t){
		return ColourUtil.writeToImage(generateImage(width, height,t), tag);
	}
	
	public DImage generateImage(String tag, int width, int height){
		return ColourUtil.writeToImage(generateImage(width, height), tag);
	}
	
	public BufferedImage generateBufferedImage(int width, int height, double t){
		return generateImage(width, height, t).toBufferedImage();
	}
	public BufferedImage generateBufferedImage(String tag, int width, int height, double t){
		return generateImage(tag, width, height, t).toBufferedImage();
	}
	public BufferedImage generateBufferedImage(String tag, int width, int height){
		return generateImage(tag, width, height).toBufferedImage();
	}
	
	public void exportAnimation(String filename, String tag, int width, int height, int images, int fps, boolean print) throws IOException{
		if(images<1)
			throw new RuntimeException("Empty animation");
		
		ImageOutputStream outStream = new FileImageOutputStream(new File(filename));
		GIFSequenceWriter gifWriter = null;

		for(int i=0; i<images; i++){
			if(print)tag(i,images,tag);
			DImage img = generateImage(tag, width, height, (double)i/(double)(images-1));
			
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

	public Animation generateAnimation(String tag, int width, int height, int images, boolean print){
		Animation a = new Animation();
		
		for(int t=0; t<images; t++){
			if(print)tag(t,images,tag);
			a.addImage(generateImage(tag, width, height, (double)t/(double)(images-1)));
		}
		
		return a;
	}
	
	public Animation generateAnimation(String tag, int width, int height, int images){
		return generateAnimation(tag,width,height,images,false);
	}
	
	public Animation generateAnimation(int width, int height, int images, boolean print){
		Animation a = new Animation();
		
		for(int t=0; t<images; t++){
			if(print)tag(t,images,"");
			a.addImage(generateImage(width, height, (double)t/(double)(images-1)));
		}
		
		return a;
	}

	public Animation generateAnimation(int width, int height, int images){
		return generateAnimation(width,height,images,false);
	}
	
	private final void tag(int t, int imgs,String tag){
		System.out.println(time()+"Image "+(t+1)+"/"+imgs + "\t"+tag);
		
		if(t!=0){
			int dt        = (int)Math.floor((System.currentTimeMillis() - begin)/1000);
			int remaining = (int)Math.floor(dt * ((double)(imgs-t) / (double)t));
					
			System.out.println("        Remaining time: ~"+remaining+" seconds.");
			if(t!=imgs-1)System.out.print("\n");
		}
	}
	
	protected abstract Colour generate(double x, double y, double t);
	
}
