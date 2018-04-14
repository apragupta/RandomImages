package com.apra.graphics;

public class RandomFunctionGenerator implements Generator {

	//generates an evaluation graph for a random function
	public RandomFunctionGenerator()
	{
		chans[3]=255; // no alpha
		
	}
	@Override
	public int[] getPixelValue(int x, int y) {
		// TODO Auto-generated method stub
		return eval(x,y);
	}
	private int[] eval(int x, int y) {
		chans[0]=eval(x,y,0);//R
		chans[0]=eval(x,y,1);//G
		chans[0]=eval(x,y,2);//B
		
		return chans;
	}
	private int eval(int x, int y, int chanel) {
		// TODO Auto-generated method stub
		return 0;
	}
	int[] chans= new int[4];

}
