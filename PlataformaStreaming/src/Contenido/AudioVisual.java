package Contenido;

import Excepciones.InvalidRatingException;

public  abstract class  AudioVisual {

    protected String titulo;

    protected String genero;

    protected int año;

    protected int calificacion;

    protected boolean estado;



    public AudioVisual(String titulo, String genero, int año, int calificacion, boolean estado) {
        this.titulo = titulo;
        this.genero = genero;
        this.año = año;
        this.calificacion = calificacion;
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public  abstract void reproducir();

 public  abstract void calificacion(int calificacion) throws InvalidRatingException;








}
