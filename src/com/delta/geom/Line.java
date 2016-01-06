package com.delta.geom;

import java.io.Serializable;

import com.delta.vectors.*;


public class Line implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4487506996383116343L;
	private Vector from, to;
	
	public Line(Vector from, Vector to){
		this.setFrom(from);
		this.setTo(to);
	}
	
	public Line(Line k){
		this.setFrom(new Cartesian(k.getFrom()));
		this.setTo(  new Cartesian(k.getTo()));
	}
	
	public double getLambda(Line o){

		if(equals(o))return -1;
		
		Vector  p = from,
				q = o.from,
				r = to.sub(from),
				s = o.to.sub(o.from);
		
		/*If r * s = 0 and (q - p) × r = 0, then the two lines are collinear.
		 * This is not useful because the intersections should be corners in a polygon. Return null in this case, then.
		 * 
		 * On the other hand, if r × s = 0 and (q - p) × r =/= 0, then the two lines are parallel and non-intersecting.
		 * In this case there isn't an intersection either.
		 * 
		 * In any case, if r × s = 0, then return null.
		 */

		if(Vector.almostZero(r.cross(s))){
			return -1;
		}
		
		double t = q.sub(p).cross(s) / r.cross(s);
		double u = p.sub(q).cross(r) / s.cross(r);
		
		//If r × s =/= 0 and 0 <= t <= 1 and 0 <= u <= 1, the two line segments meet at the point p + t r = q + u s.
		if((0 <= t && t <= 1) && (0 <= u && u <= 1)){
			return t;
		}
		
		//Otherwise, the two line segments are not parallel but do not intersect.
		return -1;
	}

	public Vector getFrom() {
		return from;
	}

	public void setFrom(Vector from) {
		this.from = from;
	}

	public Vector getTo() {
		return to;
	}

	public Line scale(double scalex,  double scaley){
		return new Line(from.scale(scalex, scaley),
						to.scale(scalex, scaley));
	}
	
	public Line translate(double dx,  double dy){
		return new Line(from.translate(dx, dy),
						to.translate(dx, dy)  );
	}

	public void setTo(Vector to) {
		this.to = to;
	}
	
	public Line rotate(double theta){
		return new Line(from.addTheta(theta),
						  to.addTheta(theta));
	}
	
	public Line invert(){
		return new Line(to, from);
	}
	
	public double cross(Line l){
		Vector UV = to.sub(from);
		Vector VW = l.to.sub(l.from);
		
		return UV.cross(VW);
	}
	
	public Line scale(double amount){
		return new Line(from.scale(amount),
						  to.scale(amount));
	}
	

	public boolean colinearAndOverlapping(Line o){

		Vector  p = from,
				q = o.from,
				r = to.sub(from),
				s = o.to.sub(o.from);
		
		return (Vector.almostZero(r.cross(s)) && Vector.almostZero(q.sub(p).cross(r)));
	}
	
	public double getLength(){
		return to.sub(from).getRadius();
	}
	
	public double getSlope(){
		return (from.getY() - to.getY()) / (from.getX() - to.getX());
	}
	
	public boolean sharePoint(Line o){
		return from.equals(o.from) || from.equals(o.to)
			  || to.equals(o.from) || to.equals(o.to);
	}
	
	/**
	 * ref: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
	 * 
	 * @param o
	 * @return
	 */
	public Vector getIntersection(Line o){
		
		if(equals(o))return null;
		
		Vector  p = from,
				q = o.from,
				r = to.sub(from),
				s = o.to.sub(o.from);
		
		/*If r * s = 0 and (q - p) × r = 0, then the two lines are collinear.
		 * This is not useful because the intersections should be corners in a polygon. Return null in this case, then.
		 * 
		 * On the other hand, if r × s = 0 and (q - p) × r =/= 0, then the two lines are parallel and non-intersecting.
		 * In this case there isn't an intersection either.
		 * 
		 * In any case, if r × s = 0, then return null.
		 */

		if(Vector.almostZero(r.cross(s))){
			return null;
		}
		
		double t = q.sub(p).cross(s) / r.cross(s);
		double u = p.sub(q).cross(r) / s.cross(r);
		
		//If r × s =/= 0 and 0 <= t <= 1 and 0 <= u <= 1, the two line segments meet at the point p + t r = q + u s.
		if((0 <= t && t <= 1) && (0 <= u && u <= 1)){
			return p.add(r.scale(t));
		}
		
		//Otherwise, the two line segments are not parallel but do not intersect.
		return null;
	}
	
	
	public boolean equals(Line o){
		return from.equals(o.from) && to.equals(o.to);
	}
	
	@Override
	public int hashCode(){
		return from.hashCode()*37 + 17*to.hashCode();
	}
	
	@Override
	public String toString(){
		return "(from="+from.toString()+", to="+to.toString()+")";
	}
}
