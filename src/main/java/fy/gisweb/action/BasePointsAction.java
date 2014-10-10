package fy.gisweb.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BasePointsAction extends ActionSupport {
	private HttpServletRequest request;
	private HttpServletResponse response;

	// private BasePoints basePoints;
	// private BasePointsService basePointsService;
	public String BasePoints() throws IOException {
		request = ServletActionContext.getRequest();
		String dataSource = request.getParameter("data");
		String dataNum = dataSource.substring(2, dataSource.length()-2);
		
//		request.getAttribute("color");
		String[] strs = dataNum.split("\\,");
		List<Double> nums = new ArrayList<Double>();
		for(String string:strs){
			System.out.println(string);
			double num=Double.parseDouble(string);
			nums.add(num);
		}
		for(Double n:nums){
			System.out.println(n);
		}
		// Array[] rings = requests
		
		// List<BasePoints> data = basePointsService.generateBasePoints(rings);
		return SUCCESS;
	}

}
