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

public class Administrador <T extends AudioVisual>  extends Usuarios implements ABM <T>{


   private  HashMap<String,HashSet<AudioVisual>> contenido;



    public Administrador(String nombre, String contraseña) {
        super(nombre, contraseña);
        this.contenido = new HashMap<>();
    }

    public Administrador(String nombre, String contraseña, boolean estado) {
        super(nombre, contraseña, estado);
        this.contenido = new HashMap<>();

    }



    public String alta(HashSet<T>elementos) {


        if(elementos.isEmpty())
        {
            return "Vacia";
        }
        T primerElemento = elementos.iterator().next();
        String titulo=primerElemento.getTitulo();

        if(!contenido.containsKey(titulo))
        {
            contenido.put(titulo, (HashSet<AudioVisual>) elementos);
            return "Carga exitosa nuevo titulo agregado";
        }
        else
        {
            HashSet<AudioVisual>contenidoexistente=contenido.get(titulo);
            contenidoexistente.addAll(elementos);
            contenido.put(titulo,contenidoexistente);
            return "Carga exitosa";
        }

    }





    public void modificar(T elemento) {
        // Implementar lógica para modificar un objeto
    }

}
