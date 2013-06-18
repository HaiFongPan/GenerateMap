package com.phf.test;

import java.text.DecimalFormat;

import com.phf.Util.GpsConvert;
import com.phf.Util.GpsUtil;

/**
 * Created with IntelliJ IDEA. User: leo Date: 13-4-22 Time: 下午4:08 Project:
 * GenerateRN
 */
public class test {

	public static void main(String args[]) {
		DecimalFormat df = new DecimalFormat("0.000000");
		GpsConvert convert = new GpsConvert(6, 1);
		double[][] lnglat = { { 30.2814, 120.10075 }, { 30.2837, 120.11471 },
				{ 30.285595, 120.13544 }, { 30.288101, 120.1369 },
		/*
		 * { 30.284624, 120.11902 }, { 30.280708, 120.150406 }, { 30.278013,
		 * 120.09563 }, { 30.2788, 120.083717 }, { 30.279483, 120.115567 }, {
		 * 30.275817, 120.1521 }
		 */};
		double[][] b54 = { { 2.12209911209E7, 3354928.9595 },
				{ 2.12223416071E7, 3355149.8556 },
				{ 2.12243426348E7, 3355309.5225 },
				{ 2.12244902004E7, 3355583.9614 } };
		double[][] dfzb = { { 790076.235681, 147134.977998 },
				{ 790753.664233, 147228.698159 },
				{ 791755.350732, 147283.812319 },
				{ 791832.456455, 147419.104531 } };
		System.out.println("=====大地经纬度-------------->北京54坐标=====");
		for (double[] l : lnglat) {
			double r[] = GpsUtil.GaussToBLToGauss(l[1], l[0]);
			System.out.println(df.format(l[1]) + "," + df.format(l[0]) + "---->" + r[0] + "," + r[1]);
		}
		System.out.println("=====北京54坐标-------------->大地经纬度=====");
		for (double[] l : b54) {
			double r[] = GpsUtil.GaussToBL(l[0], l[1]);
			System.out.println(l[0] + "," + l[1] + "---->" + df.format(r[0])
					+ "," + df.format(r[1]));
		}
		System.out.println("=====北京54坐标-------------->地方坐标=====");
		for (double[] l : b54) {
			double t[][] = { { l[0], l[1] } };
			double r[][] = convert._convert(t,false);
			System.out.println(df.format(l[0]) + "," + df.format(l[1]) + "---->" + df.format(r[0][0])
					+ "," + df.format(r[0][1]));
		}
		System.out.println("=====地方坐标-------------->北京54坐标=====");
		for (double[] l : dfzb) {
			double t[][] = { { l[0], l[1] } };
			double r[][] = convert._convert(t,true);
			System.out.println(df.format(l[0]) + "," + df.format(l[1]) + "---->" + df.format(r[0][0])
					+ "," + df.format(r[0][1]));
		}

	}
}
