package com.delta.colours.rare;

import com.delta.colours.common.*;

/**
 * DefaultColor is an adapter-class for any Color that is not RGB, written to save space.
 * Implements all conversions for this Color through the RGB object.
 * So calling .toHSV() on this object calls .toRGB().toHSV().
 * @author ssodelta
 *
 */
public abstract class DefaultColour extends Colour {

	private static final long serialVersionUID = 11157800352775219L;

	protected DefaultColour(String labels, double[] vals){
		super(labels,vals);
	}

	@Override
	public HSV toHSV() {
		// TODO Auto-generated method stub
		return toRGB().toHSV();
	}

	@Override
	public HSL toHSL() {
		return toRGB().toHSL();
	}

	@Override
	public HSI toHSI() {
		return toRGB().toHSI();
	}

	@Override
	public HCY toHCY() {
		return toRGB().toHCY();
	}

	@Override
	public CMYK toCMYK() {
		return toRGB().toCMYK();
	}

	@Override
	public YIQ toYIQ() {
		return toRGB().toYIQ();
	}
	
	@Override
	public LMS toLMS() {
		return toRGB().toLMS();
	}

	@Override
	public YUV toYUV() {
		return toRGB().toYUV();
	}
}
