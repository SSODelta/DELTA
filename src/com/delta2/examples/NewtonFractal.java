package com.delta2.examples;

import java.io.IOException;

import com.delta2.colours.Animation;
import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;
import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.filters.image.DImageFilter;
import com.delta2.colours.filters.image.HueShiftFilter;
import com.delta2.colours.generation.Generator;
import com.delta2.math.Complex;
import com.delta2.math.Polynomial;

public final class NewtonFractal extends Generator {

	private Polynomial p;
	
	private int r;
	
	private double[] magCoeffs, speedCoeffs, phaseCoeffs;
	
	
	public NewtonFractal(Polynomial p, int radius, double[] magCoeffs, double[] speedCoeffs, double[] phaseCoeffs){
		super();
		this.p = p;
		this.r = radius;
		this.magCoeffs   = magCoeffs;
		this.phaseCoeffs = phaseCoeffs;
		this.speedCoeffs = speedCoeffs;
	}
	
	public NewtonFractal(Polynomial p, int radius, int freedom){
		this(p,radius,randomMagCoeffs(freedom),randomPhaseCoeffs(freedom),randomSpeedCoeffs(freedom));
	}
	
	private static double[] randomMagCoeffs(int len){
		double[] coeffs = new double[len];
		
		for(int i=0; i<len; i++)
			coeffs[i] = Math.random()*Math.random()*15;
		
		return coeffs;
	}
	
	private static double[] randomPhaseCoeffs(int len){
		double[] coeffs = new double[len];
		
		for(int i=0; i<len; i++)
			coeffs[i] = Math.random()*Math.random()*Math.PI*2;
		
		return coeffs;
	}
	
	private static double[] randomSpeedCoeffs(int len){
		double[] coeffs = new double[len];
		
		for(int i=0; i<len; i++)
			coeffs[i] = Math.floor(Math.random()*Math.random()*6);
		
		return coeffs;
	}

	@Override
	protected Colour generate(double x, double y, double t) {
		
		Complex guess = new Complex(-r+x*2*r, -r+y*2*r);
		double err = 1;
		
		int tries = 1;
		
		double[] vals = new double[magCoeffs.length];
		
		for(int i=0; i<vals.length; i++)
			vals[i] = magCoeffs[i] * Math.cos(t*2*Math.PI*speedCoeffs[i] + phaseCoeffs[i]);
		
		Polynomial q = p.add(vals);
		Polynomial qd = q.derivative();
		
		while(err > ColourUtil.ALMOST_ZERO){
			
			Complex newGuess = guess.subtract(q.getValue(guess).divide(qd.getValue(guess)));
			err = newGuess.difference(guess);
			
			guess = newGuess;
			
			if(++tries >= 250)
				break;
		}
		
		
		Colour r = Colour.fromComplex(guess);

		
		double ds = -Math.log(tries/5.0)/3;
		
		Colour col = r.convert(ColourSpace.HSV).add(0,0,ds).convert(ColourSpace.RGB);
		
		return col;
		
	}
	
	private static final int WIDTH  = 1920,
			 				 HEIGHT = 1080,
			 				 FPS    = 30;
	
	public static final void generateAndExport(boolean print) throws IOException{
		int freedom = ColourUtil.rand(3, 8),
			r       = ColourUtil.rand(1, 5),
			deg     = Math.max(ColourUtil.rand(4, 12), freedom),
			images  = 150+(int)Math.floor(Math.random()*Math.random()*ColourUtil.rand(0, 40));
		
		System.out.println("==================================");
		System.out.println("=-- NEW ANIMATION ("+ColourUtil.pad(images, 3)+" images) --=");
		System.out.println("==================================");
		
		NewtonFractal nf = new NewtonFractal(Polynomial.random(deg), r, freedom);
		
		String filename = "nf"+ColourUtil.randomLabel()+".gif";
		
		String s = "Polynomial of degree "+deg+", radius "+r+", and freedom "+freedom+".";
		
		nf.exportAnimation(filename, s, WIDTH, HEIGHT, images, FPS, print);
		
		System.out.println("Exported as "+filename+".\n");	
	}
	
}
