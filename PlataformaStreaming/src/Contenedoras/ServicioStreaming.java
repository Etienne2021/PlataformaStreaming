package Contenedoras;
import Contenido.Pelicula;
import Contenido.Serie;
import Usuario.Perfil;
import Usuario.Usuarios;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Excepciones.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicioStreaming holaa{

    private HashMap<String, Usuarios> usuariosHashmap;
    /*
    private Usuarios usuarioLogeado;
    private Perfil perfilSeleccionado;
    private ArrayList<Pelicula> peliculas;
    private HashMap<String, Serie> series;

*/


    public ServicioStreaming() {
        leerenarchivo();
        this.usuariosHashmap = new HashMap<>();
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
    if (!usuariosHashmap.containsKey(usuario.getNombre())) {
        usuariosHashmap.put(usuario.getNombre(), usuario);
        guardarenarchivo();
        return "Nuevo usuario agregado";
    } else {
        Usuarios usuarioExistente = usuariosHashmap.get(usuario.getNombre());
        usuarioExistente.getPerfiles().addAll(usuario.getPerfiles());
        guardarenarchivo();
        return "Perfiles agregados al usuario existente";
    }
}

public void verificarsiexiste(String nombre,String Contraseña)throws UsuarioNoEncontradoException
{

    Usuarios usuario = usuariosHashmap.get(nombre);
    if (usuario != null && usuario.getContraseña().equals(Contraseña)) {
        System.out.println("Inicio de sesión exitoso");
    } else {
        throw new UsuarioNoEncontradoException("Nombre de usuario o contraseña incorrecta");
    }

}



public void verificarsiexisteusuario(String nombre) throws UsuarioYaexisteException
{
    if (usuariosHashmap.containsKey(nombre)) {
        throw new UsuarioYaexisteException("Nombre de usuario ya está en uso.");
    }
}


 public void guardarenarchivo()
 {

     File file=new File("Usuarios.json");
     ObjectMapper objectMapper=new ObjectMapper();
     try
     {
         objectMapper.writeValue(file,usuariosHashmap);
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
            usuariosHashmap=objectMapper.readValue(file, new TypeReference<HashMap<String, Usuarios>>() {});
            System.out.println("Archivo leído correctamente.");

        }catch (IOException e){
            System.out.println("No se pudo leer el archivo"+e.getMessage());
        }

    }






}
