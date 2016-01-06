package com.delta.colors.obscure;

import com.delta.colors.common.RGB;

public final class YUV extends DefaultColor {

	private static final long serialVersionUID = 3905644599112330401L;

	@Override
	public YUV toYUV(){
		return new YUV(getData());
	}
	
	public YUV(double[] vals) {
		super("yuv", vals);
	}

	public YUV(double y, double u, double v){
		this(new double[]{y,u,v});
	}

	/**
	 * Converts this YUV Color object to an RGB Color object.
	 * Uses the implementation at (SDTV with BT.601):
	 * 
	 * 	https://en.wikipedia.org/wiki/YUV
	 * 
	 */
	@Override
	public RGB toRGB() {
		double y = get('y'),
			   u = get('u'),
			   v = get('v');
		
		double r = (y + 1.13983*v)/255.0,
			   g = (y - 0.39465*u - 0.58060*v)/255.0,
			   b = (y + 2.03211*u)/255.0;
		
		return new RGB(r,g,b);
	}
	
}
