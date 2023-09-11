package it.fox.poc.location.registry.dataaccess;

import it.fox.poc.location.registry.configuration.DataAccessConfiguration;
import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SimpleFeatureDAOImpl implements SimpleFeatureDAO {


    @Autowired
    DataAccessRegistry dataAccessRegistry;

    @Autowired
    @Qualifier(DataAccessConfiguration.DATA_ACCESS_CONF_NAME)
    Map<String,Object> configurationProperties;

    @Override
    public void save(SimpleFeatureCollection simpleFeatureCollection) throws IOException {
        DataStore dataStore=dataAccessRegistry.loadDataStore("immudb-location-registry",configurationProperties);
        SimpleFeatureStore store=(SimpleFeatureStore) dataStore.getFeatureSource("registered_location");
        store.addFeatures(simpleFeatureCollection);
    }
}
