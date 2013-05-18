package Util;

import DAO.GpsDao;
import PO.Hz_split;
import PO.Hzgpstaxi;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA. User: leo Date: 13-4-22 Time: 下午12:40 Project:
 * GenerateRN
 */
public class GpsUtil {
	private static final int PERIOD_2 = 30000;
	private static final int PERIOD = 20000;
	private static final int SPEED_LIMIT = 70;
	private static final DecimalFormat df = new DecimalFormat("0.0000 ");

	/**
	 * 由经纬度反算成高斯投影坐标
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static double[] GaussToBLToGauss(double longitude, double latitude) {
		int ProjNo = 0;
		int ZoneWide; // //带宽
		double[] output = new double[2];
		double longitude1, latitude1, longitude0, X0, Y0, xval, yval;
		double a, f, e2, ee, NN, T, C, A, M, iPI;
		iPI = 0.0174532925199433; // //3.1415926535898/180.0;
		ZoneWide = 6; // //6度带宽
		a = 6378245.0;
		f = 1.0 / 298.3; // 54年北京坐标系参数
		// //a=6378140.0; f=1/298.257; //80年西安坐标系参数
		ProjNo = (int) (longitude / ZoneWide);
		longitude0 = ProjNo * ZoneWide + ZoneWide / 2;
		longitude0 = longitude0 * iPI;
		longitude1 = longitude * iPI; // 经度转换为弧度
		latitude1 = latitude * iPI; // 纬度转换为弧度
		e2 = 2 * f - f * f;
		ee = e2 / (1.0 - e2);
		NN = a
				/ Math.sqrt(1.0 - e2 * Math.sin(latitude1)
						* Math.sin(latitude1));
		T = Math.tan(latitude1) * Math.tan(latitude1);
		C = ee * Math.cos(latitude1) * Math.cos(latitude1);
		A = (longitude1 - longitude0) * Math.cos(latitude1);
		M = a
				* ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256)
						* latitude1
						- (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2
								/ 1024) * Math.sin(2 * latitude1)
						+ (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024)
						* Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072)
						* Math.sin(6 * latitude1));
		// 因为是以赤道为Y轴的，与我们南北为Y轴是相反的，所以xy与高斯投影的标准xy正好相反;
		xval = NN
				* (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 14
						* C - 58 * ee)
						* A * A * A * A * A / 120);
		yval = M
				+ NN
				* Math.tan(latitude1)
				* (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24 + (61
						- 58 * T + T * T + 270 * C - 330 * ee)
						* A * A * A * A * A * A / 720);
		X0 = 1000000L * (ProjNo + 1) + 500000L;
		Y0 = 0;
		xval = xval + X0;
		yval = yval + Y0;
		output[0] = Double.parseDouble(df.format(xval));
		output[1] = Double.parseDouble(df.format(yval));
		return output;
	}

	public static double[] GaussToBL(double X, double Y)// , double *longitude,
	// double *latitude)
	{
		int ProjNo;
		int ZoneWide; // //带宽
		double[] output = new double[2];
		double longitude1, latitude1, longitude0, X0, Y0, xval, yval;// latitude0,
		double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;
		iPI = 0.0174532925199433; // //3.1415926535898/180.0;
		// a = 6378245.0; f = 1.0/298.3; //54年北京坐标系参数
		a = 6378140.0;
		f = 1 / 298.257; // 80年西安坐标系参数
		ZoneWide = 6; // //6度带宽
		ProjNo = (int) (X / 1000000L); // 查找带号
		longitude0 = (ProjNo - 1) * ZoneWide + ZoneWide / 2;
		longitude0 = longitude0 * iPI; // 中央经线

		X0 = ProjNo * 1000000L + 500000L;
		Y0 = 0;
		xval = X - X0;
		yval = Y - Y0; // 带内大地坐标
		e2 = 2 * f - f * f;
		e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
		ee = e2 / (1 - e2);
		M = yval;
		u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
		fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u)
				+ (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32)
				* Math.sin(4 * u) + (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u)
				+ (1097 * e1 * e1 * e1 * e1 / 512) * Math.sin(8 * u);
		C = ee * Math.cos(fai) * Math.cos(fai);
		T = Math.tan(fai) * Math.tan(fai);
		NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
		R = a
				* (1 - e2)
				/ Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai))
						* (1 - e2 * Math.sin(fai) * Math.sin(fai))
						* (1 - e2 * Math.sin(fai) * Math.sin(fai)));
		D = xval / NN;
		// 计算经度(Longitude) 纬度(Latitude)
		longitude1 = longitude0
				+ (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T
						- 3 * C * C + 8 * ee + 24 * T * T)
						* D * D * D * D * D / 120) / Math.cos(fai);
		latitude1 = fai
				- (NN * Math.tan(fai) / R)
				* (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D
						* D * D * D / 24 + (61 + 90 * T + 298 * C + 45 * T * T
						- 256 * ee - 3 * C * C)
						* D * D * D * D * D * D / 720);
		// 转换为度 DD
		output[0] = longitude1 / iPI;
		output[1] = latitude1 / iPI;
		return output;
		// *longitude = longitude1 / iPI;
		// *latitude = latitude1 / iPI;
	}

	/**
	 * 将从数据库读出来的经纬度通过高斯投影转换成XY坐标
	 * 
	 * @param list
	 */
	public static List<Hzgpstaxi> LngLaiToXY(List<Hzgpstaxi> list) {
		double[] r;
		GpsConvert convert = new GpsConvert(6, 1);
		for (Hzgpstaxi hzgpstaxi : list) {
			r = GaussToBLToGauss(hzgpstaxi.getLongi(), hzgpstaxi.getLati());
			double [][] oldpoint = {{r[0],r[1]}};
			double [][] r1 = convert.convert(oldpoint);
			hzgpstaxi.setPx((long)r1[0][0]);
			hzgpstaxi.setPy((long)r1[0][1]);
			//System.out.println(r1[0][0]+","+r1[0][1]);
		}
		return list;
	}

	public static List<Hzgpstaxi> removeHighSpeedPoint(List<Hzgpstaxi> list) {
		for (int i = 0; i < list.size();) {
			if (list.get(i).getSpeed() >= SPEED_LIMIT) {
				list.remove(i);
			} else {
				i++;
			}
		}
		return list;
	}

	/**
	 * 增加数据
	 * 
	 * @param list
	 * @return
	 */
	public static List<Hzgpstaxi> GpsFilter(List<Hzgpstaxi> list) {

		Hzgpstaxi addone = new Hzgpstaxi();

		for (int i = 0; i < list.size() - 1; i++) {
			long time = list.get(i + 1).getSpeedTime().getTime()
					- list.get(i).getSpeedTime().getTime();
			if (PERIOD <= time && time <= 2 * PERIOD) {

				addone.setVehicleId(list.get(i).getVehicleId());
				addone.setVehicleNum(list.get(i).getVehicleNum());

				addone.setPx((list.get(i).getPx() + list.get(i + 1).getPx()) / 2);
				addone.setPy((list.get(i).getPy() + list.get(i + 1).getPy()) / 2);

				Timestamp timestamp = new Timestamp(list.get(i).getSpeedTime()
						.getTime() + 5000);

				addone.setSpeedTime(timestamp);
				list.add(i + 1, addone);
			}
			// result.add(nextPoint);
		}
		return list;
	}

	/**
	 * 数据分割,如果一个list里的数据中间间隔大于30s则检测为两条线.
	 * 
	 * @param list
	 * @return
	 */
	public static List<Hz_split> splitData(List<Hzgpstaxi> list) {
		List<Hz_split> result = new ArrayList<Hz_split>();
		/*		int i = 0, start = 0;

		for (int j = 0; j < list.size() - 1; j++) {
			if (list.get(j + 1).getSpeedTime().getTime()
					- list.get(j).getSpeedTime().getTime() > PERIOD_2) {
				Hz_split addone = new Hz_split();
				addone.setNum(i++);
				addone.setList(list.subList(start, j + 1));
				start = j + 1;
				result.add(addone);
			}
		}*/
		int j = 0,m = 0;
		for(int i = 0; i < list.size();i++){
			if(list.get(i).getVehicleId()!=list.get(m).getVehicleId()){
				Hz_split addone = new Hz_split();
				addone.setNum(j++);
				addone.setList(list.subList(m, i-1));
				m = i;
				result.add(addone);
			}
		}
		Hz_split addone = new Hz_split();
		addone.setNum(j++);
		addone.setList(list.subList(m, list.size()-1));
		result.add(addone);
		return result;
	}
	
	public static void main(String args[]){
		GpsDao dao = new GpsDao();
		List<Hzgpstaxi> result = dao.QueryAll();
		result = GpsUtil.LngLaiToXY(result);
		dao.modify(result);
	}

}
