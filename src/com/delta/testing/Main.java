package com.delta.testing;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.delta.colors.common.Color;
import com.delta.colors.filters.*;
import com.delta.colors.util.Image;

public class Main {

	public static void main(String[] args) {

			try {
				Image img      = new Image(ImageIO.read(new File("bird.jpg"))),
					  filtered = img.applyFilter(new MedianFilter(Color.comparatorValue(), 11));
				
				ImageIO.write(filtered.getBufferedImage(), "png", new File("dog vision.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
