package com.delta.colors.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.delta.colors.obscure.*;

/**
 * This class represents a Color object. A Color object consists of an 'n'-dimensional vector representing some Color value in a Color space.
 * A Color object makes no guarantees as to which color space it belongs to, so Colors should always be converted to the appropriate color spaces before usage (see .convert()).
 * Two Color objects are considered equal iff they are indistinguishable at 8 bits (as RGB objects).
 * 
 * Note that this class is immutable. It is not possible to change the state of a Color object after it has been declared.
 * @author ssodelta
 *
 */
public abstract class Color implements Serializable {
	
	public static BlendMode BLEND_MODE;
	
	/**
	 * Utility class for Color objects.
	 * @author ssodelta
	 *
	 */
	protected static class ColorUtil {
		
		public static final double ALMOST_ZERO = 0.00001;
		
		/**
		 * Checks if a given double is close to being zero.
		 * @param d
		 * @return
		 */
		public static boolean almostZero(double d){
			return Math.abs(d) <= ALMOST_ZERO;
		}
		
		/**
		 * Restricts a double x to an interval [min, max].
		 * @param x The input parameter
		 * @param min The minimum accepted value of 'x'. 
		 * @param max The maximum accepted value of 'x'. 
		 * @return
		 */
		public static double bound(double x, double min, double max){
			if(x<min)return min;
			if(x>max)return max;
			return x;
		}
		
		/**
		 * Returns the minimum value of some list of doubles.
		 * @param vals
		 * @return
		 */
		public static double min(double... vals){
			double min = Double.MAX_VALUE;
			
			for(double v : vals)
				if(v < min)
					min = v;
			
			return min;
		}
		
		/**
		 * Returns the maximum value of some list of doubles.
		 * @param vals
		 * @return
		 */
		public static double max(double... vals){
			double max = Double.MIN_VALUE;
			
			for(double v : vals)
				if(v > max)
					max = v;
			
			return max;
		}
		
		/**
		 * Computes cosine for some angle represented in degrees [0, 360].
		 * @param deg An angle in degrees.
		 * @return 
		 */
		public static double cos(double deg){
			return Math.cos(Math.toRadians(deg));
		}
		
		/**
		 * Computes arccosine for some cos(x). 
		 * @param deg cos(x)
		 * @return The angle represented by arccos(cos(x)) in degrees.
		 */
		public static double acos(double deg){
			return Math.toDegrees(Math.acos(deg));
		}
	}
	
	/**
	 * Blends another Color with this Color object. It is assumed that 'this' is the target, and that 'o' is the blend.
	 * Uses the global static BlendMode BLEND_MODE. To use a custom BlendMode, use .blend(Color o, BlendMode mode) instead;
	 * Follows the implementation at:
	 * 
	 * http://www.deepskycolors.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html
	 * 
	 * @param o The blend
	 * @return The blended color, has same class as 'this'.
	 */
	public Color blend(Color o){
		return blend(o, BLEND_MODE);
	}
	
	/**
	 * Blends another Color with this Color object. It is assumed that 'this' is the target, and that 'o' is the blend.
	 * Follows the implementation at:
	 * 
	 * http://www.deepskycolors.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html
	 * 
	 * @param o The blend
	 * @return The blended color, has same class as 'this'.
	 */
	public Color blend(Color o, BlendMode mode){
		return blendColors(mode, this, o);
	}
	
	private static Color blendColors(BlendMode mode, Color target, Color blend){
		
		Color a = target,
			  b = blend.convert(a.getClass());	//Make sure the colors are the same class
		
		int n = a.getDimensions();
		
		double[] c = new double[n];
		
		for(int i=0; i<n; i++)
			c[i] = mode.combine(a.data[i], b.data[i]);
		
		return Color.fromClass(target.getClass(), c);
	}
	
	private static final long serialVersionUID = -4625956113710955788L;
	
	private final double[] data;
	private final Map<Character, Integer> labelIndices;
	private final String name;
	
