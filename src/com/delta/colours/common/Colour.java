package com.delta.colours.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.delta.colours.rare.*;
import com.delta.colours.util.ColourUtil;

/**
 * This class represents a Colour object. A Colour object consists of an 'n'-dimensional vector representing some Colour value in a Colour space.
 * A Colour object makes no guarantees as to which Colour space it belongs to, so Colours should always be converted to the appropriate Colour spaces before usage (see .convert()).
 * Two Colour objects are considered equal iff they are indistinguishable at 8 bits (as RGB objects).
 * 
 * Note that this class is immutable. It is not possible to change the state of a Colour object after it has been declared.
 * @author ssodelta
 *
 */
public abstract class Colour implements Serializable {
	
	public static BlendMode BLEND_MODE;
	
	/**
	 * Blends another Colour with this Colour object. It is assumed that 'this' is the target, and that 'o' is the blend.
	 * Uses the global static BlendMode BLEND_MODE. To use a custom BlendMode, use .blend(Colour o, BlendMode mode) instead;
	 * Follows the implementation at:
	 * 
	 * http://www.deepskyColours.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html
	 * 
	 * @param o The blend
	 * @return The blended Colour, has same class as 'this'.
	 */
	public Colour blend(Colour o){
		return blend(o, BLEND_MODE);
	}
	
	/**
	 * Blends another Colour with this Colour object. It is assumed that 'this' is the target, and that 'o' is the blend.
	 * Follows the implementation at:
	 * 
	 * http://www.deepskyColours.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html
	 * 
	 * @param o The blend
	 * @return The blended Colour, has same class as 'this'.
	 */
	public Colour blend(Colour o, BlendMode mode){
		return blendColours(mode, this, o);
	}
	
	private static Colour blendColours(BlendMode mode, Colour target, Colour blend){
		
		Colour a = target,
			  b = blend.convert(a.getClass());	//Make sure the Colours are the same class
		
		int n = a.getDimensions();
		
		double[] c = new double[n];
		
		for(int i=0; i<n; i++)
			c[i] = mode.combine(a.data[i], b.data[i]);
		
		return Colour.fromClass(target.getClass(), c);
	}
	
	private static final long serialVersionUID = -4625956113710955788L;
	
	private final double[] data;
	private final Map<Character, Integer> labelIndices;
	private final String name;
	
	/**
	 * Constructs a new Colour object.
	 * @param labels List of labels for the Colour dimensions. One character per dimension.
	 * @param vals The data for this Colour object.
	 */
	protected Colour(String labels, double[] vals){
		
		int n = labels.length();
		
		if(vals.length != n)
			throw new IllegalArgumentException("Must be as many values as labels (n="+n+").");
		
		labelIndices = new HashMap<Character, Integer>();
		
		this.name = labels.toUpperCase();
		data = vals;
		
		for(int i=0; i<n; i++)
			labelIndices.put(labels.toLowerCase().charAt(i), i);
		
	}

	/**
	 * Gets (a clone of) the data associated with this Colour object.
	 * Keep in mind that .getData() makes no guarantees which Colour space it represents.
	 * To make sure you get sensible values from this function be sure to convert Colour objects to a known Colour space first.
	 * @return The data associated with this Colour object.
	 */
	public double[] getData(){
		double[] r = new double[data.length];
		for(int i=0; i<r.length; i++)
			r[i] = data[i];
		return r;
	}
	
	public RGB xor(Colour c){
		int[] r1 = this.toRGB().getRGB(),
			  r2 = c.toRGB().getRGB();
		
		return new RGB( r1[0] ^ r2[0],
						r1[1] ^ r2[1],
						r1[2] ^ r2[2]);
	}
	
	public HSL invertLightness(){
		HSL hsl = this.toHSL();
		
		return new HSL(hsl.get('h'), hsl.get('s'), 1.0-hsl.get('l'));
	}
	
	/**
	 * Returns the name of the Colour space this Colour-object represents.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the length of the vector representing the values of the Colour channels in this Colour object.
	 * @return The number of dimensions
	 */
	public int getDimensions(){
		return data.length;
	}
	
	/**
	 * Gets the data at position 'i' in the data. Is identical to .getData()[i];
	 * @param i Index i
	 * @return
	 */
	protected double get(int i){
		return data[i];
	}
	
