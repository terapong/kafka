package toto.kafka.springboot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;

import toto.kafka.springboot.entity.Topic;

//import datacool.hadoop.common.IPUtil;

public class KafkaUtil {

	private static Properties props;// Configuration information
	private static AdminClient adminClient;// kafka admin

	/*
	 * Load configuration items
	 */
	static {
		props = new Properties();
		props.put("bootstrap.servers", "kafka:9092");// Run the host of the kafka client
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");// Serialization mode
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		System.out.println("Configuration file is ready for success");
	}

	/*
	 * Create a topic
	 */
	public static void createTopic(String topic) {
		adminClient = AdminClient.create(props);
		NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
		Collection<NewTopic> newTopicList = new ArrayList<>();
		newTopicList.add(newTopic);
		adminClient.createTopics(newTopicList);
		adminClient.close();
		System.out.println("Create theme success: " + topic);
	}

	/*
	 * Delete topic
	 */
	public static void deleteTopic(String topic) {
		adminClient = AdminClient.create(props);
		Collection<String> TopicList = new ArrayList<>();
		TopicList.add(topic);
		adminClient.deleteTopics(TopicList);
		adminClient.close();
		System.out.println("Delete topic success: " + topic);
	}

	/*
	 * List all topics
	 */
	public static void listTopics() {
		adminClient = AdminClient.create(props);
		ListTopicsResult topicList = adminClient.listTopics();
		List<Topic> topics = new ArrayList<>();
		try {
			Collection<TopicListing> collection = topicList.listings().get();
			Iterator<TopicListing> it = collection.iterator();
			while (it.hasNext()) {
				String name = it.next().name();
				System.out.println(name);
				Topic tp = describeTopics(name);
				
				System.out.println(tp);
				
				topics.add(tp);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adminClient.close();
		System.out.println("Successfully lists all topics");
	}

	public static Topic describeTopics(String topic) {
    	adminClient= AdminClient.create(props);
		Collection<String> topicList = new ArrayList<>();
		topicList.add(topic);
    	DescribeTopicsResult descTopic = adminClient.describeTopics(topicList);
    	
    	Topic tp= new Topic();
    	
    	try {
			Map<String, TopicDescription> map = descTopic.all().get();
			TopicDescription topicDescription = map.get(topic);
			tp.setName(topic);
			System.out.println("hope's description: "+topicDescription);
			topicDescription.name();
			topicDescription.isInternal();
			List<TopicPartitionInfo> partitions = topicDescription.partitions();
			Iterator<TopicPartitionInfo> iterator = partitions.iterator();
			while(iterator.hasNext()) {
				TopicPartitionInfo next = iterator.next();
				next.partition();
				Node leader = next.leader();
				leader.host();
				leader.port();
				leader.id();
				tp.setPartition(leader.id());
				leader.rack();
				List<Node> replicas = next.replicas();
				for(Node it:replicas) {
					it.host();
					it.port();
					it.id();
					tp.setReplicas(it.id());
					it.rack();
				}
				List<Node> isr = next.isr();
				for(Node it:isr) {
					it.host();
					it.port();
					it.id();
					it.rack();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	adminClient.close();
    	System.out.println("Success Description Theme: "+topic);
    	return tp; 
    }

	public static void describeCluster() {
		adminClient = AdminClient.create(props);
		DescribeClusterResult describeCluster = adminClient.describeCluster();

		describeCluster.clusterId();
		describeCluster.controller();

		adminClient.close();
		System.out.println("Successfully describes the cluster");
	}

	public static void main(String[] args) {
    	     //Test
		listTopics();
//		describeTopics("demo");
//		describeTopics("demo1");
	}
}