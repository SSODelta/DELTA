package com.delta2.testing;

import java.io.IOException;
import com.delta2.colours.*;
import com.delta2.colours.filters.image.MedianFilter;

public class Main {

	public static void main(String[] args) {
		try {
			
			DImage img1 = DImageIO.read("flowers.jpg"),
				   img2 = new DImage(img1);
			
			img2.applyFilter(new MedianFilter(9));
			
			img1.blend(img2, BlendMode.HARD_LIGHT);
			
			DImageIO.write(img1, "flowers_out.jpg");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
