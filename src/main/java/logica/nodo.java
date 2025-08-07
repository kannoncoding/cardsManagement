package logica;

import modelo.Tarjeta;

public class Nodo {
    private Tarjeta tarjeta;
    private Nodo izquierdo;
    private Nodo derecho;

    // Constructor
    public Nodo(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }
}
