package Contenido;

import Excepciones.InvalidRatingException;

import java.util.Scanner;

public class Pelicula extends AudioVisual{


    private double duracion;

    public Pelicula(String titulo, String genero, int año, int calificacion, boolean estado, double duracion) {
        super(titulo, genero, año, calificacion, estado);
        this.duracion = duracion;
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


    @Override
    public String toString() {
        return "Pelicula{" +
                "duracion=" + duracion +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", año=" + año +
                ", calificacion=" + calificacion +
                ", estado=" + estado +
                '}';
    }
}

