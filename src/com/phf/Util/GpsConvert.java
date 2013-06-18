package com.phf.Util;

/**
 * Created with IntelliJ IDEA.
 * User: leo 
 * Date: 13-5-6 
 * Time: 下午8:47 
 * Project:  GenerateRN
 */
public class GpsConvert {
	private boolean _test = true;
	private double old_common[][] =  {
			{1.96960748467E7,3116876.267},
			{1.96952596216E7,3116735.0336},
			{1.96953833066E7,3117674.199},
			{1.96950888934E7,3117618.3676},
			{1.9694825735E7,3118229.7293},
			{1.96948811016E7,3118269.4462}
						  };// 公共点旧坐标
	// 公共点新坐标
	private double new_common[][] =  {
			{25290.525,46992.5795},
			{24881.5,46932.091},
			{24954.87295,47399.762},
			{24807.07285,47375.4787},
			{24683.12915,47684.15265},
			{24711.3018,47703.3581}
			 };
	private double coneB[][];// 系数矩阵B
	private double TconeB[][];// 技术矩阵B的转置
	private double consL[][];// 常熟项矩阵L
	private double Nbb[][];// 法方程系数矩阵Nbb
	private double DNbb[][];// 法方程系数矩阵Nbb逆矩阵
	private double ENbb[][];// 法方程系数矩阵Nbb的扩展矩阵
	private double W[][];// 用于表示Nbb*TconeB
	private double parameter[][];// 平差参数
	// private double V[][];// 平差改正数
	// private double result2[][];// 用于存储系数B于平差参数parameter
	private double oldpoint[][];// 用于存储需要转换的旧坐标
	private double newpoint[][];// 用于存储旧坐标转换后的新坐标

	private int N;
	private int M;
	/**
	 * 无参数构造
	 */
	public GpsConvert(){
		
	}
	/**
	 * n为公共点个数,m为转换点个数
	 * @param n
	 * @param m
	 */
	public GpsConvert(int n, int m) {
		this.N = n;
		this.M = m;
		InitAll();	
	}
	
	public double[][] getNewpoint() {
		return newpoint;
	}
	
	public double[][] getOldpoint() {
		return oldpoint;
	}

	public void setOldpoint(double[][] oldpoint) {
		this.oldpoint = oldpoint;
	}
	/**
	 * 设置公共点在两个坐标系中的坐标
	 * @param old_common  	公共点原始坐标
	 * @param new_common	公共点新坐标
	 */
//	public void SetCommonPoints(double old_common[][], double new_common[][]){
//		this.old_common = old_common;
//		this.new_common = new_common;
//	}
	

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	/**
	 * 初始化矩阵
	 * @param matrix
	 */
	public void matrixInit(double [][]matrix){
		for ( int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length ;j++)
				matrix[i][j] = 0;
	}
	/**
	 * 初始化所有矩阵
	 */
	public void InitAll() {
//		old_common = new double[N][2];
//		matrixInit(old_common);
//		new_common = new double[N][2];
//		matrixInit(new_common);
		coneB = new double[2 * N][4];
//		matrixInit(coneB);
		TconeB = new double[4][2 * N];
//		matrixInit(TconeB);
		consL = new double[2 * N][1];
//		matrixInit(consL);
		Nbb = new double[4][4];
//		matrixInit(Nbb);
		DNbb = new double[4][4];
//		matrixInit(DNbb);
		ENbb = new double[4][8];
//		matrixInit(ENbb);
		W = new double[4][2 * N];
//		matrixInit(W);
		parameter = new double[4][1];
//		matrixInit(parameter);
		// V = new double[2 * N][1];
		// result2 = new double[2 * N][1];
		oldpoint = new double[M][2];
//		matrixInit(oldpoint);
		newpoint = new double[M][2];
//		matrixInit(newpoint);
	}




	public double[][] convert(){
		if(this.oldpoint!= null && this.oldpoint.length!=0){
			return convert(this.oldpoint);
		}else
			return null;
	}

	/**
	 * 转换函数
	 * @param oldpoint		需要转换的点
	 * @return
	 */
	public double[][] convert(double[][] oldpoint) {

		this.oldpoint = oldpoint;
		
		
		for (int i = 0; i < 2 * this.N; i++) {
			for (int j = 0; j < 4; j++) {
				coneB[i][0] = (0.5 + Math.pow(-1, i) * 0.5);
				coneB[i][1] = (0.5 - Math.pow(-1, i) * 0.5);
				coneB[i][2] = old_common[i / 2][0] * (0.5 + Math.pow(-1, i) * 0.5)
						+ old_common[i / 2][1] * (0.5 - Math.pow(-1, i) * 0.5);
				coneB[i][3] = old_common[i / 2][0] * (0.5 - Math.pow(-1, i) * 0.5)
						+ old_common[i / 2][1] * (-0.5 - Math.pow(-1, i) * 0.5);
			}
		}

		for (int i = 0; i < 2 * N; i++) {
			for (int j = 0; j < 1; j++) {
				consL[i][j] = new_common[i / 2][0] * (0.5 + Math.pow(-1, i) * 0.5)
						+ new_common[i / 2][1] * (0.5 - Math.pow(-1, i) * 0.5);
			}
		}
		
		
		
		transform(this.coneB, this.TconeB, 2 * this.N, 4);
		multiplication(this.TconeB, this.coneB, this.Nbb, 4, 2 * this.N, 4);
		det(this.Nbb, this.ENbb, this.DNbb, 4);
		multiplication(this.TconeB, this.consL, this.W, 4, 2 * this.N, 1);
		multiplication(this.DNbb, this.W, this.parameter, 4, 4, 1);

		for (int i = 0; i < this.M; i++) {
			newpoint[i][0] = parameter[0][0] + oldpoint[i][0] * parameter[2][0]
					- oldpoint[i][1] * parameter[3][0];
			newpoint[i][1] = parameter[1][0] + oldpoint[i][1] * parameter[2][0]
					+ oldpoint[i][0] * parameter[3][0];
		}
		return newpoint;

	}
	
