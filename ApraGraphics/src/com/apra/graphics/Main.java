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
		for(int i=0;i<100;i++)
			makeOne();
	}


	private static void makeOne() throws IOException {
		int width=800;
		int height=600;

		Generator gen = createGen();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster pixels = (WritableRaster) image.getData();
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				int[] pixelValue = gen.getPixelValue(i, j);
				updateMeans(pixelValue);
        pixels.setPixel(i, j, pixelValue);
			}
		}
    //computeVariance(pixels.getPixels(0, 0, width, height, null));
		//check based on means if mean of means is less than 20 then ignore
		if (isTooDark())
		{
		  System.out.println("skipping- too dark");
		}
		else 
		{
		  
		image.setData(pixels);
		
		//creatcirc change alpha channel
		String fileName= "out/"+System.currentTimeMillis()+".png";
		
		ImageIO.write(image, "png", new File(fileName));
		System.out.println(fileName+" done");
		System.out.println("");
		}
	}
	

  private static boolean isTooDark() {
    // TODO Auto-generated method stub
    return false;
  }


  private static void computeVariance(int[] image) {
    // TODO Auto-generated method stub
    
  }


  private static void updateMeans(int[] pixelValue) {
    // takes array of 4, whenever alpha is 255, update means - update the  accumulate and counter t
	  if ()
    
  }


  private static Generator createGen() {
		return new RandomFunctionGenerator();
		//return new JuliaFunc();
	  //return new SimpleGenerator();
	}
}

//histogram is too narrow???? take every R/G/B value and compute variance 
