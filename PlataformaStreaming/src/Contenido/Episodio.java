package Contenido;
import Excepciones.InvalidRatingException;

import java.util.Objects;

public class Episodio {

    private String titulo;
    private int nrocap;
    private double duracionEp;


    public Episodio(String titulo, int nrocap, double duracionEp) {
        this.titulo = titulo;
        this.nrocap = nrocap;
        this.duracionEp = duracionEp;
    }

    public void reproducir() {
        System.out.println("Reproduciendo episodio: " + titulo + " (Capítulo " + nrocap + ")");
    }

    public void calificar(int calificacion) throws InvalidRatingException {
        if (calificacion < 1 || calificacion > 5) {
            throw new InvalidRatingException("Calificación inválida. Debe ser entre 1 y 5.");
        }
        System.out.println("Has calificado el episodio " + titulo + " con " + calificacion + " estrellas.");
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNroCap() {
        return nrocap;
    }

    public void setNroCap(int nroCap) {
        this.nrocap = nrocap;
    }

    public double getDuracionEp() {
        return duracionEp;
    }

    public void setDuracionEp(double duracionEp) {
        this.duracionEp = duracionEp;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Episodio episodio)) return false;
        return nrocap == episodio.nrocap && Double.compare(getDuracionEp(), episodio.getDuracionEp()) == 0 && Objects.equals(getTitulo(), episodio.getTitulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), nrocap, getDuracionEp());
    }

}
