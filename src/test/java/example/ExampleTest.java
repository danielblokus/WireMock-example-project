package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import stubs.ExampleStub;
import utils.WebServicePaths;
import utils.HttpStatusCodes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.WebServicePaths.INVALID_ENDPOINT_PATH;

public class ExampleTest extends BasicTest {

	private ExampleStub exampleStub = new ExampleStub();


	@Test
	public void checkCorrectResponse() {
		exampleStub.stubExampleResponse();

		given()
		.when()
			.get(WebServicePaths.EXAMPLE_ENDPOINT_PATH)
		.then()
			.assertThat()
				.body(equalTo("Hello"))
			.and()
				.statusCode(HttpStatusCodes.SUCCESS);
	}

	@Test
	public void checkInvalidPath() {
		exampleStub.stubNotFoundResponse();

		given()
		.when()
			.get(INVALID_ENDPOINT_PATH)
		.then()
			.assertThat()
				.statusCode(HttpStatusCodes.NOT_FOUND);
	}

	@Test
	public void checkResponseHeader() {
		exampleStub.stubExampleResponse();

		given()
		.when()
			.get(WebServicePaths.EXAMPLE_ENDPOINT_PATH)
		.then()
			.assertThat()
				.contentType(ContentType.TEXT);
	}
}