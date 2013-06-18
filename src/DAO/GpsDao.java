package DAO;

import PO.Hzgpstaxi;
import PO.Hzgpstaxi_tmp;
import Util.hibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-22
 * Time: 下午12:13
 * Project: GenerateRN
 */
public class GpsDao {
	/**
	 * 30.330657,120.138803
	 * 30.314395,120.150561
	 */
    private Session session;
    private String hql_query_all = "from Hzgpstaxi limit 0,10";
    private String hql_query_by_vehicle_num = "from Hzgpstaxi where vehicle_id =? order by speedTime";
    private String hql_query = "from Hzgpstaxi where px >=? and px <= ?";
    public GpsDao() {
    }
    
    public void modify(List<Hzgpstaxi> points){
    	session = hibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	for(Hzgpstaxi point:points)
    		session.update(point);
    	session.getTransaction().commit();
    	session.close();
    }
    public List<Hzgpstaxi> QueryBy(long min_x,long max_x){
        session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql_query);// where px >=1580000 and px <= 1585000 and py <= 295000 and py>=290000");
//        query.setFirstResult(2);
        query.setParameter(0, min_x);
        query.setParameter(1, max_x);
        query.setMaxResults(200000);
        @SuppressWarnings("unchecked")
		List<Hzgpstaxi>result = query.list();
        session.close();
        return result;
    }
    public List<Hzgpstaxi> QueryAll(){
        session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Hzgpstaxi");// where px >=1580000 and px <= 1585000 and py <= 295000 and py>=290000");
//        query.setFirstResult(2);
//        query.setMaxResults(200000);
        @SuppressWarnings("unchecked")
		List<Hzgpstaxi>result = query.list();
        session.close();
        return result;
    }
    public void insert(List<Hzgpstaxi> points){
        session = hibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for(Hzgpstaxi point:points){
            Hzgpstaxi_tmp point_tmp = new Hzgpstaxi_tmp(point);
            //System.out.println(point_tmp.getSpeedTime());
            session.saveOrUpdate(point_tmp);
        }
            
        session.getTransaction().commit();
        session.close();
    }
    public List<Hzgpstaxi> QueryByVNum(int id){
        session = hibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql_query_by_vehicle_num );
//        query.setMaxResults(10);
        query.setParameter(0,id);
        @SuppressWarnings("unchecked")
		List<Hzgpstaxi>result = query.list();
        session.close();
        return result;
    }
    
    public static void main(String args[]){
    	GpsDao dao = new GpsDao();
    	List<Hzgpstaxi> result = dao.QueryAll();
    	
    		
    	dao.insert(result);
    }
}
