package seliard.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import seliard.HotelService;
import seliard.exceptions.HotelAlreadyExistsException;
import seliard.exceptions.HotelNotFoundException;
import seliard.exceptions.RestTemplateErrorHandler;
import seliard.models.Hotel;
import seliard.models.HotelLocation;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class HotelServiceTest {

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private RestTemplateErrorHandler restTemplateErrorHandler = new RestTemplateErrorHandler();
    private HotelService hotelService = new HotelService(restTemplateBuilder, restTemplateErrorHandler);

    @Test
    public void verifyInitialHotelList() {
        List<String> hotelIds = hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList());
        assertEquals(10, hotelIds.size());
    }

    @Test(expected = HotelAlreadyExistsException.class)
    public void verifyCannotAddExistingHotel() throws HotelAlreadyExistsException {
        Hotel lonBla = new Hotel("LONBLA", "London Blackfriars (Fleet Street)", new HotelLocation("1", "Blackfriars", 51.513104, -0.105613));
        assertEquals(10, hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList()).size());
        hotelService.addHotel(lonBla);
        assertEquals(10, hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList()).size());
    }

    @Test
    public void verifyCanAddHotel() throws HotelAlreadyExistsException {
        Hotel eleCas = new Hotel("ELECAS", "London Elephant & Castle", new HotelLocation("11", "Elephant & Castle", 51.496040, -0.100740));
        assertEquals(10, hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList()).size());
        hotelService.addHotel(eleCas);
        assertEquals(11, hotelService.getHotels().stream().map(Hotel::getId).collect(Collectors.toList()).size());
        assertTrue(hotelService.getHotels().stream().anyMatch(t -> t.getId().equals(eleCas.getId())));
    }

    @Test(expected = HotelNotFoundException.class)
    public void searchForNonExistentHotel() throws HotelNotFoundException {
        hotelService.getHotel("NONEXISTENTHOTEL");
    }

}