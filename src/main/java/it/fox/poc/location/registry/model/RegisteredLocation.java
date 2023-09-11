package it.fox.poc.location.registry.model;

import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;

import java.util.ArrayList;
import java.util.List;

public class RegisteredLocation {

    private Long id;

    private Double latitude;

    private Double longitude;

    private Double workPlaceLat;

    private Double workPlaceLon;

    private Double distance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getWorkPlaceLat() {
        return workPlaceLat;
    }

    public void setWorkPlaceLat(Double workPlaceLat) {
        this.workPlaceLat = workPlaceLat;
    }

    public Double getWorkPlaceLon() {
        return workPlaceLon;
    }

    public void setWorkPlaceLon(Double workPlaceLon) {
        this.workPlaceLon = workPlaceLon;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public SimpleFeatureCollection toSimpleFeatures(SimpleFeatureType simpleFeatureType){
        SimpleFeatureBuilder builder=new SimpleFeatureBuilder(simpleFeatureType);
        GeometryFactory geometryFactory=new GeometryFactory();
        Point location=geometryFactory.createPoint(new Coordinate(getLatitude(), getLongitude()));
        List<SimpleFeature> featureList=new ArrayList<>();
        for (int i=0; i<simpleFeatureType.getAttributeCount(); i++){
            AttributeDescriptor ad=simpleFeatureType.getDescriptor(i);
            String lName=ad.getLocalName();
            if (lName.equalsIgnoreCase("distance"))
                builder.add(getDistance());
            else if (lName.equalsIgnoreCase("workplaceLong"))
                builder.add(getWorkPlaceLon());
            else if (lName.equalsIgnoreCase("workplaceLat"))
                builder.add(getWorkPlaceLat());
            else if (ad instanceof GeometryDescriptor)
                builder.add(location);
            featureList.add(builder.buildFeature(null));
        }
        return new ListFeatureCollection(simpleFeatureType,featureList);
    }
}
