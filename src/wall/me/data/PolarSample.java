package wall.me.data;

import org.jzy3d.maths.Coord3d;

public class PolarSample {
	// L1 : entre Servo 1 et Servo 2 (bras du Servo 1)
	// L2 : bras du Servo 2
	// L3 : hauteur du capteur
	public static final double L1 = 2.0 / MeshReader.ZSCALE, L2 = 5.0 / MeshReader.ZSCALE, L3 = 1.0 / MeshReader.ZSCALE;
	
	public double alpha;
	public double beta;
	public double distance;
	
	public PolarSample(double alpha, double beta, double distance) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.distance = distance;
	}
	
	public Coord3d toCoord3d() {
		Coord3d coord = new Coord3d(alpha, beta, distance);
		coord = coord.cartesian();
		
		// Correction des bras de levier -- change pas grand-chose et pas sžr que la formule soit bonne...
//		coord.x -= Math.cos(alpha) * (L1 + L2 * Math.cos(beta));
//		coord.y -= Math.sin(alpha) * (L1 + L2 * Math.cos(beta));
//		coord.z -= L2 * Math.sin(beta) + L3 * Math.cos(beta);
		
		return coord;
	}
	
	@Override
	public String toString() {
		double x = deg(alpha);
		double y = deg(beta);
		double z = distance * MeshReader.ZSCALE;
		return x + " - " + y + " - " + z;
	}
	
	private double deg(double rad) {
		return rad * 180 / Math.PI;
	}
}