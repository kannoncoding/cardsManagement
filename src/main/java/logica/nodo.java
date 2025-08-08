// Universidad Estatal a Distancia (UNED)
// II Cuatrimestre 2025
// Estructura de Datos - Proyecto 3
// Jorge Luis Arias Melendez

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

    // -------- Getters / Setters --------
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

 
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}
