package Contenedoras;
import Usuario.Usuarios;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import Excepciones.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicioStreaming {

    private HashSet<Usuarios> usuariosHashSet;
    /*
    private Usuarios usuarioLogeado;
    private Perfil perfilSeleccionado;
    private ArrayList<Pelicula> peliculas;
    private HashMap<String, Serie> series;

*/


    public ServicioStreaming() {
        this.usuariosHashSet = new HashSet<>();
       // this.peliculas = new ArrayList<>();
       // this.series = new HashMap<>();
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
    if (!usuariosHashSet.contains(usuario)) {
        usuariosHashSet.add(usuario);
        guardarenarchivo();
        return "Nuevo usuario agregado";
    } else {
        for (Usuarios u : usuariosHashSet) {
            if (u.equals(usuario)) {
                u.getPerfiles().addAll(usuario.getPerfiles());
                break;
            }
        }
        guardarenarchivo();
        return "Perfiles agregados al usuario existente";
    }
}

public void verificarsiexiste(String nombre,String Contraseña)throws UsuarioNoEncontradoException
{

    for (Usuarios u : usuariosHashSet) {
        if (u.getNombre().equals(nombre) && u.getContraseña().equals(Contraseña)) {
            System.out.println("Inicio de sesión exitoso");
            return;
        }
    }
    throw new UsuarioNoEncontradoException("Nombre de usuario o contraseña incorrecta");

}



    public void verificarsiexisteusuario(String nombre) throws UsuarioYaexisteException {
        for (Usuarios u : usuariosHashSet) {
            if (u.getNombre().equals(nombre)) {
                throw new UsuarioYaexisteException("Nombre de usuario ya está en uso.");
            }
        }
    }


 public void guardarenarchivo()
 {

     File file=new File("Usuarios.json");
     ObjectMapper objectMapper=new ObjectMapper();
     try
     {
         objectMapper.writeValue(file,usuariosHashSet);
         System.out.println("Archivo guardado correctamente.");

     }catch (IOException e){
         System.out.println("No se pudo guardar el archivo"+e.getMessage());
     }

 }



    public void leerenarchivo()
    {

        File file=new File("Usuarios.json");
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
            usuariosHashSet=objectMapper.readValue(file, new TypeReference<HashSet<Usuarios>>() {});
            System.out.println("Archivo leído correctamente.");

        }catch (IOException e){
            System.out.println("No se pudo leer el archivo"+e.getMessage());
        }

    }






}
