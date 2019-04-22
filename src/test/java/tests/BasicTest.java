package tests;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import configuration.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Rule;

import static io.restassured.RestAssured.preemptive;

public class BasicTest {

    private static final int port = 9999;

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(port);

    @BeforeClass
    public static void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.authentication =
                preemptive().basic(new ConfigurationReader().getBasicAuthName(), new ConfigurationReader().getBasicAuthPassword());
    }
}