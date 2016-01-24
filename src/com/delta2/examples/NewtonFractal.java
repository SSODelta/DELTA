package com.delta2.examples;

import java.io.IOException;

import com.delta2.colours.Colour;
import com.delta2.colours.ColourUtil;
import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.generation.Generator;
import com.delta2.math.Complex;
import com.delta2.math.Polynomial;

/**
 * Newton Fractals are based on Newton's iteration method for approximating roots of differentiable functions.
 * Suppose we have some guess for a root g_n; then we can do an ever better approximation
 * 
 * g_{n+1} = g_n - f(g_n) / f'(g_n)
 * 
 * To create a Newton Fractal, for every pixel supply an inital guess g_0 = x + y*i and then keep iterating until the error
 * dips down beneath some acceptable threshold (defined by ColourUtil.ALMOST_ZERO). Sometimes Newton Fractals don't converge,
 * so a maximum iteration cap of 250 has been established.
 * 
 * The colour of a given complex number is an HSV object with sat = value = 1, and hue = atan2(y, x) of the complex number.
 * 
 * To keep things simple, this Generator does not use general differentiable functions, 
 * but instead just polynomials (com.delta2.math.Polynomial), so:
 * 
 * P(x) = a_0 + a_1*x + a_2*x^2 + ... + a_n*x^n
 * 
 * To supply animation, the coefficients of the polynomial are given a harmonic oscillation, so we instead have:
 * 
 * P(x,t) = a_0 * cos(b_0*t + c_0)
 *        + a_1 * cos(b_1*t + c_1) * x
 *        + a_2 * cos(b_2*t + c_2) * x^2
 *        + ...
 *        + a_n * cos(b_n*t + c_n) * x^n
 *        
 * This means that it's easy to create animations which loop, just make sure all b_n are multipla of 2pi.
 * 
 * @author ssodelta
 *
 */
public final class NewtonFractal extends Generator {

	private Polynomial p;
	
	private int r;
	
	private double[] magCoeffs, speedCoeffs, phaseCoeffs;
	
	/**
	 * Constructs a new Newton Fractal
	 * @param p The Polynomial to use
	 * @param radius Will generate points in the interval [-radius; radius] for both x and y. Low radius = zoomed in, high radius = zoomed out.
	 * @param magCoeffs Coefficients for the magnitudes of the harmonic oscillations on the coefficients (corresponding to a_n in the class specification)
	 * @param speedCoeffs Coefficients for the angular velocity of the harmonic oscillations on the coefficients (corresponding to b_n in the class specification)
	 * @param phaseCoeffs Coefficients for the phase shift of the harmonic oscillations on the coefficients (corresponding to c_n in the class specification)
	 */
	public NewtonFractal(Polynomial p, int radius, double[] magCoeffs, double[] speedCoeffs, double[] phaseCoeffs){
		super(WIDTH, HEIGHT);
		this.p = p;
		this.r = radius;
		this.magCoeffs   = magCoeffs;
		this.phaseCoeffs = phaseCoeffs;
		this.speedCoeffs = speedCoeffs;
	}
	
	/**
	 * Constructs a new Newton Fractal
	 * @param p The Polynomial to use
	 * @param radius Will generate points in the interval [-radius; radius] for both x and y. Low radius = zoomed in, high radius = zoomed out.
	 * @param freedom Freedom specifies how many of the coefficients should be given a random oscillation. High freedom = chaotic animations.
	 */
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
	
	/**
	 * Generate and export an animation by writing directly into a .gif.
	 * Much more resistant to memory leaks / crashes.
	 * @param print Print progress to system.out?
	 * @throws IOException
	 */
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
		
		nf.exportAnimation(filename, s, images, FPS, print);
		
		System.out.println("Exported as "+filename+".\n");	
	}
	
}
