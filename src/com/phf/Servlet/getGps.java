package com.phf.Servlet;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-22
 * Time: 上午11:28
 * Project: GenerateRN
 */

import com.alibaba.fastjson.JSON;
import com.phf.DAO.GpsDao;
import com.phf.PO.Coord;
import com.phf.PO.Hz_split;
import com.phf.PO.Hzgpstaxi;
import com.phf.PO.Matrix;
import com.phf.Util.GpsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

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
		System.out.println("=====调用GetGps方法=====");
		GpsDao sample = new GpsDao();
		int _px = Integer.parseInt(request.getParameter("px"));
		int _py = Integer.parseInt(request.getParameter("py"));
		List<Hzgpstaxi> GpsData = sample.QueryBy(_px, _px + 1000, _py,
				_py + 3000);
		int max_x = 0, max_y = 0, min_x = 0, min_y = 0, index = 0, temp_x, temp_y;
		if (GpsData != null) {
			// GpsData = GpsUtil.GpsFilter(GpsData);
			Iterator<Hzgpstaxi> it = GpsData.iterator();
			while (it.hasNext()) {

				Hzgpstaxi temp = it.next();
				if (temp.getSpeed() >= 85) {
					it.remove();
					continue;
				}

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
			System.out.println("=====Gps点数=====\n"+GpsData.size());
			System.out.println("=====X最大值=====X最小值=====Y最大值=====Y最小值=====\n"+GpsData.size());
			System.out.println(max_x + "," + min_x + "," + max_y + "," + min_y);
			Matrix matrix = new Matrix(GpsData, max_x, max_y, min_x, 146407,
					_px - 791000, 0);
			matrix.calcMatrix(1);
			matrix.tempToMatrix();

			matrix.calcMatrix(0);
			matrix.tempToMatrix();

			matrix.calcToGrid();
			matrix.tempToMatrix();

			List<Coord> temp = matrix.getXys();
			String jsondata = JSON.toJSONString(temp);
			System.out.println("=====计算后Gps点数=====\n"+temp.size());
			PrintWriter out = response.getWriter();
			System.out.println("=====完成=====");
			out.write(jsondata);
		}
	}
}
