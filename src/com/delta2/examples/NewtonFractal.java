package com.delta2.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;
import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.generation.Generator;
import com.delta2.math.Complex;
import com.delta2.math.Polynomial;

public final class NewtonFractal extends Generator {

	private Polynomial p, deriv;
	
	private int r;
	
	
	public NewtonFractal(Polynomial p, int radius){
		super();
		this.p = p;
		this.r = radius;
		this.deriv = p.derivative();
	}

	@Override
	protected Colour generate(double x, double y, double t) {
		
		Complex guess = new Complex(-r+x*2*r, -r+y*2*r);
		double err = 1;
		
		int tries = 1;
		
		while(err > ColourUtil.ALMOST_ZERO){
			
			Polynomial q = p.add(0,0,0,Math.cos(t*2*Math.PI)*3);
			
			Complex newGuess = guess.subtract(q.getValue(guess).divide(q.derivative().getValue(guess)));
			err = newGuess.difference(guess);
			
			guess = newGuess;
			tries++;
		}
		
		
		Colour r = Colour.fromComplex(guess);
		
		double ds = -Math.log(tries/5.0)/3;
		
		if(ds<=-1)System.out.println("ds="+ds);
		
		return r.convert(ColourSpace.HSV).add(0,ds,ds);
		
	}
	
}
