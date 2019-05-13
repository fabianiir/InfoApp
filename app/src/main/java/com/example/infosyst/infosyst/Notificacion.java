package com.example.infosyst.infosyst;

public class Notificacion {
    public int indice;
    public String titulo;
    public String mensaje;
    public String fecha_hora;

    public Notificacion(int indice, String titulo, String mensaje, String fecha_hora) {
        this.indice=indice;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha_hora = fecha_hora;
    }

}