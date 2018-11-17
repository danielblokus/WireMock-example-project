package example;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Rule;

public class BasicTest {

    private static final int port = 9999;

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(port);

    @BeforeClass
    public static void setUp() {
        RestAssured.port = port;
    }
}