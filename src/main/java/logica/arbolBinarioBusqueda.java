package logica;

import modelo.Tarjeta;
import java.awt.*;

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

    // Recorridos (Preorden, Inorden, Postorden)

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
        // Encontro el nodo a eliminar
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

// Contar nodos de categoría “Súper héroes” o “Súper villanos”

public int contarSuperheroesOVillanos() {
    return contarSuperheroesOVillanosRec(raiz);
}

private int contarSuperheroesOVillanosRec(Nodo actual) {
    if (actual == null) return 0;
    String categoria = actual.getTarjeta().getCategoria();
    int suma = 0;
    if (categoria.equalsIgnoreCase("Súper héroes") || categoria.equalsIgnoreCase("Súper villanos")) {
        suma = 1;
    }
    return suma 
        + contarSuperheroesOVillanosRec(actual.getIzquierdo()) 
        + contarSuperheroesOVillanosRec(actual.getDerecho());
}


// Listar descripciones de nodos hoja de categoría “Frases icónicas”
public String listarFrasesIconicasHojas() {
    StringBuilder sb = new StringBuilder();
    listarFrasesIconicasHojasRec(raiz, sb);
    return sb.toString();
}

private void listarFrasesIconicasHojasRec(Nodo actual, StringBuilder sb) {
    if (actual != null) {
        boolean esHoja = (actual.getIzquierdo() == null && actual.getDerecho() == null);
        String categoria = actual.getTarjeta().getCategoria();
        if (esHoja && categoria.equalsIgnoreCase("Frases icónicas")) {
            sb.append(actual.getTarjeta().getDescripcion()).append("\n");
        }
        listarFrasesIconicasHojasRec(actual.getIzquierdo(), sb);
        listarFrasesIconicasHojasRec(actual.getDerecho(), sb);
    }
}

// Encontrar el nodo con el Id menor y mayor
    public Tarjeta obtenerTarjetaMenorId() {
    if (raiz == null) return null;
    Nodo actual = raiz;
    while (actual.getIzquierdo() != null) {
        actual = actual.getIzquierdo();
    }
    return actual.getTarjeta();
}

    public Tarjeta obtenerTarjetaMayorId() {
    if (raiz == null) return null;
    Nodo actual = raiz;
    while (actual.getDerecho() != null) {
        actual = actual.getDerecho();
    }
    return actual.getTarjeta();
}
    
   //metodo de dibujar START
    
    public void dibujar(Graphics2D g, int width, int height) {
    final int RADIO = 18;   
    final int V_SP  = 70;   

    if (raiz == null) { 
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 16f));
        String msg = "Árbol vacío";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(msg, (width - fm.stringWidth(msg)) / 2, height / 2);
        return;
    }

    // punto inicial y separación horizontal inicial
    int x0 = width / 2;
    int y0 = 40;
    int dx0 = Math.max(width / 4, RADIO * 3);

    dibujarNodo(g, raiz, x0, y0, dx0, V_SP, RADIO, width);
}

private void dibujarNodo(Graphics2D g, Nodo nodo, int x, int y, int dx, int vsp, int r, int width) {
    if (nodo == null) return;

    // reducción progresiva de separación horizontal
    int nextDx = Math.max(dx / 2, r * 2 + 10);

    // Dibuja primero enlaces para que queden debajo de los nodos
    Nodo izq = nodo.getIzquierdo();
    Nodo der = nodo.getDerecho();

    if (izq != null) {
        int cx = x - dx;
        int cy = y + vsp;
        cx = clamp(cx, r + 2, width - (r + 2));   // evita salirse del panel
        g.setColor(new Color(180, 180, 180));
        g.setStroke(new BasicStroke(2f));
        g.drawLine(x, y, cx, cy);
        dibujarNodo(g, izq, cx, cy, nextDx, vsp, r, width);
    }

    if (der != null) {
        int cx = x + dx;
        int cy = y + vsp;
        cx = clamp(cx, r + 2, width - (r + 2));
        g.setColor(new Color(180, 180, 180));
        g.setStroke(new BasicStroke(2f));
        g.drawLine(x, y, cx, cy);
        dibujarNodo(g, der, cx, cy, nextDx, vsp, r, width);
    }

    // Dibuja el nodo (círculo + id centrado)
    int d  = r * 2;
    int nx = x - r;
    int ny = y - r;

    g.setColor(new Color(240, 248, 255)); // relleno
    g.fillOval(nx, ny, d, d);

    g.setColor(new Color(50, 90, 160));   // borde
    g.setStroke(new BasicStroke(2f));
    g.drawOval(nx, ny, d, d);

    String texto = String.valueOf(nodo.getTarjeta().getId());
    g.setFont(g.getFont().deriveFont(Font.BOLD, 12f));
    FontMetrics fm = g.getFontMetrics();
    int tx = x - fm.stringWidth(texto) / 2;
    int ty = y + (fm.getAscent() - fm.getDescent()) / 2;
    g.setColor(Color.BLACK);
    g.drawString(texto, tx, ty);
}

// utilidad para no “salirse“ horizontalmente
private int clamp(int val, int min, int max) {
    return Math.max(min, Math.min(max, val));
}
    
    //metodo de dibujar END



}
