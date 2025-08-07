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
    

    // eliminación
    

public String eliminar(int id) {
    ResultadoEliminacion resultado = new ResultadoEliminacion();
    raiz = eliminarRec(raiz, id, resultado, null, false);
    return resultado.mensaje;
}

// Clase interna para comunicar resultado y mensaje
private class ResultadoEliminacion {
    String mensaje = "Nodo no encontrado.";
    boolean eliminado = false;
}


 // Método recursivo para eliminar un nodo, actualizando enlaces.

private Nodo eliminarRec(Nodo actual, int id, ResultadoEliminacion resultado, Nodo padre, boolean esIzquierdo) {
    if (actual == null) {
        return null;
    }
    if (id < actual.getTarjeta().getId()) {
        actual.setIzquierdo(eliminarRec(actual.getIzquierdo(), id, resultado, actual, true));
    } else if (id > actual.getTarjeta().getId()) {
        actual.setDerecho(eliminarRec(actual.getDerecho(), id, resultado, actual, false));
    } else {
        // Encontró el nodo a eliminar
        String categoria = actual.getTarjeta().getCategoria();
        if (categoria.equalsIgnoreCase("Civiles")) {
            resultado.mensaje = "No se permite eliminar tarjetas de Civiles.";
            return actual;
        }

        boolean tieneIzq = (actual.getIzquierdo() != null);
        boolean tieneDer = (actual.getDerecho() != null);

        // Caso: hoja
        if (!tieneIzq && !tieneDer) {
            resultado.mensaje = "Nodo hoja eliminado correctamente.";
            resultado.eliminado = true;
            return null;
        }

        // Caso: solo hijo izquierdo
        if (tieneIzq && !tieneDer) {
            resultado.mensaje = "Nodo con solo hijo izquierdo eliminado correctamente.";
            resultado.eliminado = true;
            return actual.getIzquierdo();
        }

        // Caso: solo hijo derecho
        if (!tieneIzq && tieneDer) {
            resultado.mensaje = "No se puede eliminar el nodo porque solo tiene un subárbol a su derecha.";
            return actual;
        }

        // Caso: dos hijos
        if (tieneIzq && tieneDer) {
            resultado.mensaje = "No se puede eliminar el nodo porque tiene dos hijos.";
            return actual;
        }
    }
    return actual;
}

    
    // eliminación, contar nodos, buscar mayor/menor
}
