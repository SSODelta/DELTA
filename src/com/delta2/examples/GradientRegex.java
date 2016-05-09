package com.delta2.examples;

import java.awt.Point;
import java.util.Set;

import com.delta2.colours.Colour;
import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.generation.Generator;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

/**
 * Assign a unique String to each point in the canvas and supply a regular expression to the generator.
 * The points will then be colored based on the Hamming distance from the String to any string the language defined by the regular expression.
 * @author ssodelta
 *
 */
public final class GradientRegex extends Generator {

	private int len, SIZE;

	private Set<String> matches;
	
	/**
	 * Constructs a Gradient Regex generator.
	 * @param regex The regular expression to use.
	 * @param len The depth of the images. A depth of n will create an image with resolution [2^n, 2^n]
	 */
	public GradientRegex(String regex, int len){
		super((int)Math.pow(2, len),(int)Math.pow(2, len));
		this.len = len;
		SIZE = (int)Math.pow(2, len);
		
		constructMatches(regex);
	}
	
	private void constructMatches(String regex){

		//Construct automaton from RegExp
		Automaton aut = getAutomaton(regex);
		
		//Construct DFA from NFA
		aut.determinize();
		
		//Get the automaton that accepts all strings of length n
		Automaton length_n = getAutomaton("0|1|2|3").repeat(len);
		
		//Get the complement automaton
		Automaton complement = aut.complement().intersection(length_n);
		complement.determinize();
		
		//Get the automaton that accepts the language, and has a length of n
		Automaton lang_n = aut.intersection(length_n);
	
		System.out.println("Computing language...");
		
		matches = lang_n.getStrings(len);
		
		if(matches.size()==0){
			System.out.println("Error: Empty language detected. Aborting...");
			return;
		}
	}
	
	/**
	 * Computes the hamming distance between some string k, and the set of matches.
	 * @param k The string
	 * @return
	 */
	private int getDistance(String k){
		int dist = Integer.MAX_VALUE;
		for(String s : matches){
			int d = hammingDistance(s,k);
			if(d==1)return 1;
			if(d<dist)dist=d;
		}
		return dist;
	}
	

	@Override
	protected Colour generate(double x, double y, double t) {

		String regexPos = getRegexPos(x,y);
		
		int d = getDistance(regexPos);
		
		Colour c = Colour.fromColourSpace(ColourSpace.HSV,  new double[]{t*d/20.0,1.0/Math.sqrt(Math.sqrt(Math.sqrt(d))),1.0/Math.sqrt(Math.sqrt(Math.sqrt(d)))});
		
		return c;
	}

	private String getRegexPos(double x, double y){
		return getRegexPos((int)(x*SIZE),(int)(y*SIZE));
	}
	private String getRegexPos(int x, int y){
		String s = getRegexPos(new Point(x,y));
		return s;
	}
	private String getRegexPos(Point p){
		return regexFromPoint(p, SIZE, SIZE, len);
	}
	private static final String regexFromPoint(Point p, int width, int height, int len){
		
		if(len==0)return "";
		
		StringBuilder sb = new StringBuilder();
		
		int hw = width  / 2,
			hh = height / 2;
		
		if(p.x <= hw){ //Left side
			
			if(p.y <= hh){ //Upper left quadrant
				
				sb.append(0);
				sb.append(regexFromPoint(
								p,
								hw,
								hh,
								len-1
						));
				
			} else { //Lower left quadrant
				
				sb.append(2);
				sb.append(regexFromPoint(
								new Point(p.x,
										  p.y-hh),
								hw,
								hh,
								len-1
						));
				
			}
			
		} else { //Right side
			
			if(p.y <= hh){ //Upper right side

				sb.append(1);
				sb.append(regexFromPoint(
								new Point(p.x-hw,
										  p.y),
								hw,
								hh,
								len-1
						));
				
			} else {

				sb.append(3);
				sb.append(regexFromPoint(
								new Point(p.x-hw,
										  p.y-hh),
								hw,
								hh,
								len-1
						));
			}
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Converts a regex-string to an Automaton-object.
	 * @param regex The string representation of the regular expression.
	 * @return An Automaton-object representing the regular expression.
	 */
	private static final Automaton getAutomaton(String regex){
		RegExp r = new RegExp(regex);
		return r.toAutomaton(true);
	}
	
	/**
	 * Computes the hamming distance between two strings of equal length.
	 * @param s0 
	 * @param s1
	 * @return
	 */
	private static final int hammingDistance (String s0, String s1) {                          
	    int dist = 0;
	    
	    for(int i=0; i<s0.length(); i++){
	    	if(s0.charAt(i) != s1.charAt(i))dist++;
	    }
	    
	    return dist;
	}
}
