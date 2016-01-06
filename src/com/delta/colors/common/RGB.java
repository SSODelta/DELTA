package com.delta.colors.common;

import com.delta.colors.obscure.*;

public final class RGB extends Color {

	private static final long serialVersionUID = 6244192527349191537L;

	@Override
	public RGB toRGB() {
		return new RGB(getData());
	}

	public RGB(double[] rgb){
		super("rgb", rgb);
	}

	/**
	 * Constructs a new RGB Color object.
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 */
	public RGB(double r, double g, double b){
		this(new double[]{r,g,b});	
	}
	
	public RGB(int r, int g, int b){
		this(((double)r)/255.0,
			 ((double)g)/255.0,
			 ((double)b)/255.0);
	}
	
	public RGB(int rgb){
		this(new java.awt.Color(rgb).getRed(),
			 new java.awt.Color(rgb).getGreen(),
			 new java.awt.Color(rgb).getBlue());
	}
	
	
	/**
	 * Calculates the hue component based on R,G,B
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	private static double getHue(double r, double g, double b){
		double M = ColorUtil.max(r,g,b),
			   m = ColorUtil.min(r,g,b),
			   C = M - m,
			   hh = -1; //Error value
		
		if(C==0){
			hh = 0;
		} else if(M == r){
			hh = ((g - b) / C) % 6;
		} else if (M == g){
			hh = ((b - r) / C) + 2;
		} else if (M == b){
			hh = ((r - g) / C) + 4;
		}
		
		return 60*hh;
	}

	/**
	 * Converts an RGB Color object to an HSV Color object.
	 * Uses the algorithm described at:
	 * 
	 * 	https://en.wikipedia.org/wiki/HSL_and_HSV#Hue_and_chroma	(for calculating H, uses a hexagonal projection rather than spherical). 
	 */
	@Override
	public HSV toHSV() {
		
		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double M = ColorUtil.max(r,g,b),
			   m = ColorUtil.min(r,g,b);
		
		double C = M - m;
		
		double h = getHue(r,g,b);
		
		//In HSV, value V is just defined as V := M = max(r,g,b)
		double v = M;
		
		//In HSV, saturation S is defined as (src: https://en.wikipedia.org/wiki/HSL_and_HSV#Saturation)
		
		double s = 0.0;
		
		if(C != 0.0)
			s = C / v;
		
		return new HSV(h,s,v);
	}
	
	/**
	 * Converts an RGB Color object to an HSV Color object.
	 * Uses the algorithm described at:
	 * 
	 * 	https://en.wikipedia.org/wiki/HSL_and_HSV#Hue_and_chroma	(for calculating H, uses a hexagonal projection rather than spherical). 
	 */
	@Override
	public HSL toHSL(){
		
		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double M = ColorUtil.max(r, g, b),
			   m = ColorUtil.min(r, g, b);
		
		double C = M - m;
		
		double h = getHue(r,g,b);
		
		//The Lightness L component is defined as (src: https://en.wikipedia.org/wiki/HSL_and_HSV#Hue_and_chroma)
		double L = 0.5*(M + m);
		
		//In HSL, the Saturation S is defined as (src: https://en.wikipedia.org/wiki/HSL_and_HSV#Saturation)
		
		double s = 0.0;
		
		if(C != 0.0)
			s = C / (1 - Math.abs((2*L) - 1));
	
		return new HSL(h,s,L);
	}

	/**
	 * Converts an RGB Color object to an HSI Color object.
	 * Uses the algorithm detailed at
	 * 
	 * 	http://www.had2know.com/technology/hsi-rgb-color-converter-equations.html
	 * 
	 */
	@Override
	public HSI toHSI() {
		double r = get('r'),
			   g = get('g'),
			   b = get('b'),
			   I = (r+g+b)/3.0,
			   m = ColorUtil.min(r, g, b),
			   S = I > 0 ? 1 - m/I : 0,
			   hh = ColorUtil.acos( (r - 0.5*g - 0.5*b) / Math.sqrt(r*r + g*g + b*b - r*g - r*b - g*b));
		
		return new HSI(b > g ? 360-hh : hh,S,I);
	}

	/**
	 * Converts an RGB Color object to an HCY (Hue Chroma Luma) Color object.
	 * Uses the same definitions for hue and chroma as HSV, but Luma (Y) is defined differently (https://en.wikipedia.org/wiki/HSL_and_HSV#Lightness)
	 */
	@Override
	public HCY toHCY() {
		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double C = ColorUtil.max(r,g,b) - ColorUtil.min(r,g,b);
		
		double h = getHue(r,g,b);
		
		double yy601 = 0.30 * r + 0.59*g + 0.11*b;
		
		return new HCY(h,C,yy601);
	}

	/**
	 * Convert this RGB Color object to a CMYK Color Object.
	 * Uses the formulae at this website:
	 * 
	 * 	http://www.rapidtables.com/convert/color/rgb-to-cmyk.htm
	 */
	@Override
	public CMYK toCMYK() {
		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double k = 1-ColorUtil.max(r,g,b);
		
		double c = (1-r-k)/(1-k),
			   m = (1-g-k)/(1-k),
			   y = (1-b-k)/(1-k);
		
		return new CMYK(c,m,y,k);
	}

	@Override
	public YIQ toYIQ() {
		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double y = 0.299*r + 0.587*g + 0.114*b,
			   i = 0.596*r - 0.274*g - 0.322*b,
			   q = 0.211*r - 0.523*g + 0.312*b;
		
		return new YIQ(y,i,q);
	}	
	
	@Override
	public LMS toLMS(){

		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double l = 17.8824*r + 43.5161*g + 4.1193*b,
			   m =  3.4557*r + 27.1554*g + 3.8671*b,
			   s = 0.02996*r + 0.18431*g + 1.4670*b;
		
		return new LMS(l,m,s);
	}

	/**
	 * Converts this RGB Color object to a YUV Color object.
	 * Uses the implementation at (SDTV with BT.601):
	 * 
	 * 	https://en.wikipedia.org/wiki/YUV
	 * 
	 */
	@Override
	public YUV toYUV() {

		double r = get('r'),
			   g = get('g'),
			   b = get('b');
		
		double y =  0.299  *r + 0.587  *g + 0.114*b,
			   u = -0.14713*r - 0.28886*g + 0.436*b,
			   v =  0.615  *r - 0.51499*g - 0.10001*b;
		
		return new YUV(y*255.0, u*255.0, v*255.0);
	}

}
