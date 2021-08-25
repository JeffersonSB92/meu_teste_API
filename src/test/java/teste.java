import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;

public class teste {

    @Test
    public void testeListaMetadadosDoUsuario(){
        when().
            get("https://reqres.in/api/users?page=2").
        then().
            statusCode(200). //alterar o valor para 203 ou outro para garantir que o teste falha
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)). //alterar o valor para 1 ou outro para garantir que o teste falha
            body("per_page", is(6)).
            body("data[0].email", is("michael.lawson@reqres.in")).
            body("data[0].first_name", is("Michael")).
            body("data", is(notNullValue()));
    }

    @Test
    public void criaUsuarioComSucesso(){
        given().log().all().
            contentType(ContentType.JSON).
            body("{\"name\": \"rafael\", \"job\": \"eng test\"}").
        when().
            post("https://reqres.in/api/users").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }
}
