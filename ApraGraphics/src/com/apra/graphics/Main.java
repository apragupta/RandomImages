package com.apra.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Main {
  static double[] accumulator = {0,0,0};
  static int counter;
  

	public static void main(String[] args) throws IOException
	{
		try {
			Files.createDirectory(Paths.get("out"));
		} catch (FileAlreadyExistsException e) {
			// no harm in ignoring this one
		}
		for(int i=0;i<500;i++)
			makeOne();
	}


	private static void makeOne() throws IOException {
		int width=800;
		int height=600;

		Generator gen = createGen();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster pixels = (WritableRaster) image.getData();
        VarianceCalc var_calc= new VarianceCalc();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				int[] pixelValue = gen.getPixelValue(i, j);
				var_calc.visit(pixelValue);
				pixels.setPixel(i, j, pixelValue);
			}
		}
		var_calc.done();
		if(!var_calc.isTooDark() && !var_calc.isTooFlat()) 
		{
		
			image.setData(pixels);
			String fileName= "out/"+System.currentTimeMillis()+".png";
			ImageIO.write(image, "png", new File(fileName));
			System.out.println(fileName+" done");
			System.out.println("");
		}
  }


  private static Generator createGen() {
		return new RandomFunctionGenerator();
		//return new JuliaFunc();
	  //return new SimpleGenerator();
	}
}

//histogram is too narrow???? take every R/G/B value and compute variance 
