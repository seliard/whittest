package seliard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import seliard.exceptions.HotelAlreadyExistsException;
import seliard.exceptions.HotelNotFoundException;
import seliard.exceptions.foursquare.FoursquareException;
import seliard.models.Hotel;
import seliard.models.foursquare.Venue;
import seliard.HotelService;
import seliard.validators.HotelLocationValidator;
import seliard.validators.HotelValidator;

import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@RestController
public class HotelController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HotelService hotelService;
    private HotelValidator hotelValidator;
    private HotelLocationValidator hotelLocationValidator;

    @Autowired
    public HotelController(HotelService hotelService, HotelValidator hotelValidator, HotelLocationValidator hotelLocationValidator) {
        this.hotelService = hotelService;
        this.hotelLocationValidator = hotelLocationValidator;
        this.hotelValidator = hotelValidator;
    }

    @RequestMapping("/hotels")
    public List<Hotel> getHotels() {
        return hotelService.getHotels();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotels")
    public ResponseEntity addHotel(@RequestBody Hotel hotel, Errors errors) {
        hotelValidator.validate(hotel, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity(errors.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return hotelService.addHotel(hotel);
            } catch (HotelAlreadyExistsException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel already exists.");
            }
        }
    }

    @RequestMapping("/hotels/{id}")
    public Hotel getHotel(@PathVariable String id) {
        try {
            return hotelService.getHotel(id);
        } catch (HotelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel " + id + " not found", e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/hotels/{id}")
    public ResponseEntity updateHotel(@RequestBody Hotel hotel, @PathVariable String id) {
        return hotelService.updateHotel(id, hotel);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/hotels/{id}")
    public ResponseEntity deleteHotel(@PathVariable String id) {
        return hotelService.deleteHotel(id);
    }

    @RequestMapping("/hotels/{id}/{query}/{radius}")
    public List<Venue> getLocationsFrom4SQ(@PathVariable String id, @PathVariable String query, @PathVariable String radius) {
        try {
            return hotelService.getLocationsFrom4SQ(id, query, radius);
        } catch (HotelNotFoundException h) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel " + id + " not found", h);
        } catch (FoursquareException f) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, f);
        }
    }

    //
//    private String printErrors(Errors errors) {
//
//    }
}
