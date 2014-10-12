package cf.webgis.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;

import fy.gisweb.model.ModelPoints;
import fy.gisweb.model.Rings;
import fy.gisweb.service.impl.ModelPointsService;

public class TestModelPoints {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerateModelPoints() {
		ArrayList<Rings> rings = new ArrayList<Rings>();
		rings.add(new Rings(0.0,0.0));
		rings.add(new Rings(0.0,50.0));
		rings.add(new Rings(50,100));
		rings.add(new Rings(100,50));
		rings.add(new Rings(25,0));
		rings.add(new Rings(50,50));
		ModelPointsService<ModelPoints> mps = new ModelPointsService<ModelPoints>();
		int gridSize = 50;//define grid size
		List<ModelPoints> data = mps.generateModelPoints(rings,gridSize);
		assertTrue(data != null);
	}

}
