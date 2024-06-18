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

    public void reproducir() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nReproduciendo episodio: " + titulo + " (CapÃtulo " + nrocap );
        System.out.println("\n\n");
        int opcion;
        System.out.println("\nIngrese 1 para poner pausa o 0 para salir.");
        opcion = scanner.nextInt();
        do{
            if (opcion == 1) {
                System.out.println("\nEl episodio esta en pausa... \n\n");
                System.out.println("\nIngrese 2 para continuar con el episodio o 0 para salir.");
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

    @Override
    public String toString() {
        return "Episodio{" +
                "titulo='" + titulo + '\'' +
                ", nrocap=" + nrocap +
                ", duracionEp=" + duracionEp +
                '}';
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
