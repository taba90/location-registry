package it.fox.poc.location.registry.repositories;

import it.fox.poc.location.registry.bo.RegisteredLocationToSimpleFeature;
import it.fox.poc.location.registry.dataaccess.SimpleFeatureDAO;
import it.fox.poc.location.registry.model.RegisteredLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Repository
public class RegisteredLocationRepositoryImpl implements RegisteredLocationRepository{

    @Autowired
    SimpleFeatureDAO dao;

    @Autowired
    RegisteredLocationToSimpleFeature mapper;
    @Override
    public void addLocations(List<RegisteredLocation> locationList) {
        if (locationList!=null && !locationList.isEmpty()) {
            try {
                dao.save(mapper.convert(locationList));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
