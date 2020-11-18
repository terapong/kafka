package toto.kafka.springboot.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;
	
//	private Topic topic;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        //เผื่อ Cluster
//        configs.put("security.protocol", "SASL_PLAINTEXT");
//        configs.put("sasl.mechanism", "PLAIN");
//        configs.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required " + 
//                                        "username=username " + 
//                                        "password=password;");
        return new KafkaAdmin(configs);
    }
    
//    @Bean
//	public NewTopic newTopic() {
//		return TopicBuilder.name(topic.getName())
//			      .partitions(topic.getPartition())
//			      .replicas(topic.getReplicas())
//			      .build();
//	}
	
}
