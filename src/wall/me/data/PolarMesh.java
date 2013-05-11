package wall.me.data;

import java.util.ArrayList;
import java.util.List;

public class PolarMesh {
	private int range;
	private List<PolarSample[]> columns;
	private int currentFilling;
	private PolarSample[] currentCol;
	
	public PolarMesh(int range) {
		this.range = range;
		columns = new ArrayList<PolarSample[]>();
		currentCol = null;
	}
	
	public void add(PolarSample sample) {
		if (currentCol == null) {
			currentCol = new PolarSample[range];
			columns.add(currentCol);
			currentFilling = 0;
		}
		
		currentCol[currentFilling++] = sample;
		if (currentFilling >= range) {
			// Reverse row if necessary
			if (currentCol[0].beta > currentCol[1].beta) {
				for (int i = 0; i < range / 2; i++) {
					PolarSample t = currentCol[i];
					currentCol[i] = currentCol[range - 1 - i];
					currentCol[range - 1 - i] = t;
				}
			}
			currentCol = null;
		}
	}
	
	public List<PolarSample[]> getColumns() {
		return columns;
	}
	
	public int getNPoints() {
		return range * columns.size();
	}
}
