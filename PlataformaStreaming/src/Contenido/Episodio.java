package Contenido;
import Excepciones.InvalidRatingException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Scanner;

public class Episodio {

    private String titulo;
    private int nrocap;
    private double duracionEp;

    @JsonCreator
    public Episodio(@JsonProperty("titulo") String titulo,
                    @JsonProperty("nrocap") int nrocap,
                    @JsonProperty("duracionEp") double duracionEp) {
        this.titulo = titulo;
        this.nrocap = nrocap;
        this.duracionEp = duracionEp;
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNrocap() {
        return nrocap;
    }

    public void setNrocap(int nrocap) {
        this.nrocap = nrocap;
    }

    public double getDuracionEp() {
        return duracionEp;
    }

    public void setDuracionEp(double duracionEp) {
        this.duracionEp = duracionEp;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "titulo='" + titulo + '\'' +
                ", nrocap=" + nrocap +
                ", duracionEp=" + duracionEp +
                '}';
    }



}