	/**
	 * Constructs a new Color object.
	 * @param labels List of labels for the Color dimensions. One character per dimension.
	 * @param vals The data for this color object.
	 */
	protected Color(String labels, double[] vals){
		
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
	 * Gets (a clone of) the data associated with this Color object.
	 * Keep in mind that .getData() makes no guarantees which color space it represents.
	 * To make sure you get sensible values from this function be sure to convert Color objects to a known color space first.
	 * @return The data associated with this Color object.
	 */
	public double[] getData(){
		double[] r = new double[data.length];
		for(int i=0; i<r.length; i++)
			r[i] = data[i];
		return r;
	}
	
	public RGB xor(Color c){
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
	 * Returns the name of the color space this Color-object represents.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the length of the vector representing the values of the color channels in this Color object.
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
	 * Gets the data of the color channel labelled by character 'c'. 
	 * For instance, suppose we have some RGB object:
	 * 
	 * Color rgb = new RGB(0.5,0,0);
	 * 
	 * Then rgb.get('r') would return 0.5. This method throws an IllegalArgumentException is an invalid color channel is given.
	 * @param c A color channel
	 * @return The value at 'c'.
	 */
	protected double get(char c){
		char lowerCase = (""+c).toLowerCase().charAt(0);
		
		if(!labelIndices.containsKey(lowerCase))
			throw new IllegalArgumentException("No such color channel '"+lowerCase+"' in color scheme "+getName());
		
		return get(labelIndices.get(lowerCase));
	}
	
	/**
	 * Constructs a Color-object from a java.awt.Color-object
	 * @param c An AWT Color object.
	 * @return A DELTA Color object.
	 */
	public static RGB fromAWTColor(java.awt.Color c){
		return new RGB(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	/**
	 * Construct a java.awt.Color-object from this Color-object.
	 * @return An AWT Color object.
	 */
	public java.awt.Color toAWTColor(){
		RGB rgb = this.toRGB();
		
		return new java.awt.Color((float)ColorUtil.bound(rgb.get('r'),0,1), (float)ColorUtil.bound(rgb.get('g'),0,1), (float)ColorUtil.bound(rgb.get('b'),0,1));
	}
	
	/**
	 * Converts this Color-object its Protanope representation. This is the way dogs see colors.
	 * @return A Color in the LMS color space, representing this Color as seen by dogs and color deficient peoples.
	 */
	public LMS toProtanopeColor() {
		return toLMS().toProtanopeColor();
	}
	
	/**
	 * Returns a 24-bit integer representing this Color object.
	 * @return 0xRRGGBB
	 */
	public int toInteger(){
		return toAWTColor().getRGB();
	}
	
	@Override
	public int hashCode(){
		int[] rgb = getRGB();
		return Objects.hash(rgb[0], rgb[1], rgb[2]);
	}
	
	/**
	 * Gets an integer array representing this Color object as 8-bit RGB colors.
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
	 * Check whether or not this Color is equal to another Object.
	 * Two colors are considered equal if their 8-bit integer RGB components are equal.
	 */
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Color))return false;
		
		int[] thisRGB  = this.getRGB(),
		      otherRGB = ((Color)o).getRGB();
		
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
	 * Adds this Color object A to another Color object B and returns the sum of A and B.
	 * The type of A+B is the same as A.
	 * @param c Some Color
	 * @return The sum of A and B, the type being the same as the type of this.
	 */
	public Color add(Color c){
		Color other = c.convert(this.getClass());
		
		double[] addData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			addData[i] = data[i] + other.data[i];
		
		return Color.fromClass(this.getClass(), addData);
	}
	
	/**
	 * Adds this Color object A to another Color object B and returns the difference between A and B.
	 * The type of A-B is the same as A.
	 * @param c Some Color
	 * @return The difference between A and B, the type being the same as the type of this.
	 */
	public Color sub(Color c){
		Color other = c.convert(this.getClass());
		
		double[] addData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			addData[i] = data[i] - other.data[i];
		
		return Color.fromClass(this.getClass(), addData);
	}
	
	/**
	 * Scales this Color object by some scalar.
	 * Returns an Object the same type as 'this'.
	 * @param amount The scalar
	 * @return
	 */
	public Color scale(double amount){
		double[] scaleData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			scaleData[i] = amount * data[i];
		
		return Color.fromClass(this.getClass(), scaleData);
	}
	
	/**
	 * Constructs a Color object from a given class and data (factory method).
	 * @param c The class of the Color to convert this object to.
	 * @param data A vector of the data.
	 * @return
	 */
	public static Color fromClass(Class<? extends Color> c, double[] data){
		Color color = null;
		
		if(c == RGB.class)
			color = new RGB(data);
			
		else if(c == HSV.class)
			color = new HSV(data);
			
		else if(c == HSL.class)
			color = new HSL(data);
			
		else if(c == HCY.class)
			color = new HCY(data);
			
		else if(c == HSI.class)
			color = new HSI(data);
		
		else if(c == CMYK.class)
			color = new CMYK(data);
		
		else if(c == YIQ.class)
			color = new YIQ(data);
		
		else if(c == YUV.class)
			color = new YUV(data);
		
		else if(c == LMS.class)
			color = new LMS(data);
		
		return color;
	}
	
	/**
	 * Adds a vector to this Color. The length of the vector must be equal to the number of color channels of this Color object.
	 * Keep in mind that .add() makes no guarantees which color space it represents.
	 * To make sure you get sensible values from this function be sure to convert Color objects to a known color space first.
	 * @param vals A vector
	 * @return The sum of this Color object and the vector 'vals'.
	 */
	public Color add(double... vals){
		if(vals.length != data.length)
			throw new IllegalArgumentException("Length of vals must be equal to length of data ("+vals.length+" vs. "+data.length+")");
		
		double[] newData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			newData[i] = data[i] + vals[i];
		
		return Color.fromClass(this.getClass(), newData);
	}
	
	/**
	 * Substracts a vector from this Color. The length of the vector must be equal to the number of color channels of this Color object.
	 * Keep in mind that .sub() makes no guarantees which color space it represents.
	 * To make sure you get sensible values from this function be sure to convert Color objects to a known color space first.
	 * @param vals A vector
	 * @return The sum of this Color object and the vector 'vals'.
	 */
	public Color sub(double... vals){
		if(vals.length != data.length)
			throw new IllegalArgumentException("Length of vals must be equal to length of data ("+vals.length+" vs. "+data.length+")");
		
		double[] newData = new double[data.length];
		
		for(int i=0; i<data.length; i++)
			newData[i] = data[i] - vals[i];
		
		return Color.fromClass(this.getClass(), newData);
	}
	
	/**
	 * Converts this Color to the same class as 'c'.
	 * @param c A Color class representing the desired output Color subtype.
	 * @return This color, in the color space 'c'.
	 */
	public Color convert(Class<? extends Color> c){
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
	 * Converts this object to the RGB color space.
	 * @return
	 */
	public abstract RGB  toRGB();
	
	/**
	 * Converts this object to the HSV color space.
	 * @return
	 */
	public abstract HSV  toHSV();
	
	/**
	 * Converts this object to the HSL color space.
	 * @return
	 */
	public abstract HSL  toHSL();
	
	/**
	 * Converts this object to the HCY color space.
	 * @return
	 */
	public abstract HCY  toHCY();
	
	/**
	 * Converts this object to the HSI color space.
	 * @return
	 */
	public abstract HSI  toHSI();
	
	/**
	 * Converts this object to the CMYK color space.
	 * @return
	 */
	public abstract CMYK toCMYK();
	
	/**
	 * Converts this object to the YIQ color space.
	 * @return
	 */
	public abstract YIQ  toYIQ();
	
	/**
	 * Converts this object to the LMS color space.
	 * @return
	 */
	public abstract LMS  toLMS();
	
	/**
	 * Converts this object to the YUV color space.
	 * @return
	 */
	public abstract YUV  toYUV();

}
