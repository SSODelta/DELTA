package com.delta.vectors;

public class Radial extends Vector {

	/**
	 * Constructor for Radial
	 * 
	 * a = radius
	 * b = angle (theta)
	 * 
	 * @param radius
	 * @param theta
	 */
	public Radial(double radius, double theta) {
		super(radius, theta);
	}

	@Override
	public Radial getRadial() {
		return this;
	}

	@Override
	public Cartesian getCartesian() {
		double x = a * Math.cos(b);
		double y = a * Math.sin(b);
		return new Cartesian(x, y);
	}

	@Override
	public String toString(){
		return "(r="+a+", theta"+b+")";
	}

}
