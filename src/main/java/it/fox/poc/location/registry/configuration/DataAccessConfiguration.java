package it.fox.poc.location.registry.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataAccessConfiguration {

    @Value("data.access.name")
    String dataAccessName;

    @Value("data.access.database")
    String database;

    @Value("${data.access.host}")
    String host;
    @Value("${data.access.user}")
    String user;
    @Value("${data.access.passwd}")
    String passwd;
    @Value("${data.access.namespace}")
    String ns;
    @Value("${data.access.port}")
    String port;
    @Value("${data.access.jsonSchema}")
    String jsonSchema;
    @Value("${data.access.stateHolderPath}")
    String stateHolder;

    public static final String DATA_ACCESS_CONF_NAME="configurationProperties";

    @Bean
    public Map<String,Object> configurationProperties(){
        Map<String,Object> configuration=new HashMap<>();
        configuration.put("name",dataAccessName);
        configuration.put("database",database);
        configuration.put("port",port);
        configuration.put("host",host);
        configuration.put("namespace",ns);
        configuration.put("user",user);
        configuration.put("passwd",passwd);
        configuration.put("jsonSchema",jsonSchema);
        configuration.put("stateHolderPath",stateHolder);
        return configuration;
    }
}
