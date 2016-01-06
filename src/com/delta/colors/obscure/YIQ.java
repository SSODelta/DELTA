package com.delta.colors.obscure;

import com.delta.colors.common.RGB;

public final class YIQ extends DefaultColor {

	private static final long serialVersionUID = 3065145711461317863L;

	@Override
	public YIQ toYIQ(){
		return new YIQ(getData());
	}
	
	public YIQ(double[] vals) {
		super("yiq", vals);
	}
	
	public YIQ(double y, double i, double q){
		this(new double[]{y,i,q});
	}

	@Override
	public RGB toRGB() {
		
		double y = get('y'),
			   i = get('i'),
			   q = get('q');
		
		double r = y + 0.956*i + 0.621*q,
			   g = y - 0.272*i - 0.647*q,
			   b = y - 1.106*i + 1.703*q;
		
		return new RGB(r,g,b);
	}

}
