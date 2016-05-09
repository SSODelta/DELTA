package com.delta2.math.func;

import com.delta2.math.Complex;

public interface Function {

	public Complex evaluate(Complex x);
	
	public double evaluate(double x);
	
	public String toLaTeX();
	
}
