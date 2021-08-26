package teste;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class BaseTeste {

    @BeforeAll
    public static void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }
}
