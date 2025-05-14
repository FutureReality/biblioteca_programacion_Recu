import java.util.*;

/**
 * Clase prinsipal con el menu de gestion
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            limpiarPantalla();
            System.out.println("Selecciona una opcion: (A)gregar, (B)uscar, (P)restar, (R)etornar, (G)uardar, (C)argar, (S)alir");
            String input = scanner.nextLine().toLowerCase();

            if (!input.matches("[a-zA-Z]+")) {
                limpiarPantalla();
                System.out.println("No se permiten ni numeros ni caracteres especiales!");
                continue;
            }

            switch (input) {
                case "a": case "agregar": agregar(scanner); break;
                case "b": case "buscar": buscar(scanner); break;
                case "p": case "prestar": prestar(scanner); break;
                case "r": case "retornar": retornar(scanner); break;
                case "g": case "guardar": limpiarPantalla(); GestorArchivos.guardar("biblioteca.csv"); pausa(); break;
                case "c": case "cargar": limpiarPantalla(); GestorArchivos.cargar("biblioteca.csv"); pausa(); break;
                case "s": case "salir": System.exit(0); break;
                default:
                    limpiarPantalla();
                    System.out.println(input + " no es una opcion valida");
                    pausa();
            }
        }
    }

    public static void agregar(Scanner scanner) {
        limpiarPantalla();
        try {
            System.out.println("Rellena los siguientes campos. <<back>> para campo anterior <<esc>> para volver al menu");
            System.out.print("ISBN (formato XXX-XXXXX-XXX): ");
            String isbn = scanner.nextLine();

            if (!isbn.matches("\\d{3}-\\d{5}-\\d{3}")) {
                limpiarPantalla();
                System.out.println("ERROR: El ISBN no sigue el formato valido");
                pausa();
                return;
            }

            if (Biblioteca.libros.containsKey(isbn)) {
                limpiarPantalla();
                System.out.println("ERROR: El ISBN ya existe");
                pausa();
                return;
            }

            System.out.print("Titulo: ");
            String titulo = scanner.nextLine();
            System.out.print("Autor: ");
            String autor = scanner.nextLine();

            Libro libro = new Libro(isbn, titulo, autor, "DISPONIBLE");
            Biblioteca.libros.put(isbn, libro);

            limpiarPantalla();
            System.out.println("Libro agregado con exito");
            pausa();
        } catch (Exception e) {
            limpiarPantalla();
            System.out.println("Se ha producido un error al agregar el libro");
            pausa();
        }
    }

    public static void buscar(Scanner scanner) {
        limpiarPantalla();
        try {
            System.out.print("Introduce titulo o parte del titulo a buscar: ");
            String termino = scanner.nextLine().toLowerCase();

            boolean encontrado = false;
            for (Libro libro : Biblioteca.libros.values()) {
                if (libro.getTitulo().toLowerCase().contains(termino)) {
                    System.out.println(libro);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontro ningun libr con ese titulo");
            }
        } catch (Exception e) {
            System.out.println("Error durante la busqueda");
        }
        pausa();
    }

    public static void prestar(Scanner scanner) {
        limpiarPantalla();
        try {
            System.out.print("Introduce el ISBN del libro que quieres prestar: ");
            String isbn = scanner.nextLine();

            if (!Biblioteca.libros.containsKey(isbn)) {
                limpiarPantalla();
                System.out.println("No existe un libro con ese ISBN");
                pausa();
                return;
            }

            Libro libro = Biblioteca.libros.get(isbn);
            if ("EN PRESTAMO".equals(libro.getEstado())) {
                limpiarPantalla();
                System.out.println("Ese libro ya esta prestado");
            } else {
                libro.setEstado("EN PRESTAMO");
                limpiarPantalla();
                System.out.println("Prestamo realizado con exito");
            }
        } catch (Exception e) {
            limpiarPantalla();
            System.out.println("Error al procesar el prestamo");
        }
        pausa();
    }

    public static void retornar(Scanner scanner) {
        limpiarPantalla();
        try {
            System.out.print("Introduce el ISBN del libro a retornar: ");
            String isbn = scanner.nextLine();

            if (!Biblioteca.libros.containsKey(isbn)) {
                limpiarPantalla();
                System.out.println("No existe un libro con ese ISBN");
                pausa();
                return;
            }

            Libro libro = Biblioteca.libros.get(isbn);
            if ("DISPONIBLE".equals(libro.getEstado())) {
                limpiarPantalla();
                System.out.println("El libro ya esta disponible");
            } else {
                libro.setEstado("DISPONIBLE");
                limpiarPantalla();
                System.out.println("Libro retornado correctamente");
            }
        } catch (Exception e) {
            limpiarPantalla();
            System.out.println("Error al retornar el libro");
        }
        pausa();
    }

    /**
     * Intenta limpiar la pantalla de la consola segun el sistema operativo
     */
    public static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la pantalla");
        }
    }

    /**
     * Espera a que el usuario pulse una tecla para continuar
     */
    public static void pausa() {
        System.out.println("\nPulsa ENTER para continuar...");
        new Scanner(System.in).nextLine();
    }
}
