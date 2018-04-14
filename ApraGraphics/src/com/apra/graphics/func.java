package com.apra.graphics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface func {
	double eval(double x, double y);

	static double mapNaN(double v) {
		if (Double.isNaN(v))
			return 0.0;
		return v;
	}

	int getArgsCount();

	String prettyPrint();

	static NumberFormat getFormatter() {
		return new DecimalFormat("#0.00");
	}
}

abstract class oneArgFunc implements func {
	@Override
	public double eval(double x, double y) {
		return myEval(x);
	}

	@Override
	public int getArgsCount() {
		return 1;
	}

	abstract protected double myEval(double eval);
}

class ident extends oneArgFunc {
	@Override
	protected double myEval(double eval) {
		return func.mapNaN(eval);
	}

	@Override
	public String prettyPrint() {
		return "_";
	}
}

class randScalarMult extends oneArgFunc {

	double k;

	randScalarMult() {
		k = 100 * Math.random();
	}

	@Override
	protected double myEval(double a) {
		a = func.mapNaN(a);
		return k * a;
	}

	@Override
	public String prettyPrint() {
		return func.getFormatter().format(k) + "*_";
	}
}

class trigFu extends oneArgFunc {
	int type; //

	trigFu(int type) {
		this.type = type;
	}

	@Override
	protected double myEval(double a) {
		a = func.mapNaN(a);
		double rads = (a * Math.PI) / 180;
		switch (type) {
		case 0:
			return Math.sin(rads);
		case 1:
			return Math.cos(rads);
		default:
			return Math.tan(rads);
		}
	}

	@Override
	public String prettyPrint() {
		return type == 0 ? "sin" : (type == 1 ? "cos" : "tan");
	}
}

class twoArgFunc implements func {
	int type;

	twoArgFunc(int type) {
		this.type = type;
	}

	@Override
	public int getArgsCount() {
		return 2;
	}

	@Override
	public double eval(double x, double y) {
		x = func.mapNaN(x);
		y = func.mapNaN(y);
		switch (type) {
		case 0:
			return x + y;
		case 1:
			return x - y;
		case 2:
			return x * y;
		default:
			return x / y;
		}
	}

	@Override
	public String prettyPrint() {
		return type == 0 ? "add" : (type == 1 ? "sub" : (type == 2 ? "mult" : "div"));
	}
}

class compositeFunc implements func {

	func xF;
	func yF;
	func myF;

	@Override
	public double eval(double x, double y) {
		return Math.abs(func.mapNaN(myF.eval(xF.eval(x, 0), yF.eval(y, 0))));
	}

	@Override
	public int getArgsCount() {
		return 2;
	}

	@Override
	public String prettyPrint() {
		return myF.prettyPrint() + "(" + xF.prettyPrint() + "," + yF.prettyPrint() + ")";
	}

}

class funcRegistry {
	List<func> funcs = new ArrayList<func>();
	Random r = new Random();

	funcRegistry() {
		for (int i = 0; i < 3; i++)
			funcs.add(new trigFu(i));
		for (int i = 0; i < 4; i++)
			funcs.add(new twoArgFunc(i));
	}

	func pickRandom() {
		return funcs.get(r.nextInt(funcs.size()));
	}

	func pickRandom2args() {
		while (true) {
			func f = pickRandom();
			if (f.getArgsCount() == 2)
				return f;
		}
	}

	public func build() {
		return buildLayer(2);
	}

	private func buildLayer(int depth) {
		compositeFunc top = new compositeFunc();
		top.myF = pickRandom();
		depth--;
		if (depth == 0) {
			top.xF = new randScalarMult();
			top.yF = new randScalarMult();
		} else {
			top.xF = buildLayer(depth);
			top.yF = buildLayer(depth);
		}
		return top;
	}
}
