package tests;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import configuration.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.preemptive;

public class BasicTest {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @BeforeClass
    public static void setUp() {
        RestAssured.port = wireMockRule.port();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.authentication =
                preemptive().basic(new ConfigurationReader().getBasicAuthName(),
                        new ConfigurationReader().getBasicAuthPassword());
    }
}