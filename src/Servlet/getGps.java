package Servlet;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-22
 * Time: 上午11:28
 * Project: GenerateRN
 */

import DAO.GpsDao;

import PO.Hzgpstaxi;
import PO.Matrix;
import PO.Coord;

import Util.GpsUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;


@WebServlet("/getGps")
public class getGps extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Matrix matrix;

	/**
	 * Default constructor.
	 */
	public getGps() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("调用Gps!");
		GpsDao sample = new GpsDao();
		int param = Integer.parseInt(request.getParameter("param"));
		System.out.println("param:" + param);
		List<Hzgpstaxi> GpsData = sample.QueryBy(param, param + 600);
		int max_x = 0, max_y = 0, min_x = 0, min_y = 0, index = 0, temp_x, temp_y;
		if (GpsData != null) {
			// Collections.sort(GpsData,new Comparator<Hzgpstaxi>() {
			// public int compare(Hzgpstaxi o1, Hzgpstaxi o2) {
			// if(o1.getVehicleId() < o2.getVehicleId())
			// return 1;
			// else
			// return o1.getSpeedTime().compareTo(o2.getSpeedTime());
			// }
			// });
			// List<Hz_split> lists = GpsUtil.splitData(GpsData);

			// String jsondata = JSON.toJSONString(lists);
			// System.out.println(jsondata);
			// PrintWriter out = response.getWriter();
			// out.write(jsondata);

			Iterator<Hzgpstaxi> it = GpsData.iterator();
			System.out.println(GpsData.size());
			while (it.hasNext()) {

				Hzgpstaxi temp = it.next();
				if (temp.getSpeed() >= 75) {
					it.remove();
					continue;
				}
				// temp_x = (int) (temp.getPx()-1584000);
				// temp_y = (int) (temp.getPy()-297680);
				// if(temp_x > max_x)
				// max_x = temp_x;
				// if(temp_y > max_y)
				// max_y = temp_y;

				//
				// /*
				// find max and min in list
				// */
				temp_x = (int) temp.getPx();
				temp_y = (int) temp.getPy();
				if (index == 0) {
					max_x = temp_x;
					min_x = temp_x;
					max_y = temp_y;
					min_y = temp_y;
					index++;
				} else {
					if (temp_x > max_x)
						max_x = temp_x;
					else if (temp_x < min_x)
						min_x = temp_x;
					if (temp_y > max_y)
						max_y = temp_y;
					else if (temp_y < min_y)
						min_y = temp_y;
				}
				//

				//
				// temp.setPx(temp_x);
				// temp.setPy(temp_y);

			}
			System.out.println(GpsData.size());
			System.out.println(max_x + "," + min_x + "," + max_y + "," + min_y);
			Matrix matrix = new Matrix(GpsData, max_x, max_y, min_x, min_y,
					param - 396000, 0);
			matrix.calcMatrix(1);
			matrix.tempToMatrix();

			matrix.calcMatrix(0);
			matrix.tempToMatrix();

			matrix.calcToGrid();
			matrix.tempToMatrix();

			List<Coord> temp = matrix.getXys();
			if (temp.size() > 100) {
				String jsondata = JSON.toJSONString(temp);
				System.out.println(temp.size());
				PrintWriter out = response.getWriter();
				System.out.println("done");
				out.write(jsondata);
			}
		}
	}
}