	/**
	 * Gets the data of the Colour channel labelled by character 'c'. 
	 * For instance, suppose we have some RGB object:
	 * 
	 * Colour rgb = new RGB(0.5,0,0);
	 * 
	 * Then rgb.get('r') would return 0.5. This method throws an IllegalArgumentException is an invalid Colour channel is given.
	 * @param c A Colour channel
	 * @return The value at 'c'.
	 */
	protected double get(char c){
		char lowerCase = (""+c).toLowerCase().charAt(0);
		
		if(!labelIndices.containsKey(lowerCase))
			throw new IllegalArgumentException("No such Colour channel '"+lowerCase+"' in Colour scheme "+getName());
		
		return get(labelIndices.get(lowerCase));
	}
	
	/**
	 * Constructs a Colour-object from a java.awt.Color-object
	 * @param c An AWT Colour object.
	 * @return A DELTA Colour object.
	 */
	public static RGB fromAWTColour(java.awt.Color c){
		return new RGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	/**
	 * Construct a java.awt.Color-object from this Colour-object.
	 * @return An AWT Colour object.
	 */
	public java.awt.Color toAWTColour(){
		RGB rgb = this.toRGB();
		
		return new java.awt.Color((float)ColourUtil.bound(rgb.get('r'),0,1), (float)ColourUtil.bound(rgb.get('g'),0,1), (float)ColourUtil.bound(rgb.get('b'),0,1));
	}
	
	/**
	 * Converts this Colour-object its Protanope representation. This is the way dogs see Colours.
	 * @return A Colour in the LMS Colour space, representing this Colour as seen by dogs and Colour deficient peoples.
	 */
	public LMS toProtanopeColour() {
		return toLMS().toProtanopeColour();
	}
	
	/**
	 * Returns a 24-bit integer representing this Colour object.
	 * @return 0xRRGGBB
	 */
	public int toInteger(){
		return toAWTColour().getRGB();
	}
	
	@Override
	public int hashCode(){
		int[] rgb = getRGB();
		return Objects.hash(rgb[0], rgb[1], rgb[2]);
	}
	
	/**
	 * Gets an integer array representing this Colour object as 8-bit RGB Colours.
	 * @return 
	 */
	public int[] getRGB(){
		RGB rgb = toRGB();
		
		int r = (int)Math.round(255*rgb.get('r')),
			g = (int)Math.round(255*rgb.get('g')),
			b = (int)Math.round(255*rgb.get('b'));
		
		return new int[]{r,g,b};
	}

	/**
	 * Check whether or not this Colour is equal to another Object.
	 * Two Colours are considered equal if their 8-bit integer RGB components are equal.
	 */
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Colour))return false;
		
		int[] thisRGB  = this.getRGB(),
		      otherRGB = ((Colour)o).getRGB();
		
		for(int i=0; i<3; i++)
			if(thisRGB[i] != otherRGB[i])return false;
		
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(getName());
		sb.append(": [");
		
		for(int i=0; i<data.length; i++){
			sb.append(data[i]);
			if(i!=data.length-1)
				sb.append(", ");
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Adds this Colour object A to another Colour object B and returns the sum of A and B.
	 * The type of A+B is the same as A.
	 * @param c Some Colour
	 * @return The sum of A and B, the type being the same as the type of this.
	 */
	public Colour add(Colour c){
		Colour other = c.convert(this.getClass());
		
		double[] addData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			addData[i] = data[i] + other.data[i];
		
		return Colour.fromClass(this.getClass(), addData);
	}
	
	/**
	 * Adds this Colour object A to another Colour object B and returns the difference between A and B.
	 * The type of A-B is the same as A.
	 * @param c Some Colour
	 * @return The difference between A and B, the type being the same as the type of this.
	 */
	public Colour sub(Colour c){
		Colour other = c.convert(this.getClass());
		
		double[] addData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			addData[i] = data[i] - other.data[i];
		
		return Colour.fromClass(this.getClass(), addData);
	}
	
	/**
	 * Scales this Colour object by some scalar.
	 * Returns an Object the same type as 'this'.
	 * @param amount The scalar
	 * @return
	 */
	public Colour scale(double amount){
		double[] scaleData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			scaleData[i] = amount * data[i];
		
		return Colour.fromClass(this.getClass(), scaleData);
	}
	
	/**
	 * Constructs a Colour object from a given class and data (factory method).
	 * @param c The class of the Colour to convert this object to.
	 * @param data A vector of the data.
	 * @return
	 */
	public static Colour fromClass(Class<? extends Colour> c, double[] data){
		Colour Colour = null;
		
		if(c == RGB.class)
			Colour = new RGB(data);
			
		else if(c == HSV.class)
			Colour = new HSV(data);
			
		else if(c == HSL.class)
			Colour = new HSL(data);
			
		else if(c == HCY.class)
			Colour = new HCY(data);
			
		else if(c == HSI.class)
			Colour = new HSI(data);
		
		else if(c == CMYK.class)
			Colour = new CMYK(data);
		
		else if(c == YIQ.class)
			Colour = new YIQ(data);
		
		else if(c == YUV.class)
			Colour = new YUV(data);
		
		else if(c == LMS.class)
			Colour = new LMS(data);
		
		return Colour;
	}
	
	/**
	 * Adds a vector to this Colour. The length of the vector must be equal to the number of Colour channels of this Colour object.
	 * Keep in mind that .add() makes no guarantees which Colour space it represents.
	 * To make sure you get sensible values from this function be sure to convert Colour objects to a known Colour space first.
	 * @param vals A vector
	 * @return The sum of this Colour object and the vector 'vals'.
	 */
	public Colour add(double... vals){
		if(vals.length != data.length)
			throw new IllegalArgumentException("Length of vals must be equal to length of data ("+vals.length+" vs. "+data.length+")");
		
		double[] newData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			newData[i] = data[i] + vals[i];
		
		return Colour.fromClass(this.getClass(), newData);
	}
	
	/**
	 * Substracts a vector from this Colour. The length of the vector must be equal to the number of Colour channels of this Colour object.
	 * Keep in mind that .sub() makes no guarantees which Colour space it represents.
	 * To make sure you get sensible values from this function be sure to convert Colour objects to a known Colour space first.
	 * @param vals A vector
	 * @return The sum of this Colour object and the vector 'vals'.
	 */
	public Colour sub(double... vals){
		if(vals.length != data.length)
			throw new IllegalArgumentException("Length of vals must be equal to length of data ("+vals.length+" vs. "+data.length+")");
		
		double[] newData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			newData[i] = data[i] - vals[i];
		
		return Colour.fromClass(this.getClass(), newData);
	}
	
	/**
	 * Converts this Colour to the same class as 'c'.
	 * @param c A Colour class representing the desired output Colour subtype.
	 * @return This Colour, in the Colour space 'c'.
	 */
	public Colour convert(Class<? extends Colour> c){
		if(c == RGB.class){
			return toRGB();
		} else if(c == HSV.class){
			return toHSV();
		} else if(c == HSL.class){
			return toHSL();
		} else if(c == HCY.class){
			return toHCY();
		} else if(c == HSI.class){
			return toHSI();
		} else if(c == CMYK.class){
			return toCMYK();
		} else if(c == YIQ.class){
			return toYIQ();
		} else if(c == YUV.class){
			return toYUV();
		} else if(c == LMS.class){
			return toLMS();
		}
		
		return null;
	}

	/**
	 * Converts this object to the RGB Colour space.
	 * @return
	 */
	public abstract RGB  toRGB();
	
	/**
	 * Converts this object to the HSV Colour space.
	 * @return
	 */
	public abstract HSV  toHSV();
	
	/**
	 * Converts this object to the HSL Colour space.
	 * @return
	 */
	public abstract HSL  toHSL();
	
	/**
	 * Converts this object to the HCY Colour space.
	 * @return
	 */
	public abstract HCY  toHCY();
	
	/**
	 * Converts this object to the HSI Colour space.
	 * @return
	 */
	public abstract HSI  toHSI();
	
	/**
	 * Converts this object to the CMYK Colour space.
	 * @return
	 */
	public abstract CMYK toCMYK();
	
	/**
	 * Converts this object to the YIQ Colour space.
	 * @return
	 */
	public abstract YIQ  toYIQ();
	
	/**
	 * Converts this object to the LMS Colour space.
	 * @return
	 */
	public abstract LMS  toLMS();
	
	/**
	 * Converts this object to the YUV Colour space.
	 * @return
	 */
	public abstract YUV  toYUV();

}