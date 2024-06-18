package Contenido;

import Excepciones.InvalidRatingException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Serie extends AudioVisual {

   private Set<Episodio> episodios;

    public Serie(String titulo, String genero, int año, int calificacion, boolean estado, Set<Episodio> episodios) {
        super(titulo, genero, año, calificacion, estado);
        this.episodios = new HashSet<>();
    }

    @JsonCreator
    public Serie(@JsonProperty("titulo") String titulo,
                 @JsonProperty("genero") String genero,
                 @JsonProperty("año") int año,
                 @JsonProperty("calificacion") int calificacion,
                 @JsonProperty("estado") boolean estado)
    {
        super(titulo, genero, año, calificacion, estado);
        this.episodios = new HashSet<>();
    }


    public void agregarEpisodio(Episodio episodio) {
        episodios.add(episodio);
    }

    public void eliminarEpidosio(Episodio episodio)
    {
        episodios.remove(episodio);
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

    @Override
    public String toString() {
        return "Serie{" +
                "episodios=" + episodios +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", año=" + año +
                ", calificacion=" + calificacion +
                ", estado=" + estado +
                '}';
    }

    public Set<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Set<Episodio> episodios) {
        this.episodios = episodios;
    }


    //ultimo visto tenemos que hacer un historial de episodios visto;



}