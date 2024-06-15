package Usuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Perfil {

    private String nombre;



    public Perfil() {
    }

    // Constructor con anotaciones Jackson para el mapeo desde JSON
    @JsonCreator
    public Perfil(String nombre) {
        this.nombre = nombre;
    }



    public void getNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
