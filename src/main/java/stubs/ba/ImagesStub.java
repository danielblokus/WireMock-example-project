package stubs.ba;

import utils.HttpHeaders;
import utils.HttpStatusCodes;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static utils.WebServicePaths.IMAGES_ENDPOINT_PATH;

public class ImagesStub {

    private static final String JSON_RESPONSE_PATH = "stubs/ba/imagesResponse.json";
    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    public void stubResponse() {
        stubFor(get(IMAGES_ENDPOINT_PATH)
                .withBasicAuth(VALID_USER_NAME, VALID_PASSWORD)
                .willReturn(aResponse()
                        .withStatus(HttpStatusCodes.SUCCESS)
                        .withHeader(HttpHeaders.CONTENT_TYPE_HEADER_KEY, HttpHeaders.JSON_HEADER_VALUE)
                        .withHeader("User-Id", generateRandomUUID())
                        .withBodyFile(JSON_RESPONSE_PATH)));
    }

    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}