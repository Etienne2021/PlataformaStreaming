package Contenido;

import Excepciones.InvalidRatingException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Serie extends AudioVisual {

   private Set<Episodio> episodios;

    public Serie(String titulo, String genero, int año, int calificacion, boolean estado, int temporadas, int episodios) {
        super(titulo, genero, año, calificacion, estado);
        this.episodios = new HashSet<>();
    }


    public  void reproducir()
    {
        if(estado)
        {
            System.out.println("Reproduciendo serie:"+titulo);
            System.out.println("Numero de episodios"+episodios.size());
        }
        else
        {
            System.out.println(titulo+"No esta disponible actualmente");
        }
    }

    public void calificacion(int calificacion) throws InvalidRatingException
    {
        if(calificacion<1 && calificacion>5)
        {
            throw new InvalidRatingException("Calificacion invalida,debe ser entre 1 y 5");

        }else {
            this.calificacion=calificacion;
            System.out.println("Has calificado la serie"+ titulo+ "con"+calificacion+"estrellas");
        }
    }



   //ultimo visto tenemos que hacer un historial de episodios visto;



}