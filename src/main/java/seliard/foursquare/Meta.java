package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Santiago on 29/06/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    private int code;

    public Meta() {
    }

    public Meta(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}