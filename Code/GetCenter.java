package application;

public class GetCenter {
	// These methods return the center of the previous stage, which will help align
	// the next stage
	public static double getX(double xMin, double xMax) {
		return (xMin + ((xMax - xMin) / 2));
	}

	public static double getY(double yMin, double yMax) {
		return (yMin + ((yMax - yMin) / 2));
	}
}
