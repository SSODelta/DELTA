package com.delta2.testing;

import java.io.File;
import java.io.IOException;

import com.delta2.colours.colourspace.ColourSpace;
import com.delta2.colours.common.Animation;
import com.delta2.colours.util.DImageIO;

public class Main {

	public static void main(String[] args) {
		try {
			
			Animation a = new Animation(DImageIO.read("bird.jpg"));
			a.ensureLength(60);
			
			a.applyFilter((imgs,t) -> imgs[t].applyFilter((x,y,raster) -> raster[x][y].convert(ColourSpace.HSV).add(t/8.0+(x+y)/110.0,0,0)));
			
			a.export(new File("birds.gif"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
