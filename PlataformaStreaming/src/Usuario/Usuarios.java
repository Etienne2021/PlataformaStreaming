package Usuario;

import java.util.ArrayList;

public class Usuarios {

    private  String Nombre;

    private String contraeña;

    private Boolean Estado;

    private ArrayList<Perfil>perfiles;


    public Usuarios(String nombre, String contraeña) {
        this.Nombre=nombre;
        this.contraeña = contraeña;
        this.Estado=false;
        this.perfiles=new ArrayList<>();
    }



    public void iniciarSesion() {

    }

    public void cerrarSesion() {

    }

    public void registrarse() {

    }

    public void seleccionarPerfil() {

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
}

