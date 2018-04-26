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
      return 0;
    return v;
  }

  int getArgsCount();

  String prettyPrint();

  static NumberFormat getFormatter() {
    return new DecimalFormat("#0.00");
  }
}

// no operation
class noOP implements func {

  @Override
  public double eval(double x, double y) {
    return 0;
  }

  @Override
  public int getArgsCount() {
    return 0;
  }

  @Override
  public String prettyPrint() {
    return "";
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

// identity
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


//log10

/*class log10 extends oneArgFunc {
  @Override
  protected double myEval(double eval) {
    return func.mapNaN(Math.log10(eval));
  }

  @Override
  public String prettyPrint() {
    return "log10" ;
  }
}*/




class randScalarMult extends oneArgFunc {

  double k;

  randScalarMult() {
    k = Math.random();
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
  int type;

  trigFu(int type) {
    this.type = type;
  }

  @Override
  protected double myEval(double a) {
    a = func.mapNaN(a);
    double rads = (a * Math.PI) / 200;
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
  double a, b;

  twoArgFunc(int type) {
    this.type = type;
    //128 is half of scale(255)
    a = 100*Math.random();
    b = 100*Math.random();
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
      return a * x + b * y;
    case 1:
      return a * x - b * y;
    case 2:
      return a * x * y + b;
    case 3:
      return a * x / (y + .0001) + b;
    default:
      return Math.sqrt(a * x * x + b * y * y) ;

    }
  }

  @Override
  public String prettyPrint() {
    switch (type) {
    case 0:
      return "add";
    case 1:
      return "sub";
    case 2:
      return "mult";
    case 3:
      return "div";
    default:
      return "circ";

    }
  }
}

class compositeFunc implements func {

  // left function
  func xF;
  // right function
  func yF;
  // me
  func myF;

  @Override
  public double eval(double x, double y) {
    return func.mapNaN(myF.eval(xF.eval(x, y), yF.eval(y, x)));
  }

  @Override
  public int getArgsCount() {
    return 2;
  }

  @Override
  public String prettyPrint() {
    StringBuilder sb = new StringBuilder();
    sb.append(myF.prettyPrint()).append("(").append(xF.prettyPrint());

    if (myF.getArgsCount() == 2) {
      sb.append(",").append(yF.prettyPrint());
    }
    sb.append(")");
    return sb.toString();
  }

}

class funcRegistry {
  List<func> funcs = new ArrayList<func>();
  Random r = new Random();

  funcRegistry() {
    for (int i = 0; i < 3; i++)
      funcs.add(new trigFu(i));
    for (int i = 0; i < 5; i++)
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
      // stops only when you return
    }
  }

  
  
  public func build() {
    Random r = new Random();
    return buildLayer(2 + r.nextInt(2));
  }

  //building tree from top to bottom
  private func buildLayer(int depth) {
    compositeFunc top = new compositeFunc();
    top.myF = pickRandom();
    depth--;
    if (depth == 0) {
      top.xF = new randScalarMult();
      top.yF = new randScalarMult();
    }
    else {
      top.xF = buildLayer(depth);
      top.yF = buildLayer(depth);
    }
    if (top.myF.getArgsCount() == 1) {
      top.yF = new noOP();
    }

    return top;
  }
}
