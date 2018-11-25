package stubs.ba;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static utils.HttpStatusCodes.SUCCESS;
import static utils.WebServicePaths.BA_ENDPOINT_PATH;

public class BasicAuthStub {

    private final String bodyResponse = "Hello BA!";
    private static final String VALID_USER_NAME = "admin@danielblokus.github.io";
    private static final String VALID_PASSWORD = "12345678";

    public void stubResponse() {
        stubFor(get(BA_ENDPOINT_PATH)
                .withBasicAuth(VALID_USER_NAME, VALID_PASSWORD)
                .willReturn(aResponse()
                        .withBody(bodyResponse)
                        .withStatus(SUCCESS)));
    }
}