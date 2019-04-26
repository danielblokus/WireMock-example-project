package tests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import stubs.ba.ImagesStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImagesTest extends BasicTest {

    private static final String HTTPS_REGEX = "^(https)://.*$";
    private static final String ORIGINAL_URL_JSON_PATH = "$..originalUrl";

    private final ImagesStub imagesStub = new ImagesStub();

    @Before
    public void setUpEnvironment() {
        RestAssured.port = wireMockRule.port();
        imagesStub.stubResponse();
    }

    @Test
    public void checkBodyResponseNotEmpty() {
        given()
        .when()
            .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
        .then()
            .assertThat()
                .body("results", hasSize(greaterThan(0)));
    }

    @Test
    public void checkStatusCode() {
        given()
        .when()
            .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
        .then()
            .assertThat()
                .statusCode(HttpStatusCodes.SUCCESS);
    }


    @Test
    public void checkHttpsSchemaOfOriginalImagesUrls() {
        getListOfOriginalUrls().forEach(x -> assertThat(x.matches(HTTPS_REGEX), is(true)));
    }

    private List<String> getListOfOriginalUrls() {
        Response response = given()
            .when()
                .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
            .then()
                .extract().response();
        return JsonPath.read(response.asString(), ORIGINAL_URL_JSON_PATH);
    }
}