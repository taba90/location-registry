package it.fox.poc.location.registry.dataaccess;

import it.fox.poc.location.registry.bo.PropertiesWatcher;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.util.SoftValueHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;


@Component
public class DefaultDataAccessRegistry implements DataAccessRegistry{

    private SoftValueHashMap<String, DataStore> storeCache;

    private SoftValueHashMap<String, PropertiesWatcher> propertiesCache;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataAccessRegistry.class);

    private StampedLock lock = new StampedLock();

    public DefaultDataAccessRegistry() {
        this.storeCache = new SoftValueHashMap<>(0);
        this.propertiesCache = new SoftValueHashMap<>(0);
    }

    @Override
    public DataStore loadDataStore(String name, Map<String,Object> properties) {
        long stamp = lock.readLock();
        try {
            PropertiesWatcher propertiesWatcher = propertiesCache.get(name);
            DataStore dataStore = storeCache.get(name);
            if (dataStore == null || propertiesWatcher.isModified()) {
                stamp = toWriteLock(stamp);
                if (dataStore == null || propertiesWatcher.isModified()) {
                    try {
                        dataStore = DataStoreFinder.getDataStore(properties);
                        storeCache.put(name, dataStore);
                    } catch (IOException e) {
                        String message =
                                String.format(
                                        "Error while retrieving the datastore for name %s", name);
                        LOGGER.error(message, e);
                        throw new RuntimeException(message, e);
                    }
                }
            }
            return dataStore;
        } finally {
            lock.unlock(stamp);
        }
    }

    private long toWriteLock(long stamp) {
        stamp = lock.tryConvertToWriteLock(stamp);
        if (stamp == 0L) {
            LOGGER.debug("stamp is zero for tryConvertToWriteLock(), so acquiring the write lock");
            stamp = lock.writeLock();
        }
        return stamp;
    }

}
