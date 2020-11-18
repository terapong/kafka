package toto.kafka.springboot.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import toto.kafka.springboot.controller.TopicController;
import toto.kafka.springboot.entity.*;

@Named(value = "producerBean")
@SessionScoped
public class ProducerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Topic> topics;
	private List<Topic> selectedTopics;
	private User newUser;
	
	private Properties props;// Configuration information
	private KafkaProducer kafkaProducer;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;
	
	@Value("${key.serializer}")
	private String keySerializer;
	
	@Value("${value.serializer}")
	private String valueSerializer;
	
	@Autowired
	private TopicController control;
	
	@PostConstruct
	public void init() {
		topics = control.getAllTopics();
		selectedTopics = new ArrayList<>();
		newUser = new User();
		
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	private KafkaProducer getKafkaProducer() {
		props = new Properties();
		props.put("bootstrap.servers", bootStrapServer);// Run the host of the kafka client
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", keySerializer);// Serialization mode
		props.put("value.serializer", valueSerializer);

		return new KafkaProducer(props);
	}
	
	public void btnSendClick() {
		kafkaProducer = getKafkaProducer();
		for(Topic t : selectedTopics) {
			System.out.println("Selected topic name:" + t.getName());
			kafkaProducer.send(new ProducerRecord<String, User>(t.getName(), newUser));
		}
		selectedTopics = new ArrayList<>();
		newUser = new User();
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public List<Topic> getSelectedTopics() {
		return selectedTopics;
	}

	public void setSelectedTopics(List<Topic> selectedTopics) {
		this.selectedTopics = selectedTopics;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
}
