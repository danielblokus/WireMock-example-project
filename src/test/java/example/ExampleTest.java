package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.http.ContentType;
import org.junit.Rule;
import org.junit.Test;
import stubs.ExampleStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static utils.WebServicePaths.INVALID_ENDPOINT_PATH;

public class ExampleTest {

    private static final int WIREMOCK_PORT = 1131;
    private static final long FIXED_DELAY_IN_MILLISECONDS = 2500L;
    private static final String VALID_BODY_RESPONSE = "Hello";

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(options().port(WIREMOCK_PORT));

    private final ExampleStub exampleStub = new ExampleStub();

    @Test
    public void checkCorrectResponse() {
        exampleStub.stubExampleResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.EXAMPLE_ENDPOINT_PATH)
        .then()
            .assertThat()
                .body(equalTo(VALID_BODY_RESPONSE))
            .and()
                .statusCode(HttpStatusCodes.SUCCESS);
    }

    @Test
    public void checkInvalidPath() {
        exampleStub.stubNotFoundResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(INVALID_ENDPOINT_PATH)
        .then()
            .assertThat()
            .statusCode(HttpStatusCodes.NOT_FOUND);
    }

    @Test
    public void checkResponseHeader() {
        exampleStub.stubExampleResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.EXAMPLE_ENDPOINT_PATH)
        .then()
            .assertThat()
            .contentType(ContentType.TEXT);
    }

    @Test
    public void checkResponseAfterFixedDelay() {
        exampleStub.stubFixedDelayResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.DELAYED_ENDPOINT_PATH)
        .then()
            .assertThat()
                .body(equalTo(VALID_BODY_RESPONSE))
            .and()
                .time(lessThan(FIXED_DELAY_IN_MILLISECONDS));
    }
}