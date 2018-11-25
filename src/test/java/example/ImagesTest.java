package example;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import stubs.ba.ImagesStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ImagesTest extends BasicTest {

    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    private final ImagesStub imagesStub = new ImagesStub();

    @Before
    public void setPreemptiveBasicAuthentication() {
        RestAssured.authentication =
                preemptive()
                        .basic(VALID_USER_NAME, VALID_PASSWORD);

        imagesStub.stubResponse();
    }

    @Test
    public void checkBodyResponseNotEmpty() {
        given()
        .when()
            .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
            .assertThat()
                .body("results", hasSize(greaterThan(0)));
    }

    @Test
    public void checkStatusCode() {
        given()
        .when()
            .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
            .assertThat()
                .statusCode(HttpStatusCodes.SUCCESS);
    }
}