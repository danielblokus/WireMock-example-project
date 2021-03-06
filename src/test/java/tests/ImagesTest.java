package tests;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import stubs.ba.ImagesStub;
import utils.HttpStatusCodes;
import utils.WebServicePaths;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ImagesTest extends BasicTest {

    private static final String HTTPS_REGEX = "^(https)://.*$";
    private static final String ORIGINAL_URL_JSON_PATH = "$..originalUrl";

    private final ImagesStub imagesStub = new ImagesStub();

    @Before
    public void setUpEnvironment() {
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

        WireMock.verify(getRequestedFor(urlPathEqualTo(WebServicePaths.IMAGES_ENDPOINT_PATH)));
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
    public void checkOriginalImagesUrlsHaveHttpsScheme() {
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

    @Test
    @Ignore
    public void checkEntireResponseWithDynamicHeaders() {
        Response response = given()
                .get(WebServicePaths.IMAGES_ENDPOINT_PATH);

        Approvals.verify(replaceDynamicData(getEntireResponse(response)));
    }

    private String replaceDynamicData(String responseAsString) {
        responseAsString = responseAsString.replaceAll("User-Id=.*", "###GENERATED-USER-ID###");
        responseAsString = responseAsString.replaceAll("Matched-Stub-Id=.*", "###GENERATED-STUB-ID###");
        return responseAsString;
    }

    private String getEntireResponse(Response response) {
        return getResponseHeaders(response) + "\n" + getResponseBody(response);
    }

    private String getResponseHeaders(Response response) {
        return response.headers().toString();
    }

    private String getResponseBody(Response response) {
        return response.body().asString();
    }
}