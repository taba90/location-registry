package it.fox.poc.location.registry.dataaccess;

import it.fox.poc.location.registry.configuration.DataAccessConfiguration;
import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.immudb.GeoJSONToFeatureType;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

@Component
public class SimpleFeatureDAOImpl implements SimpleFeatureDAO {

    @Value("${data.access.jsonSchema}")
    String jsonSchema;

    @Value("${data.access.tname}")
    String featureTypeName;

    @Value("${data.access.name}")
    String dataAccessName;

    @Value("${data.access.namespace}")
    String ns;

    @Autowired
    DataAccessRegistry dataAccessRegistry;

    @Autowired
    @Qualifier(DataAccessConfiguration.DATA_ACCESS_CONF_NAME)
    Map<String,Object> configurationProperties;

    @Override
    public void save(SimpleFeatureCollection simpleFeatureCollection) throws IOException {
        DataStore dataStore=dataAccessRegistry.loadDataStore(dataAccessName,getConfigurationProperties());
        SimpleFeatureStore store=(SimpleFeatureStore) dataStore.getFeatureSource(featureTypeName);
        store.addFeatures(simpleFeatureCollection);
    }

    @PostConstruct
    public void createFeatureType() throws IOException, URISyntaxException {
        DataStore dataStore=dataAccessRegistry.loadDataStore(dataAccessName,getConfigurationProperties());
        if (dataStore!=null && !Arrays.stream(dataStore.getTypeNames()).anyMatch(t->t!=null && t.equals(featureTypeName))){
            SimpleFeatureType simpleFeatureType= new GeoJSONToFeatureType(new URI(jsonSchema),ns).readType();
            dataStore.createSchema(simpleFeatureType);
        }

    }

    public Map<String,Object> getConfigurationProperties(){
       return (Map<String,Object>) configurationProperties.get(DataAccessConfiguration.DATA_ACCESS_CONF_NAME);
    }
}
