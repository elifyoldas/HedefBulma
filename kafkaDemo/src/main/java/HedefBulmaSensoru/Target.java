package HedefBulmaSensoru;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Target {
	public Target() {
		super();
		this.coordinate = CoordinateSystem.createRandomPoint();
	}

	private Point2D.Double coordinate;

	public Point2D.Double getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point2D.Double coordinate) {
		this.coordinate = coordinate;
	}
}
