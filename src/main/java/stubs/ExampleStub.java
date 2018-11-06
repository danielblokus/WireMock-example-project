package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static utils.HttpStatusCodes.NOT_FOUND;
import static utils.WebServicePaths.*;

public class ExampleStub {

	private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
	private static final String TEXT_PLAIN_HEADER_VALUE = "text/plain";
	private static final String bodyResponse = "Hello";
	private static final int FIXED_DELAY_IN_MILLISECONDS = 2000;

	public void stubExampleResponse() {
		stubFor(get(EXAMPLE_ENDPOINT_PATH)
			.willReturn(aResponse()
				.withHeader(CONTENT_TYPE_HEADER_KEY, TEXT_PLAIN_HEADER_VALUE)
				.withBody(bodyResponse)));
	}

	public void stubNotFoundResponse() {
		stubFor(get(INVALID_ENDPOINT_PATH)
			.willReturn(status(NOT_FOUND)));
	}

	public void stubFixedDelayResponse() {
		stubFor(get(DELAYED_ENDPOINT_PATH)
			.willReturn(aResponse()
				.withBody(bodyResponse)
				.withFixedDelay(FIXED_DELAY_IN_MILLISECONDS)));
	}
}
