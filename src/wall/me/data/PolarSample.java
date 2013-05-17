package wall.me.data;

import org.jzy3d.maths.Coord3d;

import wall.me.utils.Angles;

/**
 * Echantillon en coordonnées polaires (alpha, beta, distance)
 * Les distances sont divisées par DIST_SCALE à la conversion en coord cartésiennes
 * @author tvial
 *
 */
public class PolarSample {
	private static final double DIST_SCALE = 1.0; //400.0;
	
	// L1 : entre Servo 1 et Servo 2 (bras du Servo 1)
	// L2 : bras du Servo 2
	// L3 : hauteur du capteur
	public static final double L1 = 2.0 / DIST_SCALE, L2 = 5.0 / DIST_SCALE, L3 = 1.0 / DIST_SCALE;
	
	// Angles en radians (conversion faite dans MeshReader)
	public double alpha;
	public double beta;
	public double distance;
	
	public PolarSample(double alpha, double beta, double distance) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.distance = distance;
	}
	
	// Conversion en cartésien
	public Coord3d toCoord3d() {
		Coord3d coord = new Coord3d(alpha, beta, distance / DIST_SCALE);
		coord = coord.cartesian();
		
		// Correction des bras de levier
		double cx = L1, cy = L2, cz = L3;
		double cx1 = cx, cy1 = cy * Math.cos(beta) - cz * Math.sin(beta), cz1 = cy * Math.sin(beta) + cz * Math.cos(beta);
		double cx2 = cx1 * Math.cos(alpha) - cy1 * Math.sin(alpha), cy2 = cx1 * Math.sin(alpha) + cy1 * Math.cos(alpha), cz2 = cz1;
		
		coord.x += (float)cx2;
		coord.y += (float)cy2;
		coord.z += (float)cz2;
		
		return coord;
	}
	
	@Override
	public String toString() {
		// Coord polaires réaffichées à l'échelle du fichier lu (pour débogage)
		double x = Angles.deg(alpha);
		double y = Angles.deg(beta);
		double z = distance;
		return x + " - " + y + " - " + z;
	}

}