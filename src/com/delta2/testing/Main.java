package com.delta2.testing;

import java.io.IOException;

import com.delta2.colours.Colour;
import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.generation.Generator;
import com.delta2.examples.GradientRegex;

public class Main {

	public static void main(String[] args) {
		
		 Colour c = Colour.RED;
		    c.convert(ColourSpace.CMYK);
		    System.out.println(c);
	}
}
