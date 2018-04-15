package com.apra.graphics;

import com.apra.complex.Complex;

public class MandelbrotFunc implements func {
	double xc,yc;
	int max = 255;
	double size=2;
	MandelbrotFunc(){
		size=1+Math.random();
        xc   = -0.5+Math.random();
        yc   = -0.5+Math.random();
	}
	
	 public static int mand(Complex z0, int max) {
	        Complex z = z0;
	        for (int t = 0; t < max; t++) {
	            if (z.abs() > 2) return t;
	            z = z.times(z).plus(z0);
	        }
	        return max;
	    }
	@Override
	public double eval(double x, double y) {
		 double x0 = xc - size/2 + size*x/640;
         double y0 = yc - size/2 + size*y/480;
         return max - mand(new Complex(x0, y0), max);

	}
	@Override
	public int getArgsCount() {
		return 2;
	}
	@Override
	public String prettyPrint() {
		return "MB";
	}

}
