import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Libro {
    private String titulo;
    private List<String> autores;
    private int anioPublicacion;
    private String edicion;
    private String editorial;
    private int existencias;

    public Libro(String titulo, List<String> autores, int anioPublicacion,
                 String edicion, String editorial, int existencias) {
        if (autores == null || autores.size() > 5) {
            throw new IllegalArgumentException("Un libro puede tener hasta 5 autores.");
        }
        if (existencias < 0) {
            throw new IllegalArgumentException("Las existencias no pueden ser negativas.");
        }
        this.titulo = titulo;
        this.autores = new ArrayList<>(autores);
        this.anioPublicacion = anioPublicacion;
        this.edicion = edicion;
        this.editorial = editorial;
        this.existencias = existencias;
    }

    public String getTitulo() { return titulo; }
    public int getExistencias() { return existencias; }

    public void agregarEjemplares(int cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        existencias += cantidad;
    }

    public void retirarEjemplares(int cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
        } else if (cantidad > existencias) {
            System.out.println("No hay suficientes ejemplares en existencia.");
        } else {
            existencias -= cantidad;
        }
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\nAutores: " + String.join(", ", autores)
                + "\nAño: " + anioPublicacion + "\nEdición: " + edicion
                + "\nEditorial: " + editorial + "\nExistencias: " + existencias;
    }
}

class CatalogoLibros {
    private List<Libro> libros = new ArrayList<>();

    public boolean agregarLibro(Libro libro) {
        if (buscarLibro(libro.getTitulo()) != null) {
            System.out.println("Ya existe un libro con ese título.");
            return false;
        }
        libros.add(libro);
        return true;
    }

    public boolean eliminarLibro(String titulo) {
        Libro libro = buscarLibro(titulo);
        if (libro == null) return false;
        libros.remove(libro);
        return true;
    }

    public Libro buscarLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) return libro;
        }
        return null;
    }

    public void mostrarCatalogo() {
        if (libros.isEmpty()) {
            System.out.println("El catálogo está vacío.");
            return;
        }
        for (Libro libro : libros) {
            System.out.println("--------------------");
            System.out.println(libro);
        }
    }
}

public class Problema1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CatalogoLibros catalogo = new CatalogoLibros();
        int opcion;

        do {
            System.out.println("\n--- LIBRERÍA ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Mostrar catálogo");
            System.out.println("4. Agregar ejemplares");
            System.out.println("5. Retirar ejemplares");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    List<String> autores = new ArrayList<>();
                    System.out.print("Cantidad de autores (0 a 5): ");
                    int n = Integer.parseInt(sc.nextLine());
                    if (n < 0 || n > 5) {
                        System.out.println("Cantidad de autores inválida.");
                        break;
                    }
                    for (int i = 0; i < n; i++) {
                        System.out.print("Autor " + (i + 1) + ": ");
                        autores.add(sc.nextLine());
                    }
                    System.out.print("Año de publicación: ");
                    int anio = Integer.parseInt(sc.nextLine());
                    System.out.print("Edición: ");
                    String edicion = sc.nextLine();
                    System.out.print("Editorial: ");
                    String editorial = sc.nextLine();
                    System.out.print("Existencias iniciales: ");
                    int existencias = Integer.parseInt(sc.nextLine());
                    try {
                        if (catalogo.agregarLibro(new Libro(titulo, autores, anio,
                                edicion, editorial, existencias))) {
                            System.out.println("Libro agregado.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Título del libro a eliminar: ");
                    System.out.println(catalogo.eliminarLibro(sc.nextLine())
                            ? "Libro eliminado." : "Libro no encontrado.");
                    break;
                case 3:
                    catalogo.mostrarCatalogo();
                    break;
                case 4:
                case 5:
                    System.out.print("Título del libro: ");
                    Libro libro = catalogo.buscarLibro(sc.nextLine());
                    if (libro == null) {
                        System.out.println("Libro no encontrado.");
                        break;
                    }
                    System.out.print("Cantidad: ");
                    int cantidad = Integer.parseInt(sc.nextLine());
                    if (opcion == 4) libro.agregarEjemplares(cantidad);
                    else libro.retirarEjemplares(cantidad);
                    System.out.println("Existencias actuales: " + libro.getExistencias());
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
