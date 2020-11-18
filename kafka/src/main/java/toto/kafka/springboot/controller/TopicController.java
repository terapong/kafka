package toto.kafka.springboot.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import toto.kafka.springboot.entity.Topic;
import toto.kafka.springboot.exception.ResourceNotFoundException;
import toto.kafka.springboot.repository.TopicRepository;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

	@Autowired
	private TopicRepository repo;

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;
	
	// get all Topics
	@GetMapping
	public List<Topic> getAllTopics() {
		AdminClient adminClient = getAdminClient();
		ListTopicsResult topicList = adminClient.listTopics();
		List<Topic> topics = new ArrayList<>();
		try {
			int i = 0;
			Collection<TopicListing> collection = topicList.listings().get();
			Iterator<TopicListing> it = collection.iterator();
			while (it.hasNext()) {
				String name = it.next().name();
				System.out.println(name);
				Topic tp = describeTopics(name);
				tp.setId(i);
				System.out.println(tp);
				
				topics.add(tp);
				i++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adminClient.close();
		
		return topics;
	}
	
	private Topic describeTopics(String topic) {
		AdminClient adminClient = getAdminClient();
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

	// get Topic by id
	@GetMapping("/{id}")
	public Topic getTopicById(@PathVariable (value = "id") long id) {
		return this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id :" + id));
	}
	
	// get Topic by id
		@GetMapping("/{name}")
		public Topic getTopicByName(@PathVariable (value = "name") long name) {
			return null;//this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id :" + id));
		}

	// create Topic
	@PostMapping
	public Topic createTopic(@RequestBody Topic newObj) {
		List<NewTopic> newTopics = new ArrayList<NewTopic>();
		
		AdminClient adminClient = getAdminClient();
		
		NewTopic newTopic = new NewTopic(newObj.getName(), newObj.getPartition(), (short ) newObj.getReplicas().intValue()); //new NewTopic(topicName, numPartitions, replicationFactor)
		newTopics.add(newTopic);
		
		adminClient.createTopics(newTopics);
		adminClient.close();
		return null;//this.repo.save(newObj);
	}
	
	// update Topic
	@PutMapping("/{id}")
	public Topic updateTopic(@RequestBody Topic newObj, @PathVariable ("id") long id) {
		 Topic existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id :" + id));
		 existing.setName(newObj.getName());
		 existing.setPartition(newObj.getPartition());
		 existing.setReplicas(newObj.getReplicas());
		 return this.repo.save(existing);
	}
	
	// delete Topic by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Topic> deleteTopic(@PathVariable ("id") long id){
		 Topic existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id :" + id));
		 this.repo.delete(existing);
		 
		AdminClient adminClient = getAdminClient();
		 
		List<String> topics = new ArrayList<>();
		topics.add(existing.getName());
		adminClient.deleteTopics(topics);
		adminClient.close();
		 
		 return ResponseEntity.ok().build();
	}
	
	private AdminClient getAdminClient() {
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        
      //เผื่อ Cluster
//      configs.put("security.protocol", "SASL_PLAINTEXT");
//      configs.put("sasl.mechanism", "PLAIN");
//      configs.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required " + 
//                                      "username=username " + 
//                                      "password=password;");
        
//		AdminClient ka = KafkaAdminClient.create(properties);
//		ListTopicsResult listTopicsResult = ka.listTopics();
//		ka.close();
		
		return AdminClient.create(properties);
	}
}
