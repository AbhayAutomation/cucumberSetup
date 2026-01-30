package pageObject;

import pojo.AddPlace;
import pojo.Location;

import java.util.Arrays;

public class PayloadAddPlace {

    public static AddPlace getAddPlacePayload() {

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        AddPlace addPlace = new AddPlace();
        addPlace.setLocation(location);
        addPlace.setAccuracy(50);
        addPlace.setName("Swapnil");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setTypes(Arrays.asList("shoe park", "shop"));
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");

        return addPlace;
    }
}
