package com.delta.vectors;

public class Cartesian extends Vector {

	/**
	 * Constructor for Cartesian
	 * 
	 * a = x
	 * b = y
	 * 
	 * @param x
	 * @param y
	 */
	public Cartesian(double x, double y) {
		super(x, y);
	}

	public Cartesian(Vector v) {
		this(v.getX(), v.getY());
	}

	@Override
	public Radial getRadial() {
		double r     = Math.hypot(a, b);
		double theta = Math.atan2(b, a);
		return new Radial(r,theta);
	}

	@Override
	public Cartesian getCartesian() {
		return this;
	}
	

	@Override
	public String toString(){
		return "(x="+a+", y="+b+")";
	}

}
