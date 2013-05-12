package wall.me.data;

import java.io.PrintWriter;

/**
 * GŽnŽrateur de fichier de test sous forme de demi-cube (ou presque)
 * @author tvial
 *
 */
public class SampleGeneratorBox {
	public static void main(String[] args) throws Exception {
		String fileName = "/tmp/sample.txt";
		if (args.length >= 1) {
			fileName = args[0];
		}
		
		System.out.println("Generating " + fileName);
		PrintWriter pw = new PrintWriter(fileName);
		
		double s = Math.sqrt(3.0);
		
		for (int alpha = 0; alpha <= 180; alpha += 2) {
			for (int beta = 0; beta <= 180; beta += 2) {
				double x = cap(s * Math.cos(rad(alpha)) * Math.cos(rad(beta)));
				double y = cap(s * Math.sin(rad(alpha)) * Math.cos(rad(beta)));
				double z = cap(s * Math.sin(rad(beta)));
				
				double d = 200.0 * Math.sqrt(x * x + y * y + z * z);
				
				pw.println(alpha + ";" + beta + ";" + d);
			}
		}
		
		pw.close();
		System.out.println("Done");
	}
	
	private static double rad(double alpha) {
		return alpha * Math.PI / 180.0;
	}
	
	private static double cap(double x) {
		return x < -1.0 ? -1.0 : x > 1.0 ? 1.0 : x;
	}
}
