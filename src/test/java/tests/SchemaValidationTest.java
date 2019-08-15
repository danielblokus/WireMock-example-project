package tests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest {

    private static final String URL_PATH = "https://httpbin.org/json";
    private static final String SCHEMA_PATH = "schema/response.json";

    @Test
    public void validateSchemaForGivenJson() {
        given()
        .when()
            .get(URL_PATH)
        .then()
            .assertThat()
                .body(matchesJsonSchemaInClasspath(SCHEMA_PATH));
    }
}