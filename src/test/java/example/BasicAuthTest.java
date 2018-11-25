package example;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import stubs.ba.BasicAuthStub;
import utils.WebServicePaths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.equalTo;
import static utils.HttpStatusCodes.SUCCESS;

public class BasicAuthTest extends BasicTest {

    private static final String VALID_RESPONSE = "Hello BA!";
    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    private final BasicAuthStub basicAuthStub = new BasicAuthStub();

    @Before
    public void setPreemptiveBasicAuthentication() {
        RestAssured.authentication =
                preemptive()
                .basic(VALID_USER_NAME, VALID_PASSWORD);
    }

    @Test
    public void checkStatusCodeAfterBasicAuthorization() {
        basicAuthStub.stubResponse();

        given()
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
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
        .and()
            .assertThat()
                .body(equalTo(VALID_RESPONSE));
    }
}