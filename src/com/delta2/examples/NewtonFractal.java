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
	
	private List<Complex> roots;
	
	private int r;
	
	private List<Colour> colList;
	
	public NewtonFractal(Polynomial p, int radius){
		super();
		this.p = p;
		this.r = radius;
		this.deriv = p.derivative();
		roots = new ArrayList<Complex>();
		
		colList = ColourUtil.getMaximumContrastList();
	}

	@Override
	protected Colour generate(double x, double y, double t) {
		
		int root = -1;
		
		Complex guess = new Complex(-r+x*2*r, -r+y*2*r);
		double err = 1;
		
		int tries = 1;
		
		while(err > ColourUtil.ALMOST_ZERO){
			
			Complex newGuess = guess.subtract(p.getValue(guess).divide(deriv.getValue(guess)));
			err = newGuess.difference(guess);
			
			guess = newGuess;
			tries++;
		}
		
		boolean newRoot = true;
		
		for(int i=0; i<roots.size(); i++){
			if(guess.equals(roots.get(i))){
				root = i;
				newRoot = false;
				break;
			}
		}
		
		if(newRoot){
			root = roots.size();
			roots.add(guess);
		}
		
		Colour r = new Colour(colList.get(root));
		
		double ds = -Math.log(tries/5.0)/3;
		return r.convert(ColourSpace.HSV).add(0,ds,ds);
		
	}
	
}
