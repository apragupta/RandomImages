package com.apra.graphics;

import com.apra.complex.Complex;

/**
 * https://en.wikipedia.org/wiki/Julia_set#Quadratic_polynomials
 * @author Apra G.
 *
 */
public class JuliaFunc implements Generator,func{
	Complex c;
	int max = 255;
	JuliaFunc(){
		c= new Complex(Math.random(), Math.random());
	}
	
	 public int jul(Complex z0) {
	        Complex z = z0;
	        for (int t = 0; t < 1023; t++) {
	            if (z.abs_sq() >= 4) 
	            	return t;
	            z = z.times(z).plus(c);
	        }
	        return 1023;
	    }
	public static int[] heatMap(int i)
	{
		int[] rgb = new int[3];
		rgb[0]=(i>>7)<<5; //top 3
		rgb[1]=((i&0x038)>>3)<<5; //middle 4
		rgb[2]=(i&0x07)<<5; //bottom 3
		return rgb;
	}
	@Override
	public int[] getPixelValue(int x, int y) {
		 double x0 = x*3.5/640.0 -2.5;
         double y0 = y/240.0 -1.0;
         int v=jul(new Complex(x0, y0));
         return heatMap(1023-v);
	}

	@Override
	public double eval(double x, double y) {
	   double x0 = x*3.5/640.0 -2.5;
       double y0 = y/240.0 -1.0;
       int v=jul(new Complex(x0, y0));
	   return v/4;
	}

	@Override
	public int getArgsCount() {
		return 0;
	}

	@Override
	public String prettyPrint() {
		return "J";
	}
}
