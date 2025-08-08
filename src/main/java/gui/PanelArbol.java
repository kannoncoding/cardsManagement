package gui;

import java.awt.*;
import javax.swing.*;
import logica.ArbolBinarioBusqueda;

public class PanelArbol extends JPanel {
    private ArbolBinarioBusqueda arbol;

    public PanelArbol() {
        setBackground(Color.WHITE);
    }

    public void setArbol(ArbolBinarioBusqueda arbol) {
        this.arbol = arbol;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (arbol == null) {
            dibujarMensaje(g, w, h, "Árbol no asignado");
        } else {
          
            arbol.dibujar(g, w, h);
        }
        g.dispose();
    }

    private void dibujarMensaje(Graphics2D g, int w, int h, String msg) {
        g.setFont(getFont().deriveFont(Font.PLAIN, 16f));
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(msg)) / 2;
        int y = (h - fm.getHeight()) / 2 + fm.getAscent();
        g.setColor(Color.GRAY);
        g.drawString(msg, x, y);
    }
}