package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static utils.WebServicePaths.EXAMPLE_ENDPOINT_PATH;
import static utils.WebServicePaths.INVALID_ENDPOINT_PATH;
import static utils.HttpStatusCodes.NOT_FOUND;

public class ExampleStub {

	private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
	private static final String TEXT_PLAIN_HEADER_VALUE = "text/plain";

	public void stubExampleResponse() {
		String bodyResponse = "Hello";

		stubFor(get(EXAMPLE_ENDPOINT_PATH)
			.willReturn(aResponse()
				.withHeader(CONTENT_TYPE_HEADER_KEY, TEXT_PLAIN_HEADER_VALUE)
				.withBody(bodyResponse)));
	}

	public void stubNotFoundResponse() {
		stubFor(get(INVALID_ENDPOINT_PATH)
			.willReturn(status(NOT_FOUND)));
	}
}
