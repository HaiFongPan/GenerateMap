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
public class getXYData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public getXYData() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html; charset=UTF-8");
        GpsDao sample = new GpsDao();
        List<Hzgpstaxi> GpsData = sample.QueryByVNum(10005);
        if (GpsData != null) {
            GpsData = GpsUtil.LngLaiToXY(GpsData);

			Iterator<Hzgpstaxi> it = GpsData.iterator();
            while(it.hasNext()){
                Hzgpstaxi temp = it.next();

                System.out.println(temp.getPx()+" "+temp.getPy());

			}
            String jsondata = JSON.toJSONString(GpsData);
            System.out.println(jsondata);
            PrintWriter out = response.getWriter();
            out.write(jsondata);
        }
    }

}
