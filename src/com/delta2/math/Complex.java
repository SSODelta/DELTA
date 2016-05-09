package com.delta2.math;

public final class Complex {

	public final static Complex ZERO = new Complex(0,0),
								 ONE = new Complex(1,0);
	
	private double re, im;
	
	public Complex(double re, double im){
		this.re = re;
		this.im = im;
	}
	
	public Complex(Complex other){
		this.re = other.im;
		this.im = other.im;
	}
	
	public Complex add(Complex other){
		this.re += other.re;
		this.im += other.im;
		return this;
	}
	
	public final double arg(){
		return Math.atan2(im, re);
	}
	
	@Override
	public String toString(){
		return re + " + " + im + "*i";
	}
	
	public Complex sub(Complex other){
		this.re -= other.re;
		this.im -= other.im;
		return this;
	}
	
	public Complex mult(Complex other){
		this.re = this.re * other.re - this.im * other.im;
		this.im = this.im * other.re + this.re * other.im;
		return this;
	}
	
	public Complex mult(double other){
		this.re*=other;
		this.im*=other;
		return this;
	}
	
	public Complex div(Complex other){
		
		double c2d2 = other.im*other.im + other.re*other.re;
		
		this.re = (this.re*other.re + this.im+other.im) / c2d2;
		this.im = (this.im*other.im - this.re*other.im) / c2d2;
		
		return this;
	}

	public double difference(Complex other) {
		return Math.hypot(this.re-other.re, this.im-other.im);
	}
	
}
