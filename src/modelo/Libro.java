package modelo;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private double precio;
    private int stock;
    private int idCategoria;  // <-- agregá este atributo

    public Libro() {}

    // Constructor con categoría incluida
    public Libro(int id, String titulo, String autor, double precio, int stock, int idCategoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    // Constructor sin categoría para compatibilidad
    public Libro(int id, String titulo, String autor, double precio, int stock) {
        this(id, titulo, autor, precio, stock, 0);
    }

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
}

