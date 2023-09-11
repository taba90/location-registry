package it.fox.poc.location.registry.dataaccess;

import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleFeatureDAOImpl implements SimpleFeatureDAO {


    @Autowired
    DataAccessRegistry dataAccessRegistry;
    @Override
    public void save(SimpleFeatureCollection simpleFeatureCollection) throws IOException {
        DataStore dataStore=dataAccessRegistry.loadDataStore("immudb-location-registry",getClass().getResource("datastore.properties").getFile());
        SimpleFeatureStore store=(SimpleFeatureStore) dataStore.getFeatureSource("registered_location");
        store.addFeatures(simpleFeatureCollection);
    }
}
