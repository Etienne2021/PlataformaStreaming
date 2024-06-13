package Contenido;

import Excepciones.InvalidRatingException;

import java.util.Scanner;

public class Pelicula extends AudioVisual{


    private double duracion;

    public Pelicula(String titulo, String genero, int año, int calificacion, boolean estado, double duracion) {
        super(titulo, genero, año, calificacion, estado);
        this.duracion = duracion;
    }


    public void reproducir()
    {
       if(estado)
       {
           System.out.println("Reproduciendo pelicula"+titulo);
           System.out.println("Duracion:"+duracion+"Horas");
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

        }else
        {
            this.calificacion=calificacion;
            System.out.println("Has calificado la pelicula"+titulo+"con"+calificacion+"estrellas");
        }
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }
}

