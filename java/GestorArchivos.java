import java.io.*;
import java.util.*;

/**
 * Clase para gestionar la lectura y escritura de archivos
 */
public class GestorArchivos {

    /**
     * Guarda los datos de la biblioteca en un archivo CSV
     */
    public static void guardar(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Libro libro : Biblioteca.libros.values()) {
                writer.println(libro.getIsbn() + "," + libro.getTitulo() + "," + libro.getAutor() + "," + libro.getEstado());
            }
            System.out.println("Datos guardados correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos en el archivo");
        }
    }

    /**
     * Carga los datos desde un archivo CSV a la biblioteca
     */
    public static void cargar(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No se encontro el archivo");
            return;
        }

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    Libro libro = new Libro(partes[0], partes[1], partes[2], partes[3]);
                    Biblioteca.libros.put(partes[0], libro);
                }
            }
            System.out.println("Datos cargados correctamente");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos desde el archivo");
        }
    }
}
