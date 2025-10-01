package eu.happycoders.shop.rest.common;

import org.springframework.http.ResponseEntity;

/**
 * An exception to be thrown in case of a client error (e.g., invalid input).
 *
 * @author Sven Woltmann
 */
public class ClientErrorException extends RuntimeException {

  private final ResponseEntity<ErrorEntity> response;

  public ClientErrorException(ResponseEntity<ErrorEntity> response) {
    super(getMessage(response));
    this.response = response;
  }

  public ResponseEntity<ErrorEntity> getResponse() {
    return response;
  }

  private static String getMessage(ResponseEntity<ErrorEntity> response) {
    ErrorEntity body = response.getBody();
    return body != null ? body.errorMessage() : null;
  }
}
