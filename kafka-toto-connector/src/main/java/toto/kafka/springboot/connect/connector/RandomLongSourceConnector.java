package toto.kafka.springboot.connect.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import toto.kafka.springboot.connect.config.RandomLongSourceConnectorConfig;
import toto.kafka.springboot.connect.util.Version;

import static toto.kafka.springboot.connect.config.RandomLongSourceConnectorConfig.SLEEP_CONFIG;
import static toto.kafka.springboot.connect.config.RandomLongSourceConnectorConfig.TOPIC_CONFIG;
import static toto.kafka.springboot.connect.config.RandomLongSourceConnectorConfig.API_URL_CONFIG;

public class RandomLongSourceConnector extends SourceConnector {

	private RandomLongSourceConnectorConfig randomLongSourceConnectorConfig;

	@Override
	public void start(Map<String, String> props) {
		randomLongSourceConnectorConfig = new RandomLongSourceConnectorConfig(props);
	}

	@Override
	public void stop() {
	}

	@Override
	public Class<? extends Task> taskClass() {
		return RandomLongSourceTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> configs = new ArrayList<>(maxTasks);
		for (int i = 0; i < maxTasks; i++) {
			Map<String, String> config = new HashMap<>(3);
			config.put(API_URL_CONFIG, randomLongSourceConnectorConfig.getString(API_URL_CONFIG));
			config.put(SLEEP_CONFIG, Integer.toString(randomLongSourceConnectorConfig.getInt(SLEEP_CONFIG)));
			config.put(TOPIC_CONFIG, randomLongSourceConnectorConfig.getString(TOPIC_CONFIG));
			configs.add(config);
		}
		return configs;
	}

	@Override
	public ConfigDef config() {
		return RandomLongSourceConnectorConfig.config();
	}

	@Override
	public String version() {
		return Version.getVersion();
	}
}
