package com.delta2.math;

public class Complex {
	
	double re, im;
	
	
	public static int PRECISION = 5;
	public static Complex ZERO = new Complex(0,0);
	
	/**
	 * Instantiates a new Complex number with a real and imaginary part.
	 * @param re The real part.
	 * @param im The imaginary part.
	 */
	public Complex(double re, double im){
		this.re = re;
		this.im = im;
	}
	
	public Complex(double re){
		this(re,0);
	}
	
	/**
	 * Returns the modulus of this Complex-object.
	 * @return The modulus of this Complex-object.
	 */
	public double mod(){
		return mod(this);
	}
	
	/**
	 * Returns the argument of this Complex-object.
	 * @return the argument of this Complex-object.
	 */
	public double arg(){
		return arg(this);
	}
	
	/**
	 * Returns the sum of this Complex-object and a real number.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) + c = (a+c) + bi
	 * 
	 * @param n The real number.
	 * @return The sum of this Complex-object and another.
	 */
	public Complex add(double n){
		return new Complex( n + re,
							im);
	}
	
	/**
	 * Returns the difference of this Complex-object and a real number.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) - c = (a-c) + bi
	 * 
	 * @param n The real number.
	 * @return The difference of this Complex-object and a real number.
	 */
	public Complex subtract(double n){
		return new Complex( re - n,
							im);
	}
	
	/**
	 * Returns the product of this Complex-object and a real number.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) * c = ac + cbi
	 * 
	 * @param n The real number.
	 * @return The product of this Complex-object and a real number.
	 */
	public Complex multiply(double n){
		return new Complex( n * re,
							n * im);
	}
	
	/**
	 * Returns the dividend of this Complex-object and a real number.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) / c = (a/c) + (b/c)i
	 * 
	 * @param n The real number.
	 * @return The dividend of this Complex-object and a real number.
	 */
	public Complex divide(double n){
		return new Complex( re / n,
							im / n);
	}

	/**
	 * Returns the exponentiation of this Complex-object and a real exponent.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi)^n = |(a + bi)|^n * (cos(n*theta) + sin(n*theta)*i)
	 * 
	 * @param n
	 * @return
	 */
	public Complex pow(double n){
		return pow(this, n);
	}
	
	/**
	 * Returns the sum of this Complex-object and another.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) + (c + di) = (a+c) + (b+d)*i
	 * 
	 * @param a The complex number.
	 * @return The sum of this Complex-object and another.
	 */
	public Complex add(Complex a){
		return add(this, a);
	}
	
	/**
	 * Returns the sum of this Complex-object and another.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) + (c + di) = (a-c) + (b-d)*i
	 * 
	 * @param a The complex number.
	 * @return The sum of this Complex-object and another.
	 */
	public Complex subtract(Complex a){
		return subtract(this, a);
	}
	
	/**
	 * Returns the product of this Complex-object and another.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) * (c + di) = (ac - bd) + (ad + bc)*i
	 * 
	 * @param a The complex number.
	 * @return The product of this Complex-object and another.
	 */
	public Complex multiply(Complex a){
		return multiply(this, a);
	}
	
	/**
	 * Returns the dividend of this Complex-object and another.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) / (c + di) = (ac + bd) / k + (bc - ad) / k
	 * ... where k = (c^2 + d^2)
	 * 
	 * @param a The complex number.
	 * @return The dividend of this Complex-object and another.
	 */
	public Complex divide(Complex a){
		return divide(this, a);
	}
	
	/**
	 * Adds two complex numbers.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) + (c + di) = (a-c) + (b-d)*i
	 * 
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return The sum of the two complex numbers.
	 */
	public static Complex add(Complex a, Complex b){
		return new Complex( a.re + b.re,
							a.im + b.im);
	}
	
	/**
	 * Subtracts two complex numbers.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) - (c + di) = (a-c) + (b-d)*i
	 * 
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return The difference of the two complex numbers.
	 */
	public static Complex subtract(Complex a, Complex b){
		return new Complex( a.re - b.re,
							a.im - b.im);
	}
	
