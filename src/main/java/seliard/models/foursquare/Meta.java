package seliard.models.foursquare;

/**
 * Created by Santiago on 29/06/2017.
 */

public class Meta {

    private int code;
    private String errorType;
    private String errorDetail;

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

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
