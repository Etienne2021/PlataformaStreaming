package Contenido;

import Excepciones.InvalidRatingException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
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