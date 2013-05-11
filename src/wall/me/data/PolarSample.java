package wall.me.data;

import org.jzy3d.maths.Coord3d;

public class PolarSample {
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
		return coord.cartesian();
	}
}