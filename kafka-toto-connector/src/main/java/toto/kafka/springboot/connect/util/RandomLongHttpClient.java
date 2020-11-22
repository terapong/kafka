package toto.kafka.springboot.connect.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RandomLongHttpClient {

    public static CloseableHttpClient httpClient = HttpClients.createDefault();
}
