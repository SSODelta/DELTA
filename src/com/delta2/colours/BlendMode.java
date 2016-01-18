package com.delta2.colours;

public interface BlendMode {

	double combine(double a, double b);
	
	
	public static final BlendMode NORMAL       = (a,b) -> b,
					              MULTIPLY     = (a,b) -> a*b,
					              SCREEN       = (a,b) -> 1-(1-a)*(1-b),
					              DARKEN       = (a,b) -> Math.min(a, b),
							      COLOR_BURN   = (a,b) -> 1 - (1-a) / b,
							      LINEAR_BURN  = (a,b) -> a+b-1,
							      LIGHTEN      = (a,b) -> Math.max(a, b),
							      COLOR_DODGE  = (a,b) -> a / (1-b),
							      LINEAR_DODGE = (a,b) -> a+b,
							      XOR		   = (a,b) -> ((int)(a*255) ^ (int)(b*255)) / 255.0,
							      OVERLAY      = (a,b) -> a>0.5 ? 1-(1-2*(a-0.5))*(1-b)  : 2*a*b,
							      SOFT_LIGHT   = (a,b) -> b>0.5 ? 1-(1-a)*(1-(b-0.5))    : a*(b+0.5),
							      HARD_LIGHT   = (a,b) -> b>0.5 ? 1-(1-a)*(1-2*(b-0.5))  : 2*a*b,
							      VIVID_LIGHT  = (a,b) -> b>0.5 ? 1-(1-a)/(2*(b-0.5))    : a / (1-2*b),
							      LINEAR_LIGHT = (a,b) -> b>0.5 ? a + 2*(b-0.5)          : a / (1-2*b),
							      PIN_LIGHT    = (a,b) -> b>0.5 ? Math.max(a, 2*(b-0.5)) : Math.min(a, 2*b),
							      DIFFERENCE   = (a,b) -> Math.abs(a-b),
							      EXCLUSION    = (a,b) -> 0.5 - 2*(a-0.5)*(b-0.5);
}
