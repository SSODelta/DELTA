package com.delta2.colours;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.delta2.colours.filters.animation.AnimationFilter;
import com.delta2.thirdparty.GIFSequenceWriter;


public final class Animation {

	private List<DImage> images;
	private int delay;
	
	public Animation(){
		images = new ArrayList<DImage>();
		delay = 100/6;
	}
	
	public Animation(DImage img){
		this();
		images.add(img);
	}
	
	public Animation(Collection<? extends DImage> imgs){
		this();
		images.addAll(imgs);
	}
	
	public void applyFilter(AnimationFilter filter){
		
		DImage[] imgs = images.toArray(new DImage[0]);
		
		for(int i=0; i<images.size(); i++)
			filter.applyFilter(imgs, i);
		
	}
	
	public void ensureLength(int len){
		int i = 0;
		
		while(images.size() < len)
			images.add(new DImage(images.get(i++)));
		
	}
	
	
	public void addImage(DImage img){
		images.add(img);
	}
	
	public void export(File output) throws IOException{
		if(images==null || images.size()==0)
			throw new RuntimeException("Empty animation");
		
		ImageOutputStream outStream = new FileImageOutputStream(output);
		GIFSequenceWriter gifWriter = new GIFSequenceWriter(outStream, BufferedImage.TYPE_INT_RGB, delay, true);

		for(int i=0; i<images.size(); i++){
			DImage img = images.get(i);
			gifWriter.writeToSequence(img.toBufferedImage());
		}

		System.out.println();
		gifWriter.close();
	}
}
