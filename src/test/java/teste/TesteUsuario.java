package teste;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dominio.Usuario;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIA_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Test
    public void testeExemplos() {

        given().
            param("page", "2").
        when().
            get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(200). //alterar o valor para 203 ou outro para garantir que o TesteUsuario.TesteUsuario falha
            statusCode(HttpStatus.SC_OK).
            body("per_page", is(6)).
            body("data[0].email", is("michael.lawson@reqres.in")).
            body("data[0].first_name", is("Michael"));
    }

    @Test
    public void testeMostraPaginaEspecifica() {

        given().
            param("page", "2").
        when().
            get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Usuario usuario = new Usuario("rafael", "eng test", "usuario@gmail.com", "lima");

        given().
            body(usuario).
        when().
            post(CRIA_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
            params("page", paginaEsperada).
        when().
            get(LISTA_USUARIOS_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                    "page", is(paginaEsperada),
                    "data.size()", is(perPageEsperado),
                    "data.findAll { it.avatar.startsWith('https://reqres.in')}.size()", is(perPageEsperado)
//o código acima vai no data e acha todos(findAll), dentro das chaves ele
//percorre todos itens(it) vendo se o avatar inicia com(startsWith) um valor específico
//fora das chaves botamos o .size() para definir que é dentro do tamanho daquele array
//e botamos ,is(6) para dizermos que têm de ser 6 posições nesse array
                );
    }

    @Test
    public void testeMostraUsuarioEspecifico(){

        Usuario usuario = given().
            pathParam("userId", 2).
        when().
            get(MOSTRAR_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
        extract().
            body().jsonPath().getObject("data", Usuario.class);

        assertThat(usuario.getEmail(), containsString( "@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastName(), is("Weaver"));
    }

    private int retornaPerPageEsperado(int page) {

        int perPageEsperado = given().
                params("page", page).
            when().
                get(LISTA_USUARIOS_ENDPOINT).
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                path("per_page");
                return perPageEsperado;
    }
}
