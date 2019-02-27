package seliard.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import seliard.models.Hotel;
import seliard.models.HotelLocation;

@Component
public class HotelValidator implements Validator {

    private final HotelLocationValidator hotelLocationValidator;

    public HotelValidator(HotelLocationValidator hotelLocationValidator) {
        if (hotelLocationValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!hotelLocationValidator.supports(HotelLocation.class)) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                   "support the validation of [HotelLocation] instances.");
        }
        this.hotelLocationValidator = hotelLocationValidator;
    }

    @Override
    public boolean supports(Class clazz) {
        return Hotel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Hotel hotel = (Hotel) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        if (hotel.getLoc() == null) {
            errors.rejectValue("loc", "loc.required");
        } else {
            try {
                errors.pushNestedPath("loc");
                ValidationUtils.invokeValidator(hotelLocationValidator, hotel.getLoc(), errors);
            } finally {
                errors.popNestedPath();
            }
        }
    }

}
