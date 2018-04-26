package com.apra.graphics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class VarianceCalc {

	double[] means = new double[3];
	double[] variances= new double[3];
	ArrayList<int[]> pixels = new ArrayList<>(); //for computing variance at the end 
	static int extractByte(int inp) { return inp&0xFF;}
	public void visit(int[] pixelValue) {
		int a=(pixelValue[3]&0xFF);
		if(a>0)
		{
			for(int c=0;c<3;c++)
			{
				means[c]+=(pixelValue[c]&0xFF);
			}
			pixels.add(pixelValue);
		}
		
	}
	/**
	 * @param all_pixels is a flat Array with R,G,B,A is repeated in row scan order
	 */
	public void done() {
		int count=pixels.size();
		//compute means
		for(int c=0;c<3;c++) //once per channel
		{
			means[c]/=count;
		}
		
		for(int[] p:pixels)
		{
			for(int c=0;c<3;c++) { //once per channel
				int chan= extractByte(p[c]);
				variances[c]+=(chan-means[c])*(chan-means[c]);
			}
		}
		for(int c=0;c<3;c++) //once per channel
		{
			variances[c]/=count;
			variances[c]=Math.sqrt(variances[c]);
		}
	}
	@Override
	public String toString() {
		NumberFormat nf = new DecimalFormat("#0.0");
		StringBuilder sb = new StringBuilder();
		sb.append("means=[");
		for(double m:means) {
			sb.append(nf.format(m)).append(" ");
		}
		sb.append("] vars=[");
		for(double v:variances) {
			sb.append(nf.format(v)).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	public boolean isTooDark()
	{
		return ( means[0]+means[1]+means[2] < 50);
	}
	public boolean isTooFlat()
	{
		return ( variances[0]+variances[1]+variances[2] < 50);
	}


}
