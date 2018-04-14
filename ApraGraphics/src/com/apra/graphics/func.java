package com.apra.graphics;

public interface func {
	double eval(int x, int y);
}

abstract class oneArgFunc implements func{
	func childFu;
	@Override
	public double eval(int x, int y) {
		return myEval(childFu.eval(x, y));
	}
	abstract protected double myEval(double eval);
}

class trigFu extends oneArgFunc{
	int type; //
	trigFu(int type){
		this.type=type;
	}
	@Override
	protected double myEval(double a) {
		double rads=(a*Math.PI)/180;
		switch(type)
		{
			case 0: 
				return Math.sin(rads);
			case 1: 
				return Math.cos(rads);
			default: 
				return Math.tan(rads);
		}
	}
//	private int map(double v) {
//		//v can go from -1 to +1
//		return (int)((1+v)*127);
//	}
//	private int mapNaN(double v) {
//		if(Double.isNaN(v))
//			return 0;
//		return (int)(127*((v+Double.POSITIVE_INFINITY)/Double.POSITIVE_INFINITY));
//	}
}

//class twoArgFunc implements func{
//	int type;
//	twoArgFunc(int type){this.type=type;}
//	@Override
//	public int eval(int x, int y) {
//			
//	}
//
//}