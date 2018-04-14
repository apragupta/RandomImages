package com.apra.graphics;

public class SimpleGenerator implements Generator {

	@Override
	public int[] getPixelValue(int x, int y) {
		return new int[] {x,y,x+y,255};
	}

}