	public double[][] _convert(double[][] oldpoint,boolean flag) {
		if(flag&&_test){
			 double [][]temp = this.old_common;
			 this.old_common = this.new_common;
			 this.new_common = temp;
			 _test = false;
		}
		this.oldpoint = oldpoint;
		
		
		for (int i = 0; i < 2 * this.N; i++) {
			for (int j = 0; j < 4; j++) {
				coneB[i][0] = (0.5 + Math.pow(-1, i) * 0.5);
				coneB[i][1] = (0.5 - Math.pow(-1, i) * 0.5);
				coneB[i][2] = old_common[i / 2][0] * (0.5 + Math.pow(-1, i) * 0.5)
						+ old_common[i / 2][1] * (0.5 - Math.pow(-1, i) * 0.5);
				coneB[i][3] = old_common[i / 2][0] * (0.5 - Math.pow(-1, i) * 0.5)
						+ old_common[i / 2][1] * (-0.5 - Math.pow(-1, i) * 0.5);
			}
		}

		for (int i = 0; i < 2 * N; i++) {
			for (int j = 0; j < 1; j++) {
				consL[i][j] = new_common[i / 2][0] * (0.5 + Math.pow(-1, i) * 0.5)
						+ new_common[i / 2][1] * (0.5 - Math.pow(-1, i) * 0.5);
			}
		}
		
		
		
		transform(this.coneB, this.TconeB, 2 * this.N, 4);
		multiplication(this.TconeB, this.coneB, this.Nbb, 4, 2 * this.N, 4);
		det(this.Nbb, this.ENbb, this.DNbb, 4);
		multiplication(this.TconeB, this.consL, this.W, 4, 2 * this.N, 1);
		multiplication(this.DNbb, this.W, this.parameter, 4, 4, 1);

		for (int i = 0; i < this.M; i++) {
			newpoint[i][0] = parameter[0][0] + oldpoint[i][0] * parameter[2][0]
					- oldpoint[i][1] * parameter[3][0];
			newpoint[i][1] = parameter[1][0] + oldpoint[i][1] * parameter[2][0]
					+ oldpoint[i][0] * parameter[3][0];
		}
		return newpoint;

	}
	
	/**
	 * 求逆函数
	 * @param p
	 * @param b
	 * @param c
	 * @param n
	 */
	public void det(double[][] p, double[][] b, double[][] c, int n) {
		//showMatirx(b);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				b[i][j] = p[i][j];
			}
			for (int j = n; j < 2 * n; j++) {
				if (i + n == j)
					b[i][j] = 1;
				else
					b[i][j] = 0;
			}
		}
		
		//showMatirx(b);
		double m;
		for (int i = 0; i < n; i++) {
			m = b[i][i];
			for (int j = i; j < 2 * n; j++)
				b[i][j] /= m;
			for (int j = i + 1; j < n; j++) {
				m = b[j][i];
				for (int k = i; k < 2 * n; k++)
					b[j][k] = b[j][k] - m * b[i][k];
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			m = b[i][i];
			for (int j = i - 1; j >= 0; j--) {
				m = b[j][i];
				for (int k = i; k < n * 2; k++)
					b[j][k] = b[j][k] - m * b[i][k];
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = n; j < 2 * n; j++) {
				c[i][j - n] = b[i][j];
			}
		}
		
		//showMatirx(b);

	}
	/**
	 * 转置矩阵
	 * @param a
	 * @param b
	 * @param x
	 * @param y
	 */
	public void transform(double[][] a, double[][] b, int x, int y) {
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				b[j][i] = a[i][j];
			}
	}
	/**
	 * 矩阵相乘
	 * @param a
	 * @param b
	 * @param c
	 * @param m
	 * @param s
	 * @param n
	 */
	public void multiplication(double[][] a, double[][] b, double[][] c, int m,
			int s, int n) {
		double sum;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				sum = 0.0;
				for (int k = 0; k < s; k++)
					sum += a[i][k] * b[k][j];
				c[i][j] = sum;
			}
	}

	/**
	 * 打印矩阵
	 * @param matrix
	 */
	public static void showMatirx(double [][] matrix){
		for ( int i = 0; i < matrix.length;i++){
			for (int j = 0;j < matrix[0].length;j++){
				System.out.print(matrix[i][j]+",");
			}
			System.out.println();
		}	
	}
}
