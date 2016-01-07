package com.delta.colours.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.NodeList;

import com.delta.thirdparty.GIFSequenceWriter;

public class Animation {

	private final List<Image> images;
	private int delay;
	
	private Animation(){
		images = new LinkedList<Image>();
	}
	
	public Animation(int delay){
		this();
		this.delay = delay;
	}
	
	public Animation(int delay, Image img){
		this(delay);
		images.add(img);
	}
	
	public Animation(int delay, List<Image> images){
		this.delay  = delay;
		this.images = images;
	}
	
	public Animation(int delay, File file){
		this();
		this.delay = delay;
		List<BufferedImage> gif;
		try {
			gif = loadGIF(file);
		} catch (IOException e) {
			return;
		}
		for(BufferedImage bufferedImage : gif)
			images.add(new Image(bufferedImage));
	}
	
	public Animation append(Animation a){
		List<Image> newImages = new ArrayList<Image>(images);
		for(int i=0; i<images.size(); i++)
			newImages.add(a.images.get(i));
		return new Animation(delay, newImages);
	}
	
	public Animation processAnimation(int frames, TimeFunction f){
		return processAnimation(frames, 0, 1, f);
	}
	
	public Animation processAnimation(int t_start, int t_end, TimeFunction f){
		List<Image> newImages = new ArrayList<Image>();

		for(int t=t_start; t<t_end; t++)
			newImages.add( f.apply(images.get((t-t_start)%images.size()), t));

		
		return new Animation(this.delay, newImages);
	}
	
	public Animation processAnimation(int frames, double t_start, double t_end, TimeFunction f){
		
		List<Image> newImages = new ArrayList<Image>();
		double dt = (t_end - t_start) / (frames-1);

		System.out.println("PROCESSING");
		for(int i=0; i<frames; i++){

			double t = t_start + i*dt;

			System.out.println("Processing "+(i+1)+"/"+frames+" (t="+t+")");			
			newImages.add( f.apply(images.get(i%images.size()), t));
			
		}
		System.out.println();
		
		return new Animation(this.delay, newImages);
	}
	
	private String pad(int i, int len){
		String s = ""+i;
		while(s.length() < len) s = "0"+s;
		return s;
	}
	public void export(String output) throws IOException{
		if(!output.contains(".") || output.endsWith("."))
			throw new IllegalArgumentException("output file must contain a valid file extension");
		
		int n = output.indexOf(".");
		
		String prefix = output.substring(0, n),
			   ext    = output.substring(n+1).toLowerCase();
		
		int outputLen = (""+(images.size()-1)).length();
		
		for(int i=0; i<images.size(); i++)
			ImageIO.write(images.get(i).getBufferedImage(), ext, new File(prefix+pad(i,outputLen)+"."+ext));
		
	}
	
	public void export(File output) throws IOException{
		if(images==null || images.size()==0)
			throw new RuntimeException("Empty animation");
		
		ImageOutputStream outStream = new FileImageOutputStream(output);
		GIFSequenceWriter gifWriter = new GIFSequenceWriter(outStream, BufferedImage.TYPE_INT_RGB, delay, true);

		System.out.println("EXPORTING");
		for(int i=0; i<images.size(); i++){
			System.out.println("Processing "+(i+1)+"/"+images.size());
			Image img = images.get(i);
			gifWriter.writeToSequence(img.getBufferedImage());
		}

		System.out.println();
		gifWriter.close();
	}

	private List<BufferedImage> loadGIF(File file) throws IOException{
		
		List<BufferedImage> imageList = new ArrayList<BufferedImage>();
		
		ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
		ImageInputStream ciis = ImageIO.createImageInputStream(file);
		
		reader.setInput(ciis, false);
		
		int i=0;
		while(true){
			ImageFrame frame = ImageFrame.readGIF(reader, ++i);
			
			if(i==1)
				this.delay = frame.getDelay();
			
			if(frame==null)break;
			
			imageList.add(frame.getImage());
		}
		
		return imageList;
	}
	
	private static class ImageFrame {
		private final int delay;
	    private final BufferedImage image;

	    public ImageFrame(BufferedImage image, int delay) {
	        this.image = image;
	        this.delay = delay;
	    }

	    public BufferedImage getImage() {
	        return image;
	    }

	    public int getDelay() {
	        return delay;
	    }
	    
	    /**
	     * Read a frame of a .gif file.
	     * @param reader The ImageReader to read from.
	     * @param frameIndex The frame to load.
	     * @return A frame from a .gif file.
	     * @throws IOException
	     */
	    public static ImageFrame readGIF(ImageReader reader, int frameIndex) throws IOException {

	        int width = -1;
	        int height = -1;

	        IIOMetadata metadata = reader.getStreamMetadata();
	        if (metadata != null) {
	            IIOMetadataNode globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());

	            NodeList globalScreenDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");

	            if (globalScreenDescriptor != null && globalScreenDescriptor.getLength() > 0) {
	                IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreenDescriptor.item(0);

	                if (screenDescriptor != null) {
	                    width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
	                    height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
	                }
	            }
	        }

	        BufferedImage master = null;
	        Graphics2D masterGraphics = null;

	            BufferedImage image;
	            try {
	                image = reader.read(frameIndex);
	            } catch (IndexOutOfBoundsException io) {
	                return null;
	            }

	            if (width == -1 || height == -1) {
	                width = image.getWidth();
	                height = image.getHeight();
	            }

	            IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
	            IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
	            int delay = Integer.valueOf(gce.getAttribute("delayTime"));

	            int x = 0;
	            int y = 0;

	            master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				masterGraphics = master.createGraphics();
				masterGraphics.setBackground(new Color(0, 0, 0, 0));
	            masterGraphics.drawImage(image, x, y, null);

	            BufferedImage copy = new BufferedImage(master.getColorModel(), master.copyData(null), master.isAlphaPremultiplied(), null);
	            return new ImageFrame(copy, delay);

	    }
	}
}
