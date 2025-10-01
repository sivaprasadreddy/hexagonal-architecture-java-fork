package eu.happycoders.shop.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public final class HttpTestCommons {

  private HttpTestCommons() {}

  public static void assertThatResponseIsError(
      Response response, HttpStatus expectedStatus, String expectedErrorMessage) {
    assertThat(response.getStatusCode()).isEqualTo(expectedStatus.value());

    JsonPath json = response.jsonPath();

    assertThat(json.getInt("httpStatus")).isEqualTo(expectedStatus.value());
    assertThat(json.getString("errorMessage")).isEqualTo(expectedErrorMessage);
  }
}
