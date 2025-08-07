package modelo;

public class tarjeta {
    private int id;
    private String descripcion;
    private String categoria;

    // Constructor
    public tarjeta(int id, String descripcion, String categoria) {
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

    // ToString para mostrar información fácilmente
    @Override
    public String toString() {
        return "ID: " + id + ", Descripción: " + descripcion + ", Categoría: " + categoria;
    }
}