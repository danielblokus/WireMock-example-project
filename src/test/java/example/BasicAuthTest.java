package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.*;
import stubs.ba.BasicAuthStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.equalTo;
import static utils.HttpStatusCodes.SUCCESS;

public class BasicAuthTest {

    private static final int WIREMOCK_PORT = 2111;
    private static final String VALID_RESPONSE = "Hello BA!";
    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(options().port(WIREMOCK_PORT));

    private final BasicAuthStub basicAuthStub = new BasicAuthStub();

    @Before
    public void setUp() {
        RestAssured.authentication =
                preemptive()
                .basic(VALID_USER_NAME, VALID_PASSWORD);
    }

    @Test
    public void checkStatusCodeAfterBasicAuthorization() {
        basicAuthStub.stubResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
        .and()
            .assertThat()
                .statusCode(SUCCESS);
    }

    @Test
    public void checkBodyResponseAfterBasicAuthorization() {
        basicAuthStub.stubResponse();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
        .and()
            .assertThat()
                .body(equalTo(VALID_RESPONSE));
    }

    @Test
    public void checkStatusForUnauthorizedAccess() {
        basicAuthStub.stubWithoutBasicAuth();

        given()
            .log().all()
            .port(WIREMOCK_PORT)
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
                .assertThat()
                .statusCode(HttpStatusCodes.UNAUTHORIZED);
    }
}