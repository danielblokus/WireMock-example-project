package example;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import stubs.BasicAuthStub;
import utils.WebServicePaths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.equalTo;
import static utils.HttpStatusCodes.SUCCESS;

public class BasicAuthTest extends BasicTest {

    private static final String VALID_RESPONSE = "Hello BA!";
    BasicAuthStub basicAuthStub = new BasicAuthStub();

    @Before
    public void setPreemptiveBasicAuthentication() {
        RestAssured.authentication =
                preemptive()
                .basic("admin@danielblokus.github.io", "12345678");
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