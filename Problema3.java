import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Categoria {
    private String nombre;
    private double distanciaKm;
    private String genero;

    public Categoria(String nombre, double distanciaKm, String genero) {
        this.nombre = nombre;
        this.distanciaKm = distanciaKm;
        this.genero = genero;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " | Distancia: " + distanciaKm + " km | Género: " + genero;
    }
}

class Competidor {
    private String nombre;
    private final int numero;
    private String genero;
    private Categoria categoria;
    private boolean finalizo;
    private String tiempo;
    private int lugar;

    public Competidor(String nombre, int numero, String genero, Categoria categoria) {
        this.nombre = nombre;
        this.numero = numero;
        this.genero = genero;
        this.categoria = categoria;
        this.finalizo = false;
        this.tiempo = "";
        this.lugar = 0;
    }

    public int getNumero() { return numero; }
    public Categoria getCategoria() { return categoria; }

    public void registrarResultado(boolean finalizo, String tiempo, int lugar) {
        this.finalizo = finalizo;
        if (finalizo) {
            this.tiempo = tiempo;
            this.lugar = (lugar >= 1 && lugar <= 10) ? lugar : 0;
        } else {
            this.tiempo = "";
            this.lugar = 0;
        }
    }

    @Override
    public String toString() {
        String resultado = finalizo
                ? "Finalizó | Tiempo: " + tiempo
                  + (lugar > 0 ? " | Lugar: " + lugar : " | Fuera del top 10")
                : "No finalizó";
        return "Número: " + numero + " | " + nombre + " | Género: " + genero
                + " | Categoría: " + categoria.getNombre() + " | " + resultado;
    }
}

class CompetenciaTrail {
    private List<Categoria> categorias = new ArrayList<>();
    private List<Competidor> competidores = new ArrayList<>();
    private int siguienteNumero = 1;

    public boolean agregarCategoria(Categoria c) {
        if (buscarCategoria(c.getNombre()) != null) return false;
        categorias.add(c);
        return true;
    }

    public Categoria buscarCategoria(String nombre) {
        for (Categoria c : categorias)
            if (c.getNombre().equalsIgnoreCase(nombre)) return c;
        return null;
    }

    public boolean eliminarCategoria(String nombre) {
        Categoria c = buscarCategoria(nombre);
        if (c == null) return false;
        // No se elimina una categoría que todavía tenga competidores.
        for (Competidor competidor : competidores) {
            if (competidor.getCategoria() == c) {
                System.out.println("No se puede eliminar: hay competidores en esa categoría.");
                return false;
            }
        }
        categorias.remove(c);
        return true;
    }

    public boolean agregarCompetidor(String nombre, String genero, String nombreCategoria) {
        Categoria categoria = buscarCategoria(nombreCategoria);
        if (categoria == null) return false;
        competidores.add(new Competidor(nombre, siguienteNumero++, genero, categoria));
        return true;
    }

    public boolean eliminarCompetidor(int numero) {
        for (int i = 0; i < competidores.size(); i++) {
            if (competidores.get(i).getNumero() == numero) {
                competidores.remove(i);
                return true;
            }
        }
        return false;
    }

    public Competidor buscarCompetidor(int numero) {
        for (Competidor c : competidores)
            if (c.getNumero() == numero) return c;
        return null;
    }

    public void registrarResultado(int numero, boolean finalizo, String tiempo, int lugar) {
        Competidor c = buscarCompetidor(numero);
        if (c == null) {
            System.out.println("Competidor no encontrado.");
            return;
        }
        if (finalizo && (tiempo == null || tiempo.trim().isEmpty())) {
            System.out.println("Debes registrar el tiempo si terminó.");
            return;
        }
        c.registrarResultado(finalizo, tiempo, lugar);
        System.out.println("Resultado registrado.");
    }

    public void mostrarCategorias() {
        for (Categoria c : categorias) System.out.println(c);
        if (categorias.isEmpty()) System.out.println("No hay categorías.");
    }

    public void mostrarCompetidores() {
        for (Competidor c : competidores) System.out.println(c);
        if (competidores.isEmpty()) System.out.println("No hay competidores.");
    }
}

public class Problema3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CompetenciaTrail competencia = new CompetenciaTrail();
        int opcion;

        do {
            System.out.println("\n--- COMPETENCIA TRAIL ---");
            System.out.println("1. Agregar categoría");
            System.out.println("2. Eliminar categoría");
            System.out.println("3. Mostrar categorías");
            System.out.println("4. Registrar competidor");
            System.out.println("5. Eliminar competidor por número");
            System.out.println("6. Mostrar competidores");
            System.out.println("7. Registrar resultado");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de categoría: "); String nc = sc.nextLine();
                    System.out.print("Distancia en kilómetros: "); double d = Double.parseDouble(sc.nextLine());
                    System.out.print("Género de la categoría: "); String g = sc.nextLine();
                    System.out.println(competencia.agregarCategoria(new Categoria(nc, d, g))
                            ? "Categoría agregada." : "Ya existe esa categoría.");
                    break;
                case 2:
                    System.out.print("Nombre de categoría: ");
                    System.out.println(competencia.eliminarCategoria(sc.nextLine())
                            ? "Categoría eliminada." : "No se pudo eliminar o no existe.");
                    break;
                case 3: competencia.mostrarCategorias(); break;
                case 4:
                    System.out.print("Nombre completo: "); String nombre = sc.nextLine();
                    System.out.print("Género del competidor: "); String genero = sc.nextLine();
                    System.out.print("Categoría: "); String categoria = sc.nextLine();
                    if (competencia.agregarCompetidor(nombre, genero, categoria)) {
                        System.out.println("Competidor registrado. Número asignado automáticamente.");
                        competencia.mostrarCompetidores();
                    } else {
                        System.out.println("La categoría no existe; registra primero la categoría.");
                    }
                    break;
                case 5:
                    System.out.print("Número del competidor: ");
                    int numEliminar = Integer.parseInt(sc.nextLine());
                    System.out.println(competencia.eliminarCompetidor(numEliminar)
                            ? "Competidor eliminado." : "No encontrado.");
                    break;
                case 6: competencia.mostrarCompetidores(); break;
                case 7:
                    System.out.print("Número del competidor: "); int num = Integer.parseInt(sc.nextLine());
                    System.out.print("¿Finalizó? (s/n): "); boolean termino = sc.nextLine().equalsIgnoreCase("s");
                    String tiempo = "";
                    int lugar = 0;
                    if (termino) {
                        System.out.print("Tiempo realizado (por ejemplo 01:35:20): ");
                        tiempo = sc.nextLine();
                        System.out.print("Lugar obtenido (1-10; 0 si no quedó en el top 10): ");
                        lugar = Integer.parseInt(sc.nextLine());
                    }
                    competencia.registrarResultado(num, termino, tiempo, lugar);
                    break;
                case 0: System.out.println("Programa finalizado."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
