package tests;

import com.jayway.jsonpath.JsonPath;
import configuration.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import stubs.ba.ImagesStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImagesTest extends BasicTest {

    private static ConfigurationReader configurationReader;

    private final ImagesStub imagesStub = new ImagesStub();

    @BeforeClass
    public static void setUpEnvironment() {
        configurationReader = new ConfigurationReader();
    }

    @Before
    public void setPreemptiveBasicAuthentication() {
        RestAssured.authentication =
            preemptive().basic(configurationReader.getBasicAuthName(), configurationReader.getBasicAuthPassword());

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
    public void checkHttpsShemaOfOrignalImagesUrls() {
        getListOfOriginalUrls().forEach(x -> {
            assertThat(x.matches("^(https)://.*$"), is(true));
        });
    }

    private List<String> getListOfOriginalUrls() {
        Response response = given()
            .when()
                .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
            .then()
                .extract().response();

        return JsonPath.read(response.asString(), "$..originalUrl");
    }
}