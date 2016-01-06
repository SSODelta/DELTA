package com.delta.colors.obscure;

import com.delta.colors.common.RGB;

public final class HSI extends HueColor {

	private static final long serialVersionUID = 6958273792182812813L;

	@Override
	public HSI toHSI(){
		return new HSI(getData());
	}
	
	public HSI(double[] vals) {
		super("hsi", vals);
	}
	
	public HSI(double h, double s, double l){
		this(new double[]{h,s,l});
	}

	/**
	 * Converts an HSI Color object to an RGB Color object.
	 * Uses the implementation at:
	 * 
	 * 	http://www.had2know.com/technology/hsi-rgb-color-converter-equations.html
	 * 
	 */
	@Override
	public RGB toRGB() {
		double h = get('h'),
			   s = get('s'),
			   i = get('i'),
			   r = 0.0, g = 0.0, b = 0.0;
		
		if(h == 0.0){
			r = i + 2*i*s;
			g = i - i*s;
			b = i - i*s;
		} else if(0 < h && h < 120){
			r = i + i*s*(ColorUtil.cos(h)/ColorUtil.cos(60-h));
			g = i + i*s*(1-ColorUtil.cos(h)/ColorUtil.cos(60-h));
			b = i - i*s;
		} else if(h == 120){
			r = i - i*s;
			g = i + 2*i*s;
			b = i - i*s;
		} else if(120 < h && h < 240){
			r = i - i*s;
			g = i + i*s*(ColorUtil.cos(h-120)/ColorUtil.cos(180-h));
			b = i + i*s*(1-ColorUtil.cos(h-120)/ColorUtil.cos(180-h));
		} else if(h == 240){
			r = i - i*s;
			g = i - i*s;
			b = i + 2*i*s;
		} else if(240 < h && h < 360){
			r = i + i*s*(1-ColorUtil.cos(h-240)/ColorUtil.cos(300-h));
			g = i - i*s;
			b = i + i*s*(ColorUtil.cos(h-240)/ColorUtil.cos(300-h));
		}
		
		return new RGB(r,g,b);
	}

}
