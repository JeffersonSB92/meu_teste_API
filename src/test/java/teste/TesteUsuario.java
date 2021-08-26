package teste;

import dominio.Usuario;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIA_USUARIO_ENDPOINT = "/user";

    @Test
    public void testeExemplos() {
        given().
                params("page", "2").
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
                params("page", "2").
                when().
                get(LISTA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Usuario usuario = new Usuario("rafael", "eng test", "usuario@gmail.com");
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
        given().
                params("page", "2").
                when().
                get(LISTA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                    "page", is(2),
                    "data.size()", is(6),
                    "data.findAll { it.avatar.startsWith('https://reqres.in')}.size()", is(6)
//o código acima vai no data e acha todos(findAll), dentro das chaves ele
//percorre todos itens(it) vendo se o avatar inicia com(startsWith) um valor específico
//fora das chaves botamos o .size() para definir que é dentro do tamanho daquele array
//e botamos ,is(6) para dizermos que têm de ser 6 posições nesse array
                );
    }
}
