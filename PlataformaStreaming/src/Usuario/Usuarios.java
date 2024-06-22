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


    public Usuarios() {

    }

    // Constructor básico para inicializar perfiles vacíos
    public Usuarios(String nombre, String contraseña) {
        this.Nombre = nombre;
        this.contraseña = contraseña;
        this.Estado = true;
        this.perfiles = new ArrayList<>();
    }


    // Constructor con anotaciones Jackson para el mapeo desde JSON
    @JsonCreator
    public Usuarios(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("contraseña") String contraseña,
            @JsonProperty("estado") boolean estado) {
        this.Nombre = nombre;
        this.contraseña = contraseña;
        this.perfiles = new ArrayList<>();
        this.Estado = estado;
    }


    public void agregarPerfil(Perfil perfil) {
        perfiles.add(perfil);
    }

    public void eliminarPerfil(String nombrePerfil) {

        Perfil perfilAEliminar = null;
        for (Perfil perfil : perfiles) {
            if (perfil.getNombre().equals(nombrePerfil)) {
                perfilAEliminar = perfil;
                break;
            }
        }
        if (perfilAEliminar != null) {
            perfiles.remove(perfilAEliminar);
        }
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

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public ArrayList<Perfil> getPerfiles() {
        return perfiles;
    }

    public Perfil getPerfil(String nombre) {
        for (Perfil perfil : perfiles) {
            if (perfil.getNombre().equalsIgnoreCase(nombre)) {
                return perfil;
            }
        }
        return null; // O lanzar una excepción si prefieres manejar perfiles no encontrados de otra manera
    }


    public void setPerfiles(ArrayList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuarios usuarios)) return false;
        return isEstado() == usuarios.isEstado() && Objects.equals(getNombre(), usuarios.getNombre()) && Objects.equals(getContraseña(), usuarios.getContraseña()) && Objects.equals(getPerfiles(), usuarios.getPerfiles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getContraseña(), isEstado(), getPerfiles());
    }


    @Override
    public String toString() {
        return "Usuarios{" +
                "Nombre='" + Nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", perfiles=" + perfiles +
                '}';
    }
}