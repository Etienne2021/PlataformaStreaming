package Contenedoras;
import Contenido.Pelicula;
import Contenido.Serie;
import Usuario.Perfil;
import Usuario.Usuarios;

import java.util.ArrayList;
import java.util.HashMap;



public class ServicioStreaming {

    private HashMap<String, Usuarios> usuarios;
    private Usuarios usuarioLogeado;
    private Perfil perfilSeleccionado;
    private ArrayList<Pelicula> peliculas;
    private HashMap<String, Serie> series;




    public ServicioStreaming() {
        this.usuarios = new HashMap<>();
        this.peliculas = new ArrayList<>();
        this.series = new HashMap<>();
    }


}
