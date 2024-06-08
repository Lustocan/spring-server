package it.example.lavoretti.controller;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import it.example.lavoretti.BaseTestContainer;
import it.example.lavoretti.LavorettiApplication;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LavorettiApplication.class)
class AuthControllerTestIT extends BaseTestContainer {

    public static final String BASE_AUTH_ENDPOINT = "/auth";

    @Test
    @DisplayName("test set up")
    void given_login_without_body_then_throw_exception() {
        RestAssured.port = this.port;
        // given
        given()
            .when()
            .contentType(ContentType.JSON)
            .post(BASE_AUTH_ENDPOINT + "/login")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}