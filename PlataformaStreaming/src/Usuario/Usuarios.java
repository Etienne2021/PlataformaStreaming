package Usuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Objects;

public class Usuarios {

    private  String Nombre;

    private String contraseña;

    private boolean Estado;


    private ArrayList<Perfil>perfiles;


    // Constructor básico para inicializar perfiles vacíos
    public Usuarios(String nombre, String contraseña) {
        this.Nombre = nombre;
        this.contraseña = contraseña;
        this.Estado = false; // Inicializado en falso por defecto
        this.perfiles = new ArrayList<>();
    }


    // Constructor con anotaciones Jackson para el mapeo desde JSON
    @JsonCreator
    public Usuarios(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("contraseña") String contraseña,
            @JsonProperty("perfiles") ArrayList<Perfil> perfiles,
            @JsonProperty("estado") boolean estado) {
        this.Nombre = nombre;
        this.contraseña = contraseña;
        this.perfiles = perfiles != null ? perfiles : new ArrayList<>();
        this.Estado = estado;
    }




    public void agregarPerfil(Perfil perfil) {
        perfiles.add(perfil);
    }

    public void eliminarPerfil(Perfil perfil) {
        perfiles.remove(perfil);
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public ArrayList<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(ArrayList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuarios usuarios)) return false;
        return Objects.equals(getNombre(), usuarios.getNombre()) && Objects.equals(getContraseña(), usuarios.getContraseña()) && Objects.equals(getEstado(), usuarios.getEstado()) && Objects.equals(getPerfiles(), usuarios.getPerfiles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getContraseña(), getEstado(), getPerfiles());
    }
}



