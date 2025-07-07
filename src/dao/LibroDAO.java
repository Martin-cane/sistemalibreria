package dao;

import modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    /**
     * Agrega un nuevo libro a la base de datos.
     * Recibe un objeto Libro y utiliza una consulta SQL INSERT.
     * @param libro objeto Libro con datos a insertar
     * @return true si se inserta correctamente, false si hay error
     */
    public boolean agregarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, precio, stock, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Setear los parámetros de la consulta SQL con los datos del libro
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setDouble(3, libro.getPrecio());
            ps.setInt(4, libro.getStock());
            ps.setInt(5, libro.getIdCategoria());
            // Ejecutar la inserción
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error en la inserción
        }
    }

    /**
     * Obtiene una lista con todos los libros almacenados en la base de datos.
     * Ejecuta una consulta SQL SELECT y mapea los resultados a objetos Libro.
     * @return lista de libros, puede estar vacía si no hay registros
     */
    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Iterar sobre los resultados y crear objetos Libro con los datos
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setPrecio(rs.getDouble("precio"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina un libro de la base de datos según su ID.
     * Ejecuta una consulta SQL DELETE.
     * @param id identificador del libro a eliminar
     * @return true si se elimina algún registro, false si no o si hay error
     */
    public boolean eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0; // true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // error al eliminar
        }
    }

    /**
     * Actualiza los datos de un libro existente en la base de datos.
     * Recibe un objeto Libro con el ID y los nuevos datos.
     * Ejecuta una consulta SQL UPDATE.
     * @param libro objeto Libro con datos actualizados
     * @return true si se actualizó correctamente, false si no o error
     */
    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, precio = ?, stock = ?, id_categoria = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setDouble(3, libro.getPrecio());
            ps.setInt(4, libro.getStock());
            ps.setInt(5, libro.getIdCategoria());
            ps.setInt(6, libro.getId());
            int filas = ps.executeUpdate();
            return filas > 0; // true si se actualizó alguna fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // error al actualizar
        }
    }

    /**
     * Obtiene un libro específico de la base de datos por su ID.
     * Ejecuta una consulta SQL SELECT WHERE.
      */
    public Libro obtenerLibroPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Crear y retornar el objeto Libro con los datos del resultado
                return new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getInt("id_categoria") // corregido el nombre del campo
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no encontrado o error
    }
}

