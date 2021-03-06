package tests;

import org.junit.Test;
import stubs.ba.BasicAuthStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static utils.HttpStatusCodes.SUCCESS;

public class BasicAuthTest extends BasicTest {

    private static final String VALID_RESPONSE = "Hello BA!";

    private final BasicAuthStub basicAuthStub = new BasicAuthStub();

    @Test
    public void checkStatusCodeAfterBasicAuthorization() {
        basicAuthStub.stubResponse();

        given()
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
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
            .assertThat()
                .body(equalTo(VALID_RESPONSE));
    }

    @Test
    public void checkStatusForUnauthorizedAccess() {
        basicAuthStub.stubWithoutBasicAuth();

        given()
        .when()
            .get(WebServicePaths.BA_ENDPOINT_PATH)
        .then()
            .log().ifValidationFails()
                .assertThat()
                    .statusCode(HttpStatusCodes.UNAUTHORIZED);
    }
}