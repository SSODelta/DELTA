package com.delta2.testing;

import java.io.IOException;

import com.delta2.examples.NewtonFractal;

public class Main {

	public static void main(String[] args) {
		
		for(int i=0; i<30; i++)
		
			try {
			
				NewtonFractal.generateAndExport(true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
