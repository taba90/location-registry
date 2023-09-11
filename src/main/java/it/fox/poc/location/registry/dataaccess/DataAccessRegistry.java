package it.fox.poc.location.registry.dataaccess;

import org.geotools.data.DataStore;

public interface DataAccessRegistry {

    /**
     * Loads a {@link DataStore}.
     *
     * @param name the name identifying the datastore.
     * @param propertiesURI the URI to the properties file containing connections parameters to the
     *     store.
     * @return a {@link DataStore} instance
     */
    DataStore loadDataStore(String name, String propertiesURI);
}
