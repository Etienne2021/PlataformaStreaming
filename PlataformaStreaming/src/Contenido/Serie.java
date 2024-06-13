package Contenido;

public class Serie extends AudioVisual {

    private int temporadas;
    private int episodios;

    public Serie(String titulo, String genero, int año, int calificacion, boolean estado, int temporadas, int episodios) {
        super(titulo, genero, año, calificacion, estado);
        this.temporadas = temporadas;
        this.episodios = episodios;
    }


}