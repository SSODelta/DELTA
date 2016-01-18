package com.delta2.math;

public class Polynomial {
	public double[] terms;
	
	/**
	 * Instantiate a new Polynomial-object with a specified number of terms.
	 * @param terms The number of terms in this polynomial. Keep in mind, that if terms = 2, then it's a linear equation.
	 */
	public Polynomial(double[] terms){
		this.terms = terms;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		for(int i=terms.length; i>0; i--){
			if(terms[(i-1)] == 0)continue;
			if(i!=terms.length){
				if(terms[(i-1)] > 0){
					s.append(" + ");
				} else {
					s.append(" - ");
				}
			}
			if(i-1 == 0){
				s.append(numToString(Math.abs(terms[(i-1)])));
				continue;
			}
			if(i-1 == 1){
				s.append(numToString(Math.abs(terms[(i-1)])) + " * x");
				continue;
			}
			if(terms[(i-1)]==1)s.append("x^"+(i-1));
			s.append(numToString(Math.abs(terms[(i-1)])) + " * x^"+(i-1));
		}
		
		String k = s.toString();
		
		if(k.startsWith(" + "))return k.substring(3);
		
		return k;
	}
	
	/**
	 * Rounds off a double-object to two significant digits and returns it as a String-object.
	 * @param k The double to convert.
	 * @return A String-object representing the double.
	 */
	public String numToString(double k){
		if(Math.floor(k) == k){
			return "" + (int)k;
		}
		return "" + Complex.roundOff(k, 2);
	}
	
	/**
	 * Returns a new Polynomial-object which is the derivate of this current Polynomial.
	 * @return The derivate of this polynomial.
	 */
	public Polynomial derivative(){
		if(terms == null)return null;
		double[] newTerms = new double[terms.length-1];
		
		for(int i=0; i<newTerms.length; i++){
			newTerms[i] = terms[i+1] * (i+1);
		}
		
		return new Polynomial(newTerms);
	}
	
	/**
	 * Returns a new Polynomial-object of a specified length with random terms.
	 * @param length The number of terms of the Polynomial.
	 * @return A new Polynomial-object.
	 */
	public static Polynomial random(int length){
		double[] terms = new double[length];
		
		for(int i=0; i<length; i++){
			terms[i] = Complex.roundOff((Math.pow(Math.random(), 4) *20)*(1+(Math.round(Math.random())*-2)),2);
		}
		
		return new Polynomial(terms);
	}
	
	/**
	 * Get the function value for a given x. That is, if this polynomial is the function f, then this return f(x).
	 * @param x The complex argument for the polynomial.
	 * @return f(x).
	 */
	public Complex getValue(Complex x){
		Complex y = Complex.ZERO;
		for(int i=0; i<terms.length; i++){
			if(i==0){
				y = y.add(terms[0]);
				continue;
			}
			if(terms[i] == 0)continue;
			y = y.add(
					x.pow(i).multiply(terms[i]));
		}
		return y;
	}
	
	/**
	 * Get the function value for a given x. That is, if this polynomial is the function f, then this return f(x).
	 * @param x The real argument for the polynomial.
	 * @return f(x).
	 */
	public double getValue(double x){
		double y = 0;
		for(int i=0; i<terms.length; i++){
			if(i==0){
				y += terms[0];
				continue;
			}
			if(terms[i]==0)continue;
			y += terms[i] * Math.pow(x, i);
		}
		return y;
	}
}