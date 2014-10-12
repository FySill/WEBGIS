/**
 * @author chengfei
 *
 */
package fy.gisweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import fy.gisweb.model.ModelPoints;
import fy.gisweb.model.Points;
import fy.gisweb.model.Rings;
import fy.gisweb.service.IModelPointsService;

public  class ModelPointsService<BasePoints> implements IModelPointsService<BasePoints>{

	public List<ModelPoints> generateModelPoints(ArrayList<Rings> rings, double gridSize) {

		List<ModelPoints> modelPoints = new ArrayList<ModelPoints>();
		
		int length = rings.size();
		int minIndex = 0; int maxIndex  = 0;
		for (int i = 0; i < length; i++) {
			System.out.println(rings.get(i).getX());
	    	if (rings.get(i).getX() < rings.get(minIndex).getX()) 
	    		minIndex = i;
	    	if (rings.get(i).getX() > rings.get(maxIndex).getX()) 
	    		maxIndex = i;
		}
		//
		System.out.println(rings);
		System.out.println(length);
		//
		List<Rings> newRings = new ArrayList<Rings>();

	    int previous = minIndex;
	    sink2raise(previous, maxIndex, rings, length, newRings, gridSize, 1, modelPoints);
	    System.out.println("left-right");
	        
	    previous = maxIndex;
	    sink2raise(previous, minIndex, rings, length, newRings, gridSize, -1, modelPoints);
	    System.out.println("right-left");
	    
		generateModalPoint(newRings, gridSize, modelPoints);
		System.out.println("modelPoints" + modelPoints);
		return modelPoints;
	}

	private void sink2raise(int previous, int endIndex, ArrayList<Rings> rings,
			int length, List<Rings> newRings, double gridSize, int isdown, List<ModelPoints> modelPoints) {
//		List<Rings> polygon = new ArrayList<Rings>();
		int tmp = 0;
		while (previous != endIndex) {
			if (isdown * rings.get(previous).getX() <= isdown * rings.get((previous + length - 1) % length).getX()) {
				newRings.add(rings.get(previous));
	    		previous = (previous + length - 1) % length;
	    		tmp = previous;
			} else {
				List<Rings> polygon = new ArrayList<Rings>();
				if (isdown * rings.get((previous + length - 1) % length).getY() < isdown * rings.get(tmp).getY()){
//					polygon.add(rings.get(tmp));
					while (previous != endIndex && isdown * rings.get(previous).getX() <= isdown * rings.get(tmp).getX()) {
			    		polygon.add(rings.get(previous));
			    		previous = (previous + length - 1) % length;
			    	} 
					polygon.add(rings.get(previous));
				} else {
					previous = (previous + length - 1) % length;
					polygon.add(rings.get(previous));
					while (previous != endIndex && isdown * rings.get(previous).getX() <= isdown * rings.get(tmp).getX()) {
			    		polygon.add(rings.get(tmp));
			    		tmp = (tmp + length + 1) % length;
			    		newRings.remove(newRings.size()-1);
			    	} 
					polygon.add(rings.get(tmp));
					
				}
				if (polygon.size() > 2) {
					generateModalPoint(polygon, gridSize, modelPoints);
				}
			}
				
		}
//		if (isdown){
//			while (previous != endIndex) {
//				
//		    	int tmp;
//		    	if (rings.get(previous).getY() >= rings.get((previous + length - 1) % length).getY()){
//			    	while (rings.get(previous).getX() <= rings.get((previous + length - 1) % length).getX()) {
//			    		newRings.add(rings.get(previous));
//			    		previous = (previous + length - 1) % length;
//			    	}
//			    	newRings.add(rings.get(previous));
//			    	tmp = previous;
//			    	List<Rings> polygon = new ArrayList<Rings>();
//			    	while (previous != endIndex && rings.get(previous).getX() <= rings.get(tmp).getX()) {
//			    		polygon.add(rings.get(previous));
//			    		previous = (previous + length - 1) % length;
//			    	} 
//	
//			    	polygon.add(rings.get(previous));
//			    	if (polygon.size() > 1)
//			    		generateModalPoint(polygon, gridSize, modelPoints);
//			    } else {
//			    	
//			    }
//	    	}
//		} else {
//			while (previous != endIndex) {
//		    	int tmp;
//		    	while (rings.get(previous).getX() >= rings.get((previous + length - 1) % length).getX()) {
//		    		newRings.add(rings.get(previous));
//		    		previous = (previous + length - 1) % length;
//		    	}
//		    	newRings.add(rings.get(previous));
//		    	tmp = previous;
//		    	List<Rings> polygon = new ArrayList<Rings>();
//		    	while (previous != endIndex && rings.get(previous).getX() >= rings.get(tmp).getX()) {
//		    		polygon.add(rings.get(previous));
//		    		previous = (previous + length - 1) % length;
//		    	} 
//		    	polygon.add(rings.get(previous));
//		    	if (polygon.size() > 1) 
//		    		generateModalPoint(polygon, gridSize, modelPoints);
//		    }
//		}
		
	}

	private void generateModalPoint(List<Rings> newRings, double gridSize, List<ModelPoints> modelPoints) {
		int minIndex = 0; 
		int length = newRings.size();
		 System.out.println("+++++++++");
		 for (int i = 0; i < length; i++) {
			 System.out.println (newRings.get(i).getX());
		    }
		 System.out.println("+++++++++");
				
	    for (int i = 0; i < length; i++) {
	    	if (newRings.get(i).getX() < newRings.get(minIndex).getX()) 
	    		minIndex = i;
	    }
	    int previous = (minIndex + length - 1) % length;
	    int next = (minIndex + length + 1) % length;
	    double line = newRings.get(minIndex).getX();
	    List<ModelPoints> modalPoints = new ArrayList<ModelPoints>();
	    while (previous != (next + length - 1) % length) {
		    if (newRings.get(previous).getX() < newRings.get(next).getX()) {
				if (newRings.get(previous).getX() != newRings.get((previous + length + 1) % length).getX()) 
			        savePoints(modalPoints, newRings, previous, next, line, gridSize, length, newRings.get(previous).getX());
			    previous = (previous + length - 1) % length;
		    } else {
				if (newRings.get(next).getX() != newRings.get((next + length - 1) % length).getX()) 
			        savePoints(modalPoints, newRings, previous, next, line, gridSize, length, newRings.get(next).getX());
			    next = (next + length + 1) % length;
		    } 
		}		
	}

	private void savePoints(List<ModelPoints> modelPoints,
			List<Rings> rings, int previous, int next, double line,
			double gridSize, int length, double maxline) {
	    while (line <= maxline) {
	    	double y1 = calculateYValue(rings.get(previous), rings.get((previous + length + 1) % length), line);
	    	double y2 = calculateYValue(rings.get(next), rings.get((next + length - 1) % length), line);

		    while (y1 < y2) {
		    	Points modelPoint = new ModelPoints();
		    	modelPoint.setX(line);
		    	modelPoint.setY(y1);
		        modelPoints.add((ModelPoints) modelPoint);
		        y1 += gridSize;
		    }
		    while (y1 >= y2) {
		    	Points modelPoint = new ModelPoints();
		    	modelPoint.setX(line);
		    	modelPoint.setY(y2);
		        modelPoints.add((ModelPoints) modelPoint);
		        y2 += gridSize;
		    }
		    line += gridSize;
		}
	}

	private double calculateYValue(Rings pointM, Rings pointN, double line) {
	    double y = pointM.getY() + (pointN.getY() - pointM.getY()) / (pointN.getX() - pointM.getX())*(line - pointM.getX());
	    return y;
	}
}