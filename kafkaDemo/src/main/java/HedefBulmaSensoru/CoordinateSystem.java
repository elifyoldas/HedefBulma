package HedefBulmaSensoru;

import java.awt.geom.Point2D;
	

public class CoordinateSystem {
	
	static Target target;
	
	public CoordinateSystem() {
		super();
		target = new Target();
//		target.setCoordinate(new Point2D.Double(-1,5));
//		System.out.println(target.getCoordinate());
	}
	
    public static Point2D.Double createRandomPoint(){
    	double max = 500, min = -500;
		
		double x = (Math.random() * ((max - min) + 1)) + min;
		double y = (Math.random() * ((max - min) + 1)) + min;
		Point2D.Double coordinate = new Point2D.Double(x,y);
		return coordinate;
    }
    
    public static Point2D.Double getTarget(){
    	
    	return target.getCoordinate();
    }
}
