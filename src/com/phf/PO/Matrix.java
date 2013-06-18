package com.phf.PO;

import java.util.ArrayList;
import java.util.List;

import com.phf.PO.Hzgpstaxi;
import com.phf.Util.roadNetworkUtil;

/**
 * Created with IntelliJ IDEA. User: leo Date: 13-5-2 Time: 下午9:13 Project:
 * GenerateRN
 */
public class Matrix {
	// private static final int MAX = 1000;
	private long offset_x;
	private long offset_y;
	private long matrix[][];
	private long matrix_temp[][];
	private List<Coord>xys = new ArrayList<Coord>();
	private int m;
	private int n;
	private int min1,min2;
	private long [][]N5 = new long[5][5];
	public List<Coord> getXys() {
		for(int i = 0; i < matrix.length;i++)
			for(int j = 0; j < matrix[i].length;j++)
				if(matrix[i][j]==1)
					xys.add(new Coord((i+offset_x),(j+offset_y)));
		return xys;
	}
	public void setXys(List<Coord> xys) {
		this.xys = xys;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public long[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(long[][] matrix) {
		this.matrix = matrix;
	}
	
	public long[][] getMatrix_temp() {
		return matrix_temp;
	}
    public void setMatrix(List<Hzgpstaxi>points){
        for(Hzgpstaxi point:points){
//        	System.out.println((int) point.getPx()+","+(int) point.getPy());
            this.matrix[(int) point.getPx()-min1][(int) point.getPy()-min2] = 1;
        }
    }
	public void setMatrix_temp(long[][] matrix_temp) {
		this.matrix_temp = matrix_temp;
	}

	public Matrix() {

	}

	public long[][] initMatrix(long[][] a,int mm,int nn) {
		a = new long [mm][nn];
		for (int i = 0; i <mm; i++)
			for (int j = 0; j <nn; j++)
				a[i][j] = 0;
		return a;
	}

	public Matrix(List<Hzgpstaxi>points,int m,int n,int min1,int min2,int offset_x,int offset_y) {
		this.m = m+10-min1;
		this.n = n+10-min2;
		this.min1 = min1;
		this.min2 = min2;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		matrix = initMatrix(matrix,this.m,this.n);
		matrix_temp = initMatrix(matrix_temp,this.m,this.n);

		this.setMatrix(points);

	}

	/**
	 * 将矩阵以i,j为中心的3x3矩阵计算出临时矩阵
	 */
	public void calcMatrix(int value) {
		int v[]={1,0};
		for (int i = 1; i < m-1; i++){
			for (int j = 1; j < n-1; j++) {
//			System.out.println(i+","+j);
				if (matrix[i - 1][j - 1] == value || matrix[i - 1][j] == value
						|| matrix[i - 1][j + 1] == value || matrix[i][j - 1] == value
						|| matrix[i][j] == value || matrix[i][j + 1] == value
						|| matrix[i + 1][j - 1] == value || matrix[i + 1][j] == value
						|| matrix[i + 1][j + 1] == value) {
					matrix_temp[i][j] = value;
				}else{
					matrix_temp[i][j] = v[value];
				}
			}
        }
	}
	public void tempToMatrix(){
		for ( int i = 0; i < matrix.length;i++){
			for (int j = 0;j < matrix[0].length;j++){
				matrix[i][j] = matrix_temp[i][j];
			}
			
		}	
	}
	public static void showMatirx(long [][] matrix){
		int x = 0;
		for ( int i = 0; i < matrix.length;i++){
			for (int j = 0;j < matrix[0].length;j++){
				System.out.print(matrix[i][j]+",");
			}
			System.out.println();	
		}	
		System.out.println();	
	}

	private int [] index ={-2,-1,0,1,2};
	public void calcToGrid(){
		for (int i = 2; i < m-2; i++){
			for (int j = 2; j < n-2; j++) {
				for(int x = 0; x < 5;x++){
					for(int y =0;y < 5; y++){
						N5[x][y] = matrix[i+index[x]][j+index[y]];
					}
					
				}
				
				if(roadNetworkUtil.stepOne(N5)&&
						roadNetworkUtil.stepTwo(N5)&&
						roadNetworkUtil.stepThree(N5)&&
						roadNetworkUtil.stepFour(N5))
					matrix_temp[i][j]=1;
				else
					matrix_temp[i][j]=0;
			}
        }
	}
	
	
}
