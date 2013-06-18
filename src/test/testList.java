package test;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA. User: leo Date: 13-4-22 Time: 下午4:08 Project:
 * GenerateRN<
 */
public class testList {
	public static List<Integer> removeHighSpeedPoint(List<Integer> list) {
		for (int i = 0; i < list.size()-1;i++) {
			if (list.get(i+1)-list.get(i)>10) {
				list.add(i+1, (list.get(i+1)+list.get(i))/2);
			}
			System.out.println(list.get(i));
		}

		return list;
	}

	public static void main(String args[]) {
		double new_common1[][] =  {
				{50581.0500,93985.1590},
				{49763.0000,93864.1820},
				{49909.7459,94799.5240},
				{49614.1457,94750.9574},
				{49366.2583,95368.3053},
				{49422.6036,95406.7162}
				  };
		for(int i = 0 ; i < new_common1.length;i++)
			System.out.println("{"+new_common1[i][0]/4+","+new_common1[i][1]/4+"},");
			

	}
}
