package seliard;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class HotelServiceTest {

    private HotelService hotelService = new HotelService();

    @Test
    public void verifyInitialHotelList() {
        List<String> hotelIds = hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList());
        assertEquals(10,hotelIds.size());
    }

    @Test
    public void verifyCannotAddExistingHotel() {
        Hotel lonBla = new Hotel("LONBLA", "London Blackfriars (Fleet Street)", new HotelLocation("1", "Blackfriars", 51.513104, -0.105613));
        assertEquals(10,hotelService.getHotels().size());
        hotelService.addHotel(lonBla);
        assertEquals(10,hotelService.getHotels().size());
    }

    @Test
    public void verifyCanAddHotel() {
        Hotel lonEle = new Hotel("LONELE", "London Elephant & Castle", new HotelLocation("11", "Elephant & Castle", 51.496040, -0.100740));
        assertEquals(10,hotelService.getHotels().size());
        hotelService.addHotel(lonEle);
        assertEquals(11,hotelService.getHotels().size());
    }

}