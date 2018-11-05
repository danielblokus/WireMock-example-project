package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static utils.WebServicePaths.BA_ENDPOINT_PATH;

public class BasicAuthStub {

    private final String bodyResponse = "Hello BA!";

    public void stubResponse() {
        stubFor(get(BA_ENDPOINT_PATH)
                .withBasicAuth("admin@danielblokus.github.io", "12345678")
                .willReturn(aResponse()
                        .withBody(bodyResponse)
                        .withStatus(200)));
    }
}
