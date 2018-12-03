package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.*;
import stubs.ba.ImagesStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ImagesTest {

    private static final int WIREMOCK_PORT = 1112;
    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(options().port(WIREMOCK_PORT));

    private final ImagesStub imagesStub = new ImagesStub();

    @Before
    public void setUp() {
        RestAssured.authentication =
                preemptive().basic(VALID_USER_NAME, VALID_PASSWORD);

        imagesStub.stubResponse();
    }

    @Test
    public void checkBodyResponseNotEmpty() {
        given()
            .log().all()
            .port(WIREMOCK_PORT)
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
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.IMAGES_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
            .assertThat()
                .statusCode(HttpStatusCodes.SUCCESS);
    }
}