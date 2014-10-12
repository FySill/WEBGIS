package fy.gisweb.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import fy.gisweb.model.Extend;
import fy.gisweb.model.ModelPoints;
import fy.gisweb.model.Points;
import fy.gisweb.model.Rings;
import fy.gisweb.service.impl.ModelPointsService;

public class BasePointsAction extends ActionSupport {
	private HttpServletRequest request;
	private HttpServletResponse response;

	// private BasePoints basePoints;
	// private BasePointsService basePointsService;
	public String BasePoints() throws IOException {
		request = ServletActionContext.getRequest();
		String dataSource = request.getParameter("data");
		//string2double
		String dataNum = dataSource.substring(2, dataSource.length()-2);
		String[] strs = dataNum.split("\\,");
		List<Double> nums = new ArrayList<Double>();
		for(String string:strs){
			System.out.println(string);
			double num=Double.parseDouble(string);
			nums.add(num);
		}
		//double2XY & generateExtend
		Points bps = new Rings(); 
		Extend exd = new Extend();
		ArrayList<Rings> rings = new ArrayList<Rings>();
		for(int i = 0;i < nums.size()-2;i=i+2){
			double x = nums.get(i);
			double y = nums.get(i+1);
			if(i == 0){
				exd.setBottom(y);
				exd.setTop(y);
				exd.setLeft(x);
				exd.setRight(x);
			}else{
				if(exd.getLeft()>x){
					exd.setLeft(x);
				}
				if(exd.getRight()<x){
					exd.setRight(x);
				}
				if(exd.getBottom()>y){
					exd.setBottom(y);
				}
				if(exd.getTop()<y){
					exd.setTop(y);
				}
			}
			bps.setX(x);
			bps.setY(y);
			rings.add((Rings)bps);
			
		}
		//generateBasePoints
		ModelPointsService<ModelPoints> mps = new ModelPointsService<ModelPoints>();
		double gridSize = 5;//define grid size
		List<ModelPoints> data = mps.generateModelPoints(rings,gridSize);
		return SUCCESS;
	}

}
