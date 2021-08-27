package teste;

import dominio.Usuario;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRA_USUARIO_ENDPOINT = "/register";
    private static final String LOGIN_USUARIO_ENDPOINT = "/login";

    @BeforeAll
    public static void setupRegistro(){
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    @Test
    public void testeNaoEfetuaRegistroSemSenha(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");

        given().
            body(usuario).
        when().
            post(REGISTRA_USUARIO_ENDPOINT).
        then().
            body("error", is("Missing password"));
    }
//Esse teste deveria estar em uma classe TesteLogin, porém está aqui para mostrar
// a funcionalidade de múltiplos setups e ResponseSpec
    @Test
    public void testeLoginNaoEfetuadoQuandoSenhaEstaFaltando(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");

        given().
                body(usuario).
                when().
                post(LOGIN_USUARIO_ENDPOINT).
                then().
                body("error", is("Missing password"));
    }

}
