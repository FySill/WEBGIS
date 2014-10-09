/**
 * @author chengfei
 *
 */
package fy.gisweb.service;

import java.lang.reflect.Array;
import java.util.List;

public interface IBasePointsService<Entity> {
	
	public List<Entity> generateBasePoints(Array[] rings);
}