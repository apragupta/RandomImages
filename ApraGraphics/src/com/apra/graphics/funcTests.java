package com.apra.graphics;

import static org.junit.Assert.*;

import java.text.NumberFormat;

import org.junit.Test;

public class funcTests {

	@Test
	public void test() {
		funcRegistry reg  = new funcRegistry();
		func fu=reg.build();
		NumberFormat formatter = func.getFormatter();
		System.out.println(fu.prettyPrint());
		for(int i=0; i<4;i++) {
			for(int j=0; j<4;j++) {
				double d=fu.eval(i, j);
				System.out.print(formatter.format(d)+" " );
			}
			System.out.println("" );
		}
	}
	
	@Test
	public void testJul()
	{
		int[] rgb;
		rgb=JuliaFunc.heatMap(1023);
		rgb=JuliaFunc.heatMap(511);
		rgb=JuliaFunc.heatMap(255);
		rgb=JuliaFunc.heatMap(127);
		rgb=JuliaFunc.heatMap(63);
		rgb=JuliaFunc.heatMap(31);
		rgb=JuliaFunc.heatMap(15);
		rgb=JuliaFunc.heatMap(7);
		rgb=JuliaFunc.heatMap(3);
		rgb=JuliaFunc.heatMap(1);
		
	}

}
