package toto.kafka.springboot.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import toto.kafka.springboot.entity.User;

public class UserDeserializer implements Deserializer<User> {
	private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public User deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data, "UTF-8"), User.class);
        } catch (Exception e) {
            //log.error("Unable to deserialize message {}", data, e);
        	e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
    }
}
