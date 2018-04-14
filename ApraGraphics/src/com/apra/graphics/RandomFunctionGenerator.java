package com.apra.graphics;

public class RandomFunctionGenerator implements Generator {
	func[] funcs=new func[3];
	//generates an evaluation graph for a random function
	public RandomFunctionGenerator()
	{
		chans[3]=255; // no alpha
		funcRegistry reg  = new funcRegistry();
		for(int i=0;i<3;i++)
		{
			funcs[i]=reg.build();
			System.out.println(funcs[i].prettyPrint());
		}
	}
	@Override
	public int[] getPixelValue(int x, int y) {
		// TODO Auto-generated method stub
		return eval(x,y);
	}
	private int[] eval(int x, int y) {
		chans[0]=eval(x,y,0);//R
		chans[1]=eval(x,y,1);//G
		chans[2]=eval(x,y,2);//B
		
		return chans;
	}
	private int eval(int x, int y, int c) {
		double d=func.mapNaN(funcs[c].eval(x, y));
		return (int)d;
	}
	int[] chans= new int[4];

}
