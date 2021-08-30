package dominio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true) //ignora argumentos não usados do construtor
@Data //equivale ao @Getter e ao @Setter
@AllArgsConstructor //cria construtor com todos argumentos
@NoArgsConstructor //cria o construtor padrão sem argumentps
public class Usuario {

    @JsonAlias("first_name")
    private String name;
    private String job;
    private String email;
    @JsonAlias("last_name")
    private String lastName;
}