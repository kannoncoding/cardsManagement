// Universidad Estatal a Distancia (UNED)
// II Cuatrimestre 2025
// Estructura de Datos - Proyecto 3
// Jorge Luis Arias Melendez

package modelo;

public class Tarjeta {
    private int id;
    private String descripcion;
    private String categoria;

    // Constructor
    public Tarjeta(int id, String descripcion, String categoria) {
        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // ToString para mostrar información
    @Override
    public String toString() {
        return "ID: " + id + ", Descripción: " + descripcion + ", Categoría: " + categoria;
    }
}
