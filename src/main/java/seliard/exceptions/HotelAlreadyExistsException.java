package seliard.exceptions;

public class HotelAlreadyExistsException extends Exception {

    public HotelAlreadyExistsException(String hotelId) {
        super(hotelId + " already exists.");
    }
}
