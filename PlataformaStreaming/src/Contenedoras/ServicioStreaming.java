package Contenedoras;
import Contenido.Pelicula;
import Contenido.Serie;
import Usuario.Perfil;
import Usuario.Usuarios;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class ServicioStreaming {

    private HashSet<Usuarios> usuarios;
    private Usuarios usuarioLogeado;
    private Perfil perfilSeleccionado;
    private ArrayList<Pelicula> peliculas;
    private HashMap<String, Serie> series;




    public ServicioStreaming() {
        this.usuarios = new HashSet<>();
        this.peliculas = new ArrayList<>();
        this.series = new HashMap<>();
    }


}
