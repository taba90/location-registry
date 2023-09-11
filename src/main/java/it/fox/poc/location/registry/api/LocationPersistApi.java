package it.fox.poc.location.registry.api;

import it.fox.poc.location.registry.model.RegisteredLocation;
import it.fox.poc.location.registry.repositories.RegisteredLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationPersistApi {


    @Autowired
    RegisteredLocationRepository repository;
    @PostMapping
    public HttpStatus saveRegisteredLocations(@RequestBody List<RegisteredLocation> locations){
        repository.addLocations(locations);
        return HttpStatus.CREATED;
    }
}
