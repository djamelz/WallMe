package wall.me.main;

import wall.me.data.MeshReader;
import wall.me.data.PolarMesh;
import wall.me.data.PolarSample;

public class WallMe {
	public static void main(String[] args) throws Exception {
		String fileName = "@" + args[0];
		System.out.println("Reading " + fileName);
		MeshReader mr = new MeshReader(fileName);
		
		try {
			PolarSample sample;
			int range = mr.getAlphaRange();
			System.out.println("Range = " + range);
			PolarMesh mesh = new PolarMesh(range);
			
			do {
				sample = mr.nextSample();
				if (sample != null) {
					mesh.add(sample);
				}
			} while (sample != null);
			
			System.out.println("Smoothing");
			mesh = mesh.smooth();
			System.out.println("Displaying");
			new Display(mesh).run();
		}
		finally {
			mr.dispose();
		}
	}
}
