package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemalibreria";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";

    // MÉTODO ESTÁTICO para usar en otras clases (LibroDAO, etc.)
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método main opcional para probar la conexión manualmente
    public static void main(String[] args) {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    System.out.println("🔒 Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

