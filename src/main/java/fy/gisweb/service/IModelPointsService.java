/**
 * @author chengfei
 *
 */
package fy.gisweb.service;

import java.util.ArrayList;
import java.util.List;

import fy.gisweb.model.ModelPoints;
import fy.gisweb.model.Rings;

public interface IModelPointsService<Entity> {
	
	public List<ModelPoints> generateModelPoints(ArrayList<Rings> rings, double gridSize);
}