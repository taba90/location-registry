package it.fox.poc.location.registry.dataaccess;

import org.geotools.data.DataStore;

import java.util.Map;

public interface DataAccessRegistry {

    /**
     * Loads a {@link DataStore}.
     *
     * @param name the name identifying the datastore.
     * @param properties properties to configure the data store.
     * @return a {@link DataStore} instance
     */
    DataStore loadDataStore(String name, Map<String,Object> properties);
}
