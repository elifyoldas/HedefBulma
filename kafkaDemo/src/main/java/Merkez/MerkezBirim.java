package Merkez;

import org.apache.kafka.clients.consumer.ConsumerConfig; 
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import HedefBulmaSensoru.*;

public class MerkezBirim {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "sensorBilgiSistemi";

    public static void main(String[] args) {
    	CoordinateSystem c = new CoordinateSystem();
    	HedefSensoru s1 = new HedefSensoru();
    	HedefSensoru s2 = new HedefSensoru();
    	KafkaConsumer<String, String> consumer = createKafkaConsumer();
//    	s1.setCoordinate(new Point2D.Double(-5,1));
//    	s2.setCoordinate(new Point2D.Double(5,-1));
    	System.out.println("Birinci sensorden gelen bilgiler:");
    	s1.connectWithMerkezBirim();
    	String angle1 = consumeMessages(consumer);
    	System.out.println(angle1);
    	System.out.println("İkinci sensorden gelen bilgiler:");
    	s2.connectWithMerkezBirim();
    	String angle2 = consumeMessages(consumer);
    	Target target = findTarget(angle1, angle2, s1, s2);
        
    	CoordinateSystemAWT cDisplay = new CoordinateSystemAWT();
    	cDisplay.displayCoordinates(s1, s2, target);
		
        consumer.close();
        
        
    }

    private static Target findTarget(String angle1, String angle2, HedefSensoru s1, HedefSensoru s2) {
    	Target target = new Target();
    	Point2D.Double t1 =new Point2D.Double(0,0);
    	Point2D.Double t2 = new Point2D.Double(0,0);
//    	angle1="45.0";
//    	angle2="315.0";
    	t1 = hedefBul(Double.valueOf(angle1), s1);
    	t2 = hedefBul(Double.valueOf(angle2), s2);
    	if(t1.getX() == t2.getX() && t1.getY()==t2.getY()) {
    		target.setCoordinate(t2);
    	}
    	
    	return target;
    }

    public static Point2D.Double hedefBul(double angle ,HedefSensoru s) {
        
        double radianAngle = Math.toRadians(angle);
        Point2D.Double t = new Point2D.Double(0,0);
        t.x = s.getCoordinate().x + (s.getDistanceToTarget() * Math.cos(radianAngle));
        t.y = s.getCoordinate().y + (s.getDistanceToTarget() * Math.sin(radianAngle));
        
        return t;
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my_consumer_group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        return consumer;
    }


    private static String consumeMessages(KafkaConsumer<String, String> consumer) {
    	String[] spStg;
    	String angle = null;  
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received message: " + record.value());
                if(record.value().toString().contains("kerteriz")) {
                	 spStg= record.value().toString().split(" ");
                	 angle = spStg[spStg.length-1];
                }
            }
       return angle;
    }
 
}
