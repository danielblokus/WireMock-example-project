package tests;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import utils.HttpHeaders;
import utils.HttpStatusCodes;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static utils.WebServicePaths.INITIALIZE_ENDPOINT_PATH;

public class ServicesTest {

    private static final String JSON_RESPONSE_PATH = "stubs/service1/initResponse.json";

    @Rule
    public WireMockRule service1Mock = new WireMockRule(9999);

    @Rule
    public WireMockRule service2Mock = new WireMockRule(9998);

    @Before
    public void setUpEnvironment() {
        service1Mock.stubFor(get(INITIALIZE_ENDPOINT_PATH)
                .willReturn(aResponse()
                        .withStatus(HttpStatusCodes.SUCCESS)
                        .withHeader(HttpHeaders.CONTENT_TYPE_HEADER_KEY, HttpHeaders.JSON_HEADER_VALUE)
                        .withBodyFile(JSON_RESPONSE_PATH)));

        service2Mock.stubFor(get(anyUrl()).withHeader("userId", equalTo("6781769504"))
                .willReturn(okJson("{ \"message\": \"Hello from service2\" }")));
    }

    @Test
    public void checkCommunicationBetweenServices() {
        RestAssured.port = service1Mock.port();

        String uId = given()
                .when()
                .get(INITIALIZE_ENDPOINT_PATH).jsonPath().getString("uId");

        RestAssured.port = service2Mock.port();

        given()
            .log().everything()
            .header("userId", uId)
        .when()
            .get("/something2")
        .then()
                .log().everything()
            .and()
                .assertThat().statusCode(HttpStatusCodes.SUCCESS);
    }
}