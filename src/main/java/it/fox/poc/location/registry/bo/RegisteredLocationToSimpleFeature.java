package it.fox.poc.location.registry.bo;

import it.fox.poc.location.registry.model.RegisteredLocation;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.immudb.GeoJSONToFeatureType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegisteredLocationToSimpleFeature {

    public SimpleFeatureCollection convert(List<RegisteredLocation> locationList) throws URISyntaxException, IOException {
        SimpleFeatureType simpleFeatureType= new GeoJSONToFeatureType(new File(getClass().getResource("json-schema.json").getFile()).toURL().toURI(),"gt").readType();
        SimpleFeatureBuilder builder=new SimpleFeatureBuilder(simpleFeatureType);
        GeometryFactory geometryFactory=new GeometryFactory();
        List<SimpleFeature> features=new ArrayList<>();
        locationList.forEach(l->features.add(toSimpleFeature(l,simpleFeatureType,geometryFactory,builder)));
        return new ListFeatureCollection(simpleFeatureType,features);
    }


    private SimpleFeature toSimpleFeature(RegisteredLocation regLoc, SimpleFeatureType simpleFeatureType,GeometryFactory factory, SimpleFeatureBuilder builder){
        for (int i=0; i<simpleFeatureType.getAttributeCount(); i++){
            AttributeDescriptor ad=simpleFeatureType.getDescriptor(i);
            String lName=ad.getLocalName();
            if (lName.equalsIgnoreCase("distance"))
                builder.add(regLoc.getDistance());
            else if (lName.equalsIgnoreCase("workplaceLong"))
                builder.add(regLoc.getWorkPlaceLon());
            else if (lName.equalsIgnoreCase("workplaceLat"))
                builder.add(regLoc.getWorkPlaceLat());
            else if (ad instanceof GeometryDescriptor) {
                Point location=factory.createPoint(new Coordinate(regLoc.getLongitude(),regLoc.getLatitude()));
                builder.add(location);
            }
        }
        return builder.buildFeature(null);
    }
}