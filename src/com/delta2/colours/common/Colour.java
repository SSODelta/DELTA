package com.delta2.colours.common;

import java.awt.Color;

import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.util.ColourUtil;

public final class Colour {

	//----PRIVATE FIELDS
	
	private ColourSpace colourSpace;
	private double[] data;
	
	
	//----CONSTRUCTORS
	
	public Colour(Colour c){
		this.colourSpace = c.colourSpace;
		this.data = c.data.clone();
	}
	
	public Colour(double[] data, ColourSpace cp){
		this.colourSpace = cp;
		this.data = data;
	}
	
	public Colour(double[] data){
		this(data, ColourSpace.RGB);
	}
	
	public Colour(int r, int g, int b){
		this(new double[]{r/255.0, g/255.0, b/255.0});
	}
	
	public Colour(Color col){
		this(col.getRed(), col.getGreen(), col.getBlue());
	}
	
	public Colour(int rgb){
		this(new Color(rgb));
	}
	
	public Color toColor(){
		convert(ColourSpace.RGB);
		return new Color(ColourUtil.to8bits(data[0]),
						 ColourUtil.to8bits(data[1]),
						 ColourUtil.to8bits(data[2]));
	}
	
	
	public static final Colour fromColourSpace(ColourSpace cs, double[] data){
		Colour c = new Colour(new double[]{0,0,0}, ColourSpace.RGB);
		c.convert(cs);
		
		if(c.getDimensions() != data.length)
			throw new IllegalArgumentException("cannot construct color with a different number of input channels ("+data.length+") than specified in the ColourSpace ("+c.getDimensions()+")");
		
		for(int i=0; i<data.length; i++)
			c.set(i, data[i]);
		
		return c;
	}
	
	//-----MUTATORS
	
	public Colour add(Colour c){
		c.convert(colourSpace);
		return add(c.data);
	}
	
	public Colour add(double... vals){
		if(vals.length!=data.length)
			throw new IllegalArgumentException("Length of input vector ("+vals.length+") must equal the number of color channels ("+data.length+")");
	
		for(int i=0; i<data.length; i++)
			data[i]+=vals[i];
		
		return this;
	}
	
	public Colour set(int channel, double value){
		data[channel] = value;
		return this;
	}
	
	public Colour scale(double amount){
		for(int i=0; i<data.length; i++)
			data[i]*=amount;
		
		return this;
	}
	
	public Colour convert(ColourSpace to){
		
		this.data = to.fromRGB(colourSpace.toRGB(data));
		this.colourSpace = to;
		
		return this;
	}
	
	public Colour convert(Colour c){
		return convert(c.getColourSpace());
	}
	
	//-----ACCESSORS
	
	public int to24bits(){
		return toColor().getRGB();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		String name = colourSpace.getClass().getName();
		
		sb.append(name.substring(name.lastIndexOf(".")+1));
		sb.append(": [");
		
		for(int i=0; i<data.length; i++){
			sb.append(data[i]);
			if(i!=data.length-1)sb.append(", ");
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	public int getDimensions(){
		return data.length;
	}
	
	public double[] getData(){
		return data.clone();
	}
	
	public ColourSpace getColourSpace(){
		return colourSpace;
	}
	
	
}
