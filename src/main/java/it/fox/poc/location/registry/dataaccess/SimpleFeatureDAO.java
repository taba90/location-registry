package it.fox.poc.location.registry.dataaccess;

import org.geotools.data.simple.SimpleFeatureCollection;

import java.io.IOException;

public interface SimpleFeatureDAO {

    void save(SimpleFeatureCollection simpleFeatureCollection) throws IOException;
}
