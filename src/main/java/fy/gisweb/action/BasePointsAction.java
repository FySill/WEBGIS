package fy.gisweb.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
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
		request.getParameter("data");
		request.getAttribute("data");
		
		// Array[] rings = requests
		
		// List<BasePoints> data = basePointsService.generateBasePoints(rings);
		return SUCCESS;
	}

}
