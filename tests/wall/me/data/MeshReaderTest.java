package wall.me.data;

/**
 * Tests U de MeshReader
 * Pas ˆ jour + devrait utiliser JUnit
 * @author tvial
 *
 */
public class MeshReaderTest {
	public static void main(String[] args) throws Exception {
		testNextSample();
		testGetAlphaRange();
		testResetAfterGetAlphaRange();
	}
	
	private static void testNextSample() throws Exception {
		MeshReader mr = mr("1.23;4.56;7.89");
		PolarSample sample = mr.nextSample();
		
		assertEquals(sample.alpha, rad(1.23));
		assertEquals(sample.beta, rad(4.56));
		assertEquals(sample.distance, scl(7.89));
	}
	
	private static void testGetAlphaRange() throws Exception {
		MeshReader mr = mr("2;2;3\n2;5;6\n2;8;9\n1;5;6");
		assertEquals(mr.getAlphaRange(), 3);
	}
	
	private static void testResetAfterGetAlphaRange() throws Exception {
		MeshReader mr = mr("2;2;3\n2;5;6\n2;8;9\n1;5;6");
		mr.getAlphaRange();
		PolarSample sample = mr.nextSample();
		
		assertEquals(sample.alpha, rad(2));
		assertEquals(sample.beta, rad(2));
		assertEquals(sample.distance, scl(3));		
	}
	
	private static MeshReader mr(String str) throws Exception {
		return new MeshReader(str);
	}
	
	private static void assertEquals(double a, double b) {
		if (a != b) {
			throw new RuntimeException(a + " != " + b);
		}
	}
	
	private static void assertEquals(int a, int b) {
		if (a != b) {
			throw new RuntimeException(a + " != " + b);
		}
	}
	
	private static double rad(double deg) {
		return Math.PI * deg / 180.0;
	}
	
	private static double scl(double z) {
		return z;
	}
}
