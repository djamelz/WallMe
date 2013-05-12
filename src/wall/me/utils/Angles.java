package wall.me.utils;

public class Angles {
	
	public static double rad(double deg) {
		return Math.PI * deg / 180.0;
	}
	
	public static double deg(double rad) {
		return rad * 180 / Math.PI;
	}
}
