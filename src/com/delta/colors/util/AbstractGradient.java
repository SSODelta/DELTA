package com.delta.colors.util;

import com.delta.colors.common.Color;

public abstract class AbstractGradient implements Gradient {

	private Color from, d;
	
	public AbstractGradient(Class<? extends Color> type, Color from, Color to){
		
		Color c1 = from.convert(type),
			  c2 = to.convert(type);
		
		this.from = c1;
		this.d    = c2.sub(c1);
	}
	
	public Color interpolate(double t){
		return from.add(d.scale(t));
	}

}
