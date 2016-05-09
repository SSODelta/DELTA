package com.delta2.testing;

import java.io.IOException;

import com.delta2.colours.generation.Generator;
import com.delta2.examples.GradientRegex;
import com.delta2.examples.NewtonFractal;
import com.delta2.math.func.Polynomial;

public class Main {

	public static void main(String[] args) {
		
		//for(int i=0; i<30; i++)
		
			try {
				Generator g = new GradientRegex("3*", 9);
				
				g.exportAnimation("wutup.gif", "3*", 2400, 60, true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
