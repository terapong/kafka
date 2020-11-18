package toto.kafka.springboot.test;

import java.util.Calendar;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import com.sun.jdi.LongValue;

import toto.kafka.springboot.entity.User;

public class Producer {

    public static void main(String[] args){
    	Calendar cal = Calendar.getInstance();
    	
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "kafka:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "toto.kafka.springboot.serializer.UserSerializer");
        properties.put("group.id", "groupId");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        
        try{
        	
            for(int i = 0; i < 10; i++){
                System.out.println(i);
                User user = new User();
                user.setId(i);
                user.setFirstName("Terapong_" + i);
                user.setLastName("Potisuwan");
                user.setUpdateDate(cal.getTime());
                user.setCreateDate(cal.getTime());
                
                kafkaProducer.send(new ProducerRecord<String, User>("demo", user));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaProducer.close();
        }
    }
}