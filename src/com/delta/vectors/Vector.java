package com.delta.vectors;

public abstract class Vector {
	protected double a,b;
	
	private static final double ALMOST_ZERO = 0.00001;
	public static boolean almostZero(double x){
		return Math.abs(x) < ALMOST_ZERO;
	}
	
	public Vector(double a, double b){
		this.a = a;
		this.b = b;
	}
	
	abstract public Radial getRadial();
	abstract public Cartesian getCartesian();
	

	public double getRadius(){
		return getRadial().a;
	}
	public double getAngle(){
		return getRadial().b;
	}

	public double getX(){
		return getCartesian().a;
	}
	public double getY(){
		return getCartesian().b;
	}
	
	
	public Radial addTheta(double dtheta){
		return new Radial(getRadius(), getAngle() + dtheta);
	}
	public Radial addRadius(double dr){
		return new Radial(getRadius() + dr, getAngle());
	}
	
	
	public Cartesian addX(double dx){
		return new Cartesian(getX() + dx, getY());
	}
	public Cartesian addY(double dy){
		return new Cartesian(getX(), getY() + dy);
	}
	

	public Radial scale(double amount){
		return new Radial(getRadius()*amount, getAngle());
	}
	
	public Radial normalize(){
		return new Radial(1, getAngle());
	}
	
	public Cartesian scale(double scaleX, double scaleY){
		return new Cartesian(getX()*scaleX, getY()*scaleY);
	}
	
	public Cartesian translate(double dx, double dy){
		return new Cartesian(getX()+dx, getY()+dy);
	}
	
	
	public Cartesian add(Vector c){
		return new Cartesian(getX()+c.getX(), getY()+c.getY());
	}
	
	public Cartesian sub(Vector c){
		return new Cartesian(getX()-c.getX(), getY()-c.getY());
	}
	
	/**
	 * ref: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
	 * @param o
	 * @return
	 */
	public double cross(Vector o){
		return getX()*o.getY() - getY()*o.getX();
	}
	
	@Override
	public int hashCode(){
		return 43*new Double(getX()).hashCode() + 37*new Double(getY()).hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Vector))return false;
		
		Vector v = (Vector)o;
		
		return almostZero(getX()-v.getX()) && almostZero(getY()-v.getY());
	}
	
}
