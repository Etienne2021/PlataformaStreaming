package Contenido;

import Excepciones.InvalidRatingException;

import java.util.Scanner;

public class Pelicula extends AudioVisual{


    private double duracion;

    public Pelicula(String titulo, String genero, int año, int calificacion, boolean estado, double duracion) {
        super(titulo, genero, año, calificacion, estado);
        this.duracion = duracion;
    }



    public void reproducir() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nReproduciendo Pelicula: " + titulo);
        System.out.println("\n\n");
        int opcion;
        System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
        opcion = scanner.nextInt();
        do{
            if (opcion == 1) {
                System.out.println("\nLa Pelicula esta en pausa... \n\n");
                System.out.println("\nIngrese 2 para continuar con la pelicula o 0 para salir.");
                opcion = scanner.nextInt();
            }
            else if (opcion == 2){
                System.out.println("\nEl episodio se esta reproduciendo... \n\n");
                System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
                opcion = scanner.nextInt();
            }
            else {
                System.out.println("\nCaracter invalido.");
                System.out.println("\nIngrese otro caracter.");
            }
        }while(opcion != 0);

        // return inicio!!!
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

