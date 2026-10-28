import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Especialista {
    private String nombre;
    private String especialidad;
    private String cedula;
    private String telefono;
    private String email;

    public Especialista(String nombre, String especialidad, String cedula,
                        String telefono, String email) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
    }

    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " | Especialidad: " + especialidad + " | Cédula: " + cedula
                + " | Teléfono: " + telefono + " | Email: " + email;
    }
}

class Paciente {
    private String nombre;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;

    public Paciente(String nombre, String telefono, LocalDate fechaNacimiento, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " | Teléfono: " + telefono + " | Nacimiento: "
                + fechaNacimiento + " | Dirección: " + direccion;
    }
}

class Cita {
    private LocalDate fecha;
    private LocalTime hora;
    private Especialista especialista;
    private Paciente paciente;

    public Cita(LocalDate fecha, LocalTime hora, Especialista especialista, Paciente paciente) {
        this.fecha = fecha;
        this.hora = hora;
        this.especialista = especialista;
        this.paciente = paciente;
    }

    public Especialista getEspecialista() { return especialista; }
    public Paciente getPaciente() { return paciente; }

    @Override
    public String toString() {
        return fecha + " " + hora + " | Especialista: " + especialista.getNombre()
                + " | Paciente: " + paciente.getNombre();
    }
}

class Consultorio {
    private List<Especialista> especialistas = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();

    public boolean agregarEspecialista(Especialista e) {
        if (buscarEspecialista(e.getCedula()) != null) return false;
        especialistas.add(e);
        return true;
    }

    public Especialista buscarEspecialista(String cedula) {
        for (Especialista e : especialistas)
            if (e.getCedula().equalsIgnoreCase(cedula)) return e;
        return null;
    }

    public boolean eliminarEspecialista(String cedula) {
        Especialista e = buscarEspecialista(cedula);
        if (e == null) return false;
        // También se eliminan las citas del especialista.
        citas.removeIf(c -> c.getEspecialista() == e);
        especialistas.remove(e);
        return true;
    }

    public boolean agregarPaciente(Paciente p) {
        if (buscarPaciente(p.getNombre()) != null) return false;
        pacientes.add(p);
        return true;
    }

    public Paciente buscarPaciente(String nombre) {
        for (Paciente p : pacientes)
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        return null;
    }

    public boolean eliminarPaciente(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) return false;
        citas.removeIf(c -> c.getPaciente() == p);
        pacientes.remove(p);
        return true;
    }

    public boolean agendarCita(LocalDate fecha, LocalTime hora,
                               String cedula, String nombrePaciente) {
        Especialista e = buscarEspecialista(cedula);
        Paciente p = buscarPaciente(nombrePaciente);
        if (e == null || p == null) return false;
        citas.add(new Cita(fecha, hora, e, p));
        return true;
    }

    public boolean cancelarCita(int indice) {
        if (indice < 0 || indice >= citas.size()) return false;
        citas.remove(indice);
        return true;
    }

    public void mostrarEspecialistas() {
        for (Especialista e : especialistas) System.out.println(e);
        if (especialistas.isEmpty()) System.out.println("No hay especialistas.");
    }

    public void mostrarPacientes() {
        for (Paciente p : pacientes) System.out.println(p);
        if (pacientes.isEmpty()) System.out.println("No hay pacientes.");
    }

    public void mostrarCitas() {
        for (int i = 0; i < citas.size(); i++)
            System.out.println(i + ". " + citas.get(i));
        if (citas.isEmpty()) System.out.println("No hay citas.");
    }
}

public class Problema2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Consultorio consultorio = new Consultorio();
        int opcion;

        do {
            System.out.println("\n--- CONSULTORIO MÉDICO ---");
            System.out.println("1. Agregar especialista");
            System.out.println("2. Eliminar especialista por cédula");
            System.out.println("3. Mostrar especialistas");
            System.out.println("4. Agregar paciente");
            System.out.println("5. Eliminar paciente por nombre");
            System.out.println("6. Mostrar pacientes");
            System.out.println("7. Agendar cita");
            System.out.println("8. Mostrar citas");
            System.out.println("9. Cancelar cita por índice");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nombre completo: "); String ne = sc.nextLine();
                    System.out.print("Especialidad: "); String esp = sc.nextLine();
                    System.out.print("Cédula profesional: "); String ced = sc.nextLine();
                    System.out.print("Teléfono: "); String tel = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.println(consultorio.agregarEspecialista(
                            new Especialista(ne, esp, ced, tel, email))
                            ? "Especialista agregado." : "La cédula ya está registrada.");
                    break;
                case 2:
                    System.out.print("Cédula: ");
                    System.out.println(consultorio.eliminarEspecialista(sc.nextLine())
                            ? "Especialista eliminado." : "No encontrado.");
                    break;
                case 3: consultorio.mostrarEspecialistas(); break;
                case 4:
                    System.out.print("Nombre completo: "); String np = sc.nextLine();
                    System.out.print("Teléfono: "); String tp = sc.nextLine();
                    System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
                    LocalDate nacimiento = LocalDate.parse(sc.nextLine());
                    System.out.print("Dirección: "); String dir = sc.nextLine();
                    System.out.println(consultorio.agregarPaciente(
                            new Paciente(np, tp, nacimiento, dir))
                            ? "Paciente agregado." : "Ese nombre ya está registrado.");
                    break;
                case 5:
                    System.out.print("Nombre del paciente: ");
                    System.out.println(consultorio.eliminarPaciente(sc.nextLine())
                            ? "Paciente eliminado." : "No encontrado.");
                    break;
                case 6: consultorio.mostrarPacientes(); break;
                case 7:
                    System.out.print("Fecha de la cita (AAAA-MM-DD): ");
                    LocalDate fecha = LocalDate.parse(sc.nextLine());
                    System.out.print("Hora (HH:MM): ");
                    LocalTime hora = LocalTime.parse(sc.nextLine());
                    System.out.print("Cédula del especialista: "); String c = sc.nextLine();
                    System.out.print("Nombre del paciente: "); String pn = sc.nextLine();
                    System.out.println(consultorio.agendarCita(fecha, hora, c, pn)
                            ? "Cita agendada." : "Paciente o especialista no encontrado.");
                    break;
                case 8: consultorio.mostrarCitas(); break;
                case 9:
                    consultorio.mostrarCitas();
                    System.out.print("Índice de la cita a cancelar: ");
                    int indice = Integer.parseInt(sc.nextLine());
                    System.out.println(consultorio.cancelarCita(indice)
                            ? "Cita cancelada." : "Índice inválido.");
                    break;
                case 0: System.out.println("Programa finalizado."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
