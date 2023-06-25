package HedefBulmaSensoru;

import java.awt.Canvas; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

public class CoordinateSystemAWT extends Canvas {


    private HedefSensoru s1;
    private HedefSensoru s2;
    private Target target;
    private double angle1 = 0;
    
    public double getAngle1() {
		return angle1;
	}

	public void setAngle1(double angle1) {
		this.angle1 = angle1;
	}

	public double getAngle2() {
		return angle2;
	}

	public void setAngle2(double angle2) {
		this.angle2 = angle2;
	}

	private double angle2 = 0;

    public CoordinateSystemAWT() {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int width = 1000;
        int height = 1000;
        int originX = width / 2;
        int originY = height / 2;
        // X eksenini çizdirme
        g.setColor(Color.BLACK);
        g.drawLine(0, originY, width, originY);

        // Y eksenini çizdirme
        g.drawLine(originX, 0, originX, height);

        // X ekseninde ölçekleme ve sayıları yazdırma
        for (int x = -originX; x <= width - originX; x += 50) {
            g.drawLine(originX + x, originY - 5, originX + x, originY + 5); // Ölçek işaretleri
            g.drawString(Integer.toString(x), originX + x - 10, originY + 20); // Sayılar
        }

        // Y ekseninde ölçekleme ve sayıları yazdırma
        for (int y = -originY; y <= height - originY; y += 50) {
            g.drawLine(originX - 5, originY + y, originX + 5, originY + y); // Ölçek işaretleri
            g.drawString(Integer.toString(-y), originX - 30, originY + y + 5); // Sayılar
        }

        
        int x = (int) s1.getCoordinate().x;
        int y = (int) s1.getCoordinate().y;
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 5, 5);
        g.drawString("Hedefle Olan Açı: "+angle1, x, y);
        
        x = (int) s2.getCoordinate().x;
        y = (int) s2.getCoordinate().y; 
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 5, 5);
        g.drawString("Hedefle Olan Açı: "+angle2, x, y);
        
        x = (int) target.getCoordinate().x;
        y = (int) target.getCoordinate().y;
    	g.setColor(Color.RED);
    	g.fillOval(x, y, 15, 15);
        
        
//        g.setColor(Color.YELLOW);
//        g.drawLine((int)s2.getCoordinate().x, (int)s2.getCoordinate().y, x, y);
    }

    public void displayCoordinates(HedefSensoru s1 ,HedefSensoru s2, Target target) {
        

    	this.s1 = s1;
    	this.s2 = s2;
    	this.target = target;
    	
 
//    	Point2D.Double coordinateS1,coordinateS2,coordinateT1;
//    	coordinateS1 = new Point2D.Double(-5,1);
//    	coordinateS2 = new Point2D.Double(5,-1);
//    	coordinateT1 = new Point2D.Double(-1,5);
        Frame frame = new Frame("Coordinate System AWT Example");
        frame.setSize(getScreenSize().width, getScreenSize().height);
        frame.setResizable(true);
       
    	 angle1 = findAngle(s1.getCoordinate(), target.getCoordinate());
    	 angle2 = findAngle(s2.getCoordinate(), target.getCoordinate());
        
//    	double angle1 = canvas.findAngle(coordinateS1, coordinateT1);
//    	double angle2 = canvas.findAngle(coordinateS2, coordinateT1);
    	System.out.println(angle1+" "+angle2);
    	this.setAngle1(angle1);
    	this.setAngle2(angle2);
        frame.add(this);
     
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
    

    public static Dimension getScreenSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }
    
	public double findAngle(Point2D.Double point1, Point2D.Double point2){

      double angleRadians = Math.atan2(point2.x - point1.x, point2.y - point1.y);
      double angleDegrees = Math.toDegrees(angleRadians);
      
      if(angleDegrees<0) {
      	angleDegrees+=360;
      }
      return angleDegrees;
	}
}
