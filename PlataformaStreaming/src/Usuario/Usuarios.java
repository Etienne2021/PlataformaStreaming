package Usuario;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Usuarios {

    private  String Nombre;

    private String contraeña;

    private boolean Estado;


    private ArrayList<Perfil>perfiles;



    public Usuarios(String nombre, String contraeña) {
        this.Nombre=nombre;
        this.contraeña = contraeña;
        this.Estado=false;
        this.perfiles=new ArrayList<>();
    }





    public ArrayList<Perfil> agregarPerfil(Perfil perfil) {
        perfiles.add(perfil);
        return perfiles;
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

    public String getContraeña() {
        return contraeña;
    }

    public void setContraeña(String contraeña) {
        this.contraeña = contraeña;
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
        return Objects.equals(getNombre(), usuarios.getNombre()) && Objects.equals(getContraeña(), usuarios.getContraeña()) && Objects.equals(getEstado(), usuarios.getEstado()) && Objects.equals(getPerfiles(), usuarios.getPerfiles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getContraeña(), getEstado(), getPerfiles());
    }
}



