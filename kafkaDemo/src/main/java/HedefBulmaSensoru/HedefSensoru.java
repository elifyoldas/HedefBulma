package HedefBulmaSensoru;

import java.awt.geom.Point2D;  
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class HedefSensoru {
	
		private Point2D.Double coordinate;
	    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	    private static final String TOPIC = "sensorBilgiSistemi";
		private double distanceToTarget;
		public HedefSensoru() {
			super();
			
			this.coordinate = CoordinateSystem.createRandomPoint();// new Point2D.Double(5.0, 9.0);
		}


		public Point2D.Double getCoordinate() {
			return coordinate;
		}

		public void setCoordinate(Point2D.Double coordinate) {

			this.coordinate = coordinate;
			
		}
	  
		
		public void connectWithMerkezBirim() {
			
	        KafkaProducer<String, String> producer = createKafkaProducer();
	        
			produceMessages(producer);

	        producer.close();
		}
	    


		private void produceMessages(KafkaProducer<String, String> producer) {
			Point2D.Double targetCoordinate = CoordinateSystem.getTarget();
			
			double angle = findAngle(coordinate, targetCoordinate);
	        String message = "şuanki konum("+ this.coordinate.x+" ,"+this.coordinate.y+")"+"Hedefle olan kerteriz bilgisi "+angle;
	        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
	        producer.send(record);
	     
	        producer.flush();
	    }

	    public static KafkaProducer<String, String> createKafkaProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	      
	        return new KafkaProducer<>(props);
	    }

	    
		public double findAngle(Point2D.Double point1, Point2D.Double point2){

		      double angleRadians = Math.atan2(point2.x - point1.x, point2.y - point1.y);
		      double angleDegrees = Math.toDegrees(angleRadians);
		      setDistanceToTarget(point1.distance(point2));
		      if(angleDegrees<0) {
		      	angleDegrees+=360;
		      }
		      return angleDegrees;
			}


		public double getDistanceToTarget() {
			return distanceToTarget;
		}


		public void setDistanceToTarget(double distanceToTarget) {
			this.distanceToTarget = distanceToTarget;
		}
}
