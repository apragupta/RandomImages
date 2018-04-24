package com.apra.graphics;

import java.util.Random;

public class RandomFunctionGenerator implements Generator {
	boolean doMandleBrot=false;
	func[] funcs;
	int[] chans;
	
	//generates an evaluation graph for a random function
	public RandomFunctionGenerator()
	{//channel
	  funcs =new func[3];
	  chans= new int[4];
	  
		chans[3]=255; // no alpha
		funcRegistry reg  = new funcRegistry();
		for(int i=0;i<3;i++)
		{
			funcs[i]=reg.build();
		   //System.out.println(funcs[i].prettyPrint());
		}
		//randomly replace one chanel with a mandelbrot
		if(doMandleBrot)
		{
			int c=(int)Math.random()*3;
			funcs[c]=new MandelbrotFunc();
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
		chans[3]=isInCircle(x,y);
		return chans;		
	}
	private int isInCircle(int x, int y) {
	  int alphaChan = 0;
    if (((x-400)*(x-400)) + ((y-300)*(y-300)) < 300*300)
    {
     alphaChan =  255;
    }
    return alphaChan;
    

  }
  private int eval(int x, int y, int c) {
		double d=func.mapNaN(funcs[c].eval(x, y));
		return (int)d;
	}
	

}
