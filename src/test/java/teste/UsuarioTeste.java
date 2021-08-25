package teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class UsuarioTeste {

    @BeforeAll
    public static void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }

    @Test
    public void testeListaMetadadosDoUsuario(){
        given().
            params("page", "2").
        when().
            get("/users").
        then().
            statusCode(200). //alterar o valor para 203 ou outro para garantir que o UsuarioTeste.UsuarioTeste falha
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)). //alterar o valor para 1 ou outro para garantir que o UsuarioTeste.UsuarioTeste falha
            body("per_page", is(6)).
            body("data[0].email", is("michael.lawson@reqres.in")).
            body("data[0].first_name", is("Michael")).
            body("data", is(notNullValue()));
    }

    @Test
    public void criaUsuarioComSucesso(){
        given().
            contentType(ContentType.JSON).
            body("{\"name\": \"rafael\", \"job\": \"eng test\"}").
        when().
            post("/users").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }
}
