package seliard.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import seliard.models.HotelLocation;

@Component
public class HotelLocationValidator implements Validator {

    private static final double LON_MIN = -180;
    private static final double LON_MAX = 180;

    private static final double LAT_MIN = -90;
    private static final double LAT_MAX = 90;

    @Override
    public boolean supports(Class clazz) {
        return HotelLocation.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        HotelLocation location = (HotelLocation) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        if (location.getLat() == 0) {
            errors.rejectValue("lat", "lat.required");
        } else if (location.getLat() < LAT_MIN || location.getLat() > LAT_MAX) {
            errors.rejectValue("lat", "lat has to be between " + LAT_MIN + " and " + LAT_MAX);
        }
        if (location.getLon() == 0) {
            errors.rejectValue("lon", "lon.required");
        } else if (location.getLon() < LON_MIN || location.getLon() > LON_MAX) {
            errors.rejectValue("lon", "lon has to be between " + LON_MIN + " and " + LON_MAX);
        }
    }

}
