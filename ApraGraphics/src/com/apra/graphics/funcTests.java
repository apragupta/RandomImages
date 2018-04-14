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
	public void testNan()
	{
		//div(sin(5.11*,3.95*),mult(4.18*,7.81*))
		
	}

}
