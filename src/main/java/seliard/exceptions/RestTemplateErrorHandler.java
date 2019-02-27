package seliard.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (!httpResponse.getStatusCode().is4xxClientError()) {
            throw new HttpServerErrorException(httpResponse.getStatusCode());
        } else {
            logger.debug("Found an error 400: " + httpResponse.getBody().toString());
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

}
