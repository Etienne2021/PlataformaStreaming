package Contenedoras;
import Contenido.Pelicula;
import Contenido.Serie;
import Usuario.Perfil;
import Usuario.Usuarios;
import java.util.ArrayList;
import java.util.HashMap;
import Excepciones.*;

import javax.swing.*;

public class ServicioStreaming {

    private HashMap<String, Usuarios> usuariosHashmap;
    private Usuarios usuarioLogeado;
    private Perfil perfilSeleccionado;
    private ArrayList<Pelicula> peliculas;
    private HashMap<String, Serie> series;



    public ServicioStreaming() {
        this.usuariosHashmap = new HashMap<>();
        this.peliculas = new ArrayList<>();
        this.series = new HashMap<>();
    }


   public void validarcontraseña(String contraseña,String confirmacontraseña) throws ContraseñaNoCoincidenException
   {

       if(!contraseña.equals(confirmacontraseña))
       {
           throw  new ContraseñaNoCoincidenException("Las contraseñas no coinciden");
       }
       if(contraseña.length()<8)
       {
           throw  new ContraseñaNoCoincidenException("La primer clave ingresada es muy pqueña,ingrese mas de 8 caracteres");
       }

   }

public String  AgregarUsuario(Usuarios usuario)
{
    if (usuario.getPerfiles().isEmpty()) {
        return "No hay perfiles creados";
    }
    if (!usuariosHashmap.containsKey(usuario.getNombre())) {
        usuariosHashmap.put(usuario.getNombre(), usuario);
        return "Nuevo usuario agregado";
    } else {
        Usuarios usuarioExistente = usuariosHashmap.get(usuario.getNombre());
        usuarioExistente.getPerfiles().addAll(usuario.getPerfiles());
        return "Perfiles agregados al usuario existente";
    }
}

public void verificarsiexiste(String nombre,String Contraseña)throws UsuarioNoEncontradoException
{

    Usuarios usuario = usuariosHashmap.get(nombre);
    if (usuario != null && usuario.getContraeña().equals(Contraseña)) {
        System.out.println("Inicio de sesión exitoso");
    } else {
        throw new UsuarioNoEncontradoException("Nombre de usuario o contraseña incorrecta");
    }

}








}