	/**
	 * Multiplies two complex numbers.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) * (c + di) = (ac - bd) + (ad + bc)*i
	 * 
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return The product of the two complex numbers.
	 */
	public static Complex multiply(Complex a, Complex b){
		
		double ac = a.re * b.re;
		double bd = a.im * b.im;
		double abcd = (a.re + a.im) * (b.re + b.im);
		
		return new Complex( ac - bd,
							abcd - ac - bd);
	}
	
	/**
	 * Divides two complex numbers.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi) / (c + di) = (ac + bd) / k + (bc - ad) / k
	 * ... where k = (c^2 + d^2)
	 * 
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return The dividend of the two complex numbers.
	 */
	public static Complex divide(Complex a, Complex b){
		double k = Math.pow(b.im, 2) + Math.pow(b.re, 2);
		return new Complex( (a.re * b.re + a.im * b.im) / k,
							(a.im * b.re - a.re * b.im) / k);
	}
	
	/**
	 * Returns the modulus of a complex number.
	 * 
	 * Uses the formula:
	 * 
	 * mod(a + bi) = sqrt(a^2 + b^2)
	 * 
	 * @param a The complex number.
	 * @return The modulus of a complex number.
	 */
	public static double mod(Complex a){
		return Math.sqrt(
				Math.pow(a.re, 2) +
				Math.pow(a.im, 2));
	}
	
	/**
	 * Returns the argument of a complex number.
	 * 
	 * Uses the formula:
	 * 
	 * arg(a + bi) = atan2(b, a)
	 * 
	 * @param a The complex number.
	 * @return The argument of a complex number.
	 */
	public static double arg(Complex a){
		return Math.atan2(a.im, a.re);
	}
	
	/**
	 * Returns the exponentiation of a complex number and a real number.
	 * 
	 * Uses the formula:
	 * 
	 * (a + bi)^n = |(a + bi)|^n * (cos(n*theta) + sin(n*theta)*i)
	 * 
	 * @param a The complex number.
	 * @param b The real number.
	 * @return The exponentiation of a complex number and a real number.
	 */
	public static Complex pow(Complex a, double b){
		double m = Math.pow(a.mod(), b);
		double r = b * a.arg();
		return new Complex( Math.cos(r) * m,
							Math.sin(r) * m);
	}
	
	/**
	 * The cutoff factor for equality.
	 */
	private static double CUTOFF = 0.0001;
	/**
	 * Checks whether or not two complex numbers are approximately equal.
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return Returns 'true' if they are approximately equal.
	 */
	public static boolean equals(Complex a, Complex b){
		return (difference(a,b) < CUTOFF);
	}
	
	/**
	 * Rounds off a real number to n digits.
	 * @param a The real number.
	 * @param digits The amount of digits.
	 * @return A rounded real number.
	 */
	public static double roundOff(double a, int digits){
		double m = Math.pow(10, digits);
		return Math.round(a*m) / m;
	}
	
	/**
	 * Rounds off this Complex-object to 5 digits.
	 * @return The rounded off version of this Complex-object.
	 */
	public Complex update(){
		return update(this);
	}
	
	/**
	 * Rounds off a complex number to 5 digits.
	 * @param a The complex number to round off.
	 * @return The rounded-off version of a complex number.
	 */
	public static Complex update(Complex a){
		Complex roundOffComplex = new Complex(
								  roundOff(a.re, PRECISION),
								  roundOff(a.im, PRECISION));
		return roundOffComplex;
	}
	
	/**
	 * Checks whether or not this complex number is approximately equal to another.
	 * @param a The complex number.
	 * @return Returns 'true' if the two complex numbers are approximately equal.
	 */
	public boolean equals(Complex a){
		return equals(this.update(), a.update());
	}
	
	/**
	 * Returns the Euclidean distance between this Complex-object and another.
	 * @param a The complex number.
	 * @return The Euclidean distance between this Complex-object and another.
	 */
	public double difference(Complex a){
		return difference(this, a);
	}
	
	/**
	 * Returns the Euclidean distance between two complex numbers.
	 * @param a The first complex number.
	 * @param b The second complex number.
	 * @return The Euclidean distance between two complex numbers.
	 */
	public static double difference(Complex a, Complex b){
		return Math.sqrt(Math.pow(a.re - b.re, 2) + Math.pow(a.im - b.im, 2));
	}
	
	@Override
	public String toString(){
		return re + " + i*"+im;
	}
}
