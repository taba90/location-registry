package it.fox.poc.location.registry.repositories;

import it.fox.poc.location.registry.model.RegisteredLocation;

import java.util.List;

public interface RegisteredLocationRepository {

    void addLocations(String iv, String privateKey,List<RegisteredLocation> locationList);
}
