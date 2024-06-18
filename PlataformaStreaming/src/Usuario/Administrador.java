package Usuario;
import Contenido.Pelicula;
import Contenido.Serie;
import Interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import Usuario.Usuarios;
import Contenido.AudioVisual;

public class Administrador extends Usuarios {


   private  HashMap<String,HashSet<AudioVisual>> contenido;



    public Administrador(String nombre, String contraseña, boolean estado) {
        super(nombre, contraseña, estado);
        this.contenido = new HashMap<>();

    }


}
