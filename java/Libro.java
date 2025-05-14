/**
 * Clase que representa un libr de la biblioteca
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String estado; // DISPONIBLE o EN PRESTAMO

    public Libro(String isbn, String titulo, String autor, String estado) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return isbn + " - " + titulo + " - " + autor + " - " + estado;
    }
}
