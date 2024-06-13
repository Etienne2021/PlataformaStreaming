package Contenido;

public class Pelicula extends AudioVisual{


    private double duracion;



    public Pelicula(String titulo, String genero, int año, int calificacion, boolean estado, double duracion) {
        super(titulo, genero, año, calificacion, estado);
        this.duracion = duracion;
    }


}
