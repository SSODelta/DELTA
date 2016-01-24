package com.delta2.testing;

import java.io.IOException;

import com.delta2.colours.generation.Generator;
import com.delta2.examples.GradientRegex;

public class Main {

	public static void main(String[] args) {
		
		//for(int i=0; i<30; i++)
		
			try {
			
				Generator g = new GradientRegex("2*",10);
				
				g.exportAnimation("testing.gif", "2*", 20, 20, true);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
