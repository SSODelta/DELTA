package com.delta2.math.func;

import com.delta2.math.Complex;

public final class Polynomial implements Differentiable {

	private double[] coeffs;
	
	public Polynomial(double[] coeffs){
		this.coeffs = coeffs;
	}
	
	public Polynomial(Polynomial p){
		this(p.coeffs.clone());
	}

	@Override
	public Complex evaluate(Complex x) {
		Complex xx = Complex.ONE,
				y  = Complex.ZERO;
		
		for(int i=0; i<coeffs.length; i++){
			y.add(new Complex(xx).mult(coeffs[i]));
			if(i!=coeffs.length-1)
				xx.mult(x);
		}
		
		return y;
	}
	
	public static final Polynomial random(int len){
		double[] coeffs = new double[len];
		
		for(int i=0; i<len; i++)
			coeffs[i] = Math.random()*(Math.random()*2-1)*10;
		
		return new Polynomial(coeffs);
	}

	@Override
	public double evaluate(double x) {
		double xx = 1,
				y = 0;
		
		for(int i=0; i<coeffs.length; i++){
			y+=coeffs[i]*xx;
			if(i!=coeffs.length-1)
				xx *= x;
		}
		
		return y;
	}
	
	@Override
	public String toLaTeX() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polynomial derivative() {
		double[] newCoeffs = new double[coeffs.length-1];
		
		for(int i=0; i<newCoeffs.length; i++)
			newCoeffs[i] = coeffs[i+1] * (i+1);
		
		return new Polynomial(newCoeffs);
	}

	public Polynomial add(double... vals){
		if(vals.length > coeffs.length)
			throw new IllegalArgumentException("Cannot add a vector with larger size than the number of terms in the polynomial.");
		
		for(int i=0; i<vals.length; i++)
			coeffs[i] += vals[i];
		
		return this;
	}


}
