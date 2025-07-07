package modelo;

import java.time.LocalDate;

public class Venta {
    private int id;
    private int idUsuario;
    private int idLibro;
    private int cantidad;
    private LocalDate fecha;
    private double total;

    public Venta(int id, int idUsuario, int idLibro, int cantidad, LocalDate fecha, double total) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }
    // Getters y setters

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
