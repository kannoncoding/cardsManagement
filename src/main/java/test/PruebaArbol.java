package test;

import modelo.Tarjeta;
import logica.ArbolBinarioBusqueda;

public class PruebaArbol {
    public static void main(String[] args) {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        // Inserciones
        System.out.println("Insertando tarjetas...");
        arbol.insertar(new Tarjeta(10, "Batman", "Súper héroes"));
        arbol.insertar(new Tarjeta(5, "Joker", "Súper villanos"));
        arbol.insertar(new Tarjeta(12, "Ciudad Gótica", "Paneles"));
        arbol.insertar(new Tarjeta(7, "¡Pow!", "Frases icónicas"));
        arbol.insertar(new Tarjeta(3, "Alfred", "Civiles"));

        // Probar inserción duplicada
        System.out.println("Intento insertar ID duplicado: " + arbol.insertar(new Tarjeta(5, "Joker", "Súper villanos"))); // false

        // Recorridos
        System.out.println("Preorden: " + arbol.recorridoPreorden());
        System.out.println("Inorden: " + arbol.recorridoInorden());
        System.out.println("Postorden: " + arbol.recorridoPostorden());

        // Buscar
        Tarjeta t = arbol.buscar(10);
        System.out.println("Buscar 10: " + (t != null ? t : "No existe"));

        // Eliminar (casos especiales)
        System.out.println("Eliminar nodo hoja (7): " + arbol.eliminar(7));
        System.out.println("Eliminar nodo CIVIL (3): " + arbol.eliminar(3));
        System.out.println("Eliminar nodo con solo hijo derecho (debería bloquear): " + arbol.eliminar(12));
        // Y otros casos según vayas poblando el árbol...

        // Estadísticas
        System.out.println("Cantidad de Súper héroes/villanos: " + arbol.contarSuperheroesOVillanos());
        System.out.println("Frases icónicas en hojas:\n" + arbol.listarFrasesIconicasHojas());

        // Mayor y menor ID
        System.out.println("Menor ID: " + arbol.obtenerTarjetaMenorId());
        System.out.println("Mayor ID: " + arbol.obtenerTarjetaMayorId());
    }
}
