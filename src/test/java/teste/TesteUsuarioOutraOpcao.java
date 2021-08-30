package teste;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dominio.Usuario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TesteUsuarioOutraOpcao {

    private static final String BASE_URL = "https://reqres.in";
    private static final String BASE_PATH = "/api";

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIA_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

    @BeforeAll
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

   @Test
    public void testeMostraPaginaEspecifica() {

        String uri = getUri(LISTA_USUARIOS_ENDPOINT);

        given().
            param("page", "2").
        when().
            get(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {

        String uri = getUri(CRIA_USUARIO_ENDPOINT);

        Map<String, String> usuario = new HashMap<>();
        usuario.put("name", "rafael");
        usuario.put("job", "eng test");

        given().
            contentType(ContentType.JSON).
            body(usuario).
        when().
            post(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testeTamanhoDosItensMostradosIgualAoPerPage() {

        String uri = getUri(LISTA_USUARIOS_ENDPOINT);

        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
            params("page", paginaEsperada).
        when().
            get(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_OK).
            body(
                    "page", is(paginaEsperada),
                    "data.size()", is(perPageEsperado),
                    "data.findAll { it.avatar.startsWith('https://reqres.in')}.size()", is(perPageEsperado)
                );
    }

    @Test
    public void testeMostraUsuarioEspecifico(){

        String uri = getUri(MOSTRAR_USUARIO_ENDPOINT);

        Usuario usuario = given().
            pathParam("userId", 2).
        when().
            get(uri).
        then().
            contentType(ContentType.JSON).
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", Usuario.class);

        assertThat(usuario.getEmail(), containsString( "@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastName(), is("Weaver"));
    }

    private int retornaPerPageEsperado(int page) {

        String uri = getUri(LISTA_USUARIOS_ENDPOINT);

        int perPageEsperado = given().
                params("page", page).
            when().
                get(uri).
            then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
            extract().
                path("per_page");
                return perPageEsperado;
    }

    private String getUri(String endpoint) {
        String uri = BASE_URL + BASE_PATH + endpoint;
        return uri;
    }
}
