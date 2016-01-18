package com.delta2.testing;

import java.io.IOException;
import com.delta2.colours.*;
import com.delta2.colours.filters.image.MedianFilter;
import com.delta2.examples.NewtonFractal;
import com.delta2.math.Polynomial;

public class Main {

	public static void main(String[] args) {
		try {
			
			NewtonFractal nf = new NewtonFractal(Polynomial.random(5), 6);
			
			DImageIO.write(nf.generateImage(720, 720).applyFilter(new MedianFilter(9)), "out.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
