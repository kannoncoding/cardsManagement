package logica;

import modelo.Tarjeta;

public class ArbolBinarioBusqueda {
    private Nodo raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    // Método para insertar una nueva tarjeta (recursivo)
    public boolean insertar(Tarjeta tarjeta) {
        if (raiz == null) {
            raiz = new Nodo(tarjeta);
            return true;
        } else {
            return insertarRec(raiz, tarjeta);
        }
    }

    private boolean insertarRec(Nodo actual, Tarjeta tarjeta) {
        if (tarjeta.getId() == actual.getTarjeta().getId()) {
            // ID duplicado, no insertar
            return false;
        } else if (tarjeta.getId() < actual.getTarjeta().getId()) {
            if (actual.getIzquierdo() == null) {
                actual.setIzquierdo(new Nodo(tarjeta));
                return true;
            } else {
                return insertarRec(actual.getIzquierdo(), tarjeta);
            }
        } else {
            if (actual.getDerecho() == null) {
                actual.setDerecho(new Nodo(tarjeta));
                return true;
            } else {
                return insertarRec(actual.getDerecho(), tarjeta);
            }
        }
    }

    // Método para buscar por ID
    public Tarjeta buscar(int id) {
        return buscarRec(raiz, id);
    }

    private Tarjeta buscarRec(Nodo actual, int id) {
        if (actual == null) return null;
        if (id == actual.getTarjeta().getId()) return actual.getTarjeta();
        else if (id < actual.getTarjeta().getId()) return buscarRec(actual.getIzquierdo(), id);
        else return buscarRec(actual.getDerecho(), id);
    }

    // Recorridos (Preorden, Inorden, Postorden) - Solo las firmas, puedes completarlas luego

    public String recorridoPreorden() {
        StringBuilder sb = new StringBuilder();
        preorden(raiz, sb);
        return sb.toString();
    }

    private void preorden(Nodo actual, StringBuilder sb) {
        if (actual != null) {
            sb.append(actual.getTarjeta().getId()).append("-");
            preorden(actual.getIzquierdo(), sb);
            preorden(actual.getDerecho(), sb);
        }
    }

    public String recorridoInorden() {
        StringBuilder sb = new StringBuilder();
        inorden(raiz, sb);
        return sb.toString();
    }

    private void inorden(Nodo actual, StringBuilder sb) {
        if (actual != null) {
            inorden(actual.getIzquierdo(), sb);
            sb.append(actual.getTarjeta().getId()).append("-");
            inorden(actual.getDerecho(), sb);
        }
    }

    public String recorridoPostorden() {
        StringBuilder sb = new StringBuilder();
        postorden(raiz, sb);
        return sb.toString();
    }

    private void postorden(Nodo actual, StringBuilder sb) {
        if (actual != null) {
            postorden(actual.getIzquierdo(), sb);
            postorden(actual.getDerecho(), sb);
            sb.append(actual.getTarjeta().getId()).append("-");
        }
    }

    // eliminación, contar nodos, buscar mayor/menor
}
