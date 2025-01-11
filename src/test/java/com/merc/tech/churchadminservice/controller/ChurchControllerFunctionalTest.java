package com.merc.tech.churchadminservice.controller;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ChurchControllerFunctionalTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setUpClass() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private String createChurch(String churchJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(churchJson)
                .when()
                .post("/church")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .header("Location");
    }

    @Test
    public void testGetAllchurch() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/church")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetChurchById() {
        String newChurch = "{ \"name\": \"New Church\", \"phone\": \"817-456-7878\" }";
        String location = createChurch(newChurch);

        String[] segments = location.split("/");
        int churchId = Integer.parseInt(segments[segments.length - 1]);

        given()
            .contentType(ContentType.JSON)
        .when()
            .get(location)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("churchId", equalTo(churchId));
    }

    @Test
    public void testCreateChurch() {
        String newChurch = "{ \"name\": \"New Church\", \"phone\": \"817-456-7878\" }";
        String location = createChurch(newChurch);

        assertNotNull(location);
        assertTrue(location.matches(".*/church/\\d+"));
    }

    @Test
    public void testUpdateChurch() {
        String updatedChurch = "{ \"name\": \"Updated Church\", \"phone\": \"817-456-7878\" }";
        String newChurch = "{ \"name\": \"New Church\", \"phone\": \"817-456-7878\" }";
        String location = createChurch(newChurch);

        given()
            .contentType(ContentType.JSON)
            .body(updatedChurch)
        .when()
            .put(location)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testDeleteChurch() {
        String newChurch = "{ \"name\": \"New Church\", \"phone\": \"817-456-7878\" }";
        String location = createChurch(newChurch);

        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(location)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}