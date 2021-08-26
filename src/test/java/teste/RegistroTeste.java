package teste;

import dominio.Usuario;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class RegistroTeste extends BaseTeste{

    private static final String REGISTRA_USUARIO_ENDPOINT = "/register";

    @Test
    public void testeNaoEfetuaRegistroSemSenha(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");

        given().
            body(usuario).
        when().
            post(REGISTRA_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }

}
