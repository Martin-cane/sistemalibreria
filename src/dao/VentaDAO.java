package dao;

import modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





import java.sql.Date;

public class VentaDAO {

    private Connection conn;

    public VentaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (usuario_id, libro_id, cantidad, total, fecha) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venta.getIdUsuario());  // ⚠️ Debe existir en tabla `usuarios`
            ps.setInt(2, venta.getIdLibro());    // ⚠️ Debe existir en tabla `libros`
            ps.setInt(3, venta.getCantidad());
            ps.setDouble(4, venta.getTotal());
            ps.setDate(5, Date.valueOf(venta.getFecha())); // LocalDate → SQL Date

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Venta registrada correctamente.");
                return true;
            } else {
                System.out.println("⚠️ No se pudo registrar la venta.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar la venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


 // Listar todas las ventas
    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("libro_id"),
                    rs.getInt("cantidad"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getDouble("total")
                );
                ventas.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
}