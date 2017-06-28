package seliard;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@Service
public class HotelService {

    private List<Hotel> hotelList = new ArrayList<>(Arrays.asList(
            new Hotel("test","testName", new HotelLocation("1", "London", 51.5074, 0.1278))
    ));

    public List<Hotel> getHotels() {
        return hotelList;
    }
}
