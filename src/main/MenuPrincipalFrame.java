package main;

import modelo.Usuario;
import dao.LibroDAO;
import dao.CategoriaDAO;
import dao.VentaDAO;
import dao.Conexion;
import modelo.Libro;

import modelo.Venta;

import javax.swing.*;
import java.awt.*;


import java.time.LocalDate;
import java.util.List;


import dao.*;
import modelo.*;





public class MenuPrincipalFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private Usuario usuario;

    public MenuPrincipalFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Menú Principal - Librería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblBienvenida = new JLabel("Bienvenido/a: " + usuario.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnListarLibros = new JButton("📚 Listar Libros");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        JButton btnAgregarLibro = new JButton("➕ Agregar Libro");
        JButton btnActualizarLibro = new JButton("✏️ Actualizar Libro");
        JButton btnRegistrarUsuario = new JButton("👤 Registrar Usuario");
        JButton btnListarVentas = new JButton("📈 Ver Ventas");
        JButton btnComprarLibro = new JButton("🛒 Comprar Libro");

        setLayout(new BorderLayout());
        add(lblBienvenida, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
            panel.add(btnAgregarLibro);
            panel.add(btnActualizarLibro);
            panel.add(btnListarLibros);
            panel.add(btnRegistrarUsuario);
            panel.add(btnListarVentas);
        } else {
            panel.add(btnListarLibros);
            panel.add(btnComprarLibro);
        }

        panel.add(btnCerrarSesion);

        btnListarLibros.addActionListener(e -> mostrarLibros());
        btnAgregarLibro.addActionListener(e -> agregarLibro());
        btnActualizarLibro.addActionListener(e -> actualizarLibro());
        btnRegistrarUsuario.addActionListener(e -> registrarUsuario());
        btnListarVentas.addActionListener(e -> listarVentas());
        btnComprarLibro.addActionListener(e -> comprarLibro());

        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void mostrarLibros() {
        LibroDAO libroDAO = new LibroDAO();
        List<Libro> libros = libroDAO.listarLibros();

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros registrados.");
            return;
        }

        String[] columnas = {"ID", "Título", "Autor", "Precio", "Stock", "Categoría"};
        String[][] datos = new String[libros.size()][6];

        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            datos[i][0] = String.valueOf(l.getId());
            datos[i][1] = l.getTitulo();
            datos[i][2] = l.getAutor();
            datos[i][3] = String.valueOf(l.getPrecio());
            datos[i][4] = String.valueOf(l.getStock());
            datos[i][5] = String.valueOf(l.getIdCategoria());
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        JFrame frame = new JFrame("Libros");
        frame.setSize(600, 300);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void agregarLibro() {
        LibroDAO libroDAO = new LibroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        List<Categoria> categorias = categoriaDAO.listarCategorias();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías disponibles.");
            return;
        }

        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();
        JComboBox<String> cbCategoria = new JComboBox<>();

        for (Categoria c : categorias) {
            cbCategoria.addItem(c.getId() + " - " + c.getNombre());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Título:"));
        panel.add(titulo);
        panel.add(new JLabel("Autor:"));
        panel.add(autor);
        panel.add(new JLabel("Precio:"));
        panel.add(precio);
        panel.add(new JLabel("Stock:"));
        panel.add(stock);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);

        int res = JOptionPane.showConfirmDialog(this, panel, "Agregar libro", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String t = titulo.getText();
                String a = autor.getText();
                double p = Double.parseDouble(precio.getText());
                int s = Integer.parseInt(stock.getText());
                int idCat = Integer.parseInt(cbCategoria.getSelectedItem().toString().split(" - ")[0]);

                Libro nuevo = new Libro(0, t, a, p, s, idCat);
                if (libroDAO.agregarLibro(nuevo)) {
                    JOptionPane.showMessageDialog(this, "Libro agregado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar libro.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    private void actualizarLibro() {
        LibroDAO libroDAO = new LibroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Libro> libros = libroDAO.listarLibros();

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros.");
            return;
        }

        String[] opciones = libros.stream()
                .map(l -> l.getId() + " - " + l.getTitulo())
                .toArray(String[]::new);

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccionar libro a actualizar:",
                "Actualizar", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == null) return;

        int idLibro = Integer.parseInt(seleccion.split(" - ")[0]);
        Libro libro = libros.stream().filter(l -> l.getId() == idLibro).findFirst().orElse(null);

        JTextField titulo = new JTextField(libro.getTitulo());
        JTextField autor = new JTextField(libro.getAutor());
        JTextField precio = new JTextField(String.valueOf(libro.getPrecio()));
        JTextField stock = new JTextField(String.valueOf(libro.getStock()));
        JComboBox<String> cbCategoria = new JComboBox<>();

        List<Categoria> categorias = categoriaDAO.listarCategorias();
        for (Categoria c : categorias) {
            cbCategoria.addItem(c.getId() + " - " + c.getNombre());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Título:"));
        panel.add(titulo);
        panel.add(new JLabel("Autor:"));
        panel.add(autor);
        panel.add(new JLabel("Precio:"));
        panel.add(precio);
        panel.add(new JLabel("Stock:"));
        panel.add(stock);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);

        int res = JOptionPane.showConfirmDialog(this, panel, "Actualizar libro", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                libro.setTitulo(titulo.getText());
                libro.setAutor(autor.getText());
                libro.setPrecio(Double.parseDouble(precio.getText()));
                libro.setStock(Integer.parseInt(stock.getText()));
                libro.setIdCategoria(Integer.parseInt(cbCategoria.getSelectedItem().toString().split(" - ")[0]));

                if (libroDAO.actualizarLibro(libro)) {
                    JOptionPane.showMessageDialog(this, "Libro actualizado.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        }
    }

    private void registrarUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        JTextField nombre = new JTextField();
        JTextField email = new JTextField();
        JTextField pass = new JTextField();
        JComboBox<String> rol = new JComboBox<>(new String[]{"ADMIN", "CLIENTE"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Contraseña:"));
        panel.add(pass);
        panel.add(new JLabel("Rol:"));
        panel.add(rol);

        int res = JOptionPane.showConfirmDialog(this, panel, "Registrar usuario", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            Usuario nuevo = new Usuario(0, nombre.getText(), email.getText(), pass.getText(), rol.getSelectedItem().toString());
            if (usuarioDAO.registrarUsuario(nuevo)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.");
            }
        }
    }

    private void listarVentas() {
        VentaDAO ventaDAO = new VentaDAO(Conexion.getConnection());
        List<Venta> ventas = ventaDAO.listarVentas();

        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ventas.");
            return;
        }

        String[] columnas = {"ID", "Usuario ID", "Libro ID", "Cantidad", "Total", "Fecha"};
        String[][] datos = new String[ventas.size()][6];

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            datos[i][0] = String.valueOf(v.getId());
            datos[i][1] = String.valueOf(v.getIdUsuario());
            datos[i][2] = String.valueOf(v.getIdLibro());
            datos[i][3] = String.valueOf(v.getCantidad());
            datos[i][4] = String.format("%.2f", v.getTotal());
            datos[i][5] = v.getFecha().toString();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scroll = new JScrollPane(tabla);
        JFrame frame = new JFrame("Ventas");
        frame.setSize(600, 300);
        frame.add(scroll);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void comprarLibro() {
        LibroDAO libroDAO = new LibroDAO();
        VentaDAO ventaDAO = new VentaDAO(Conexion.getConnection());
        List<Libro> libros = libroDAO.listarLibros();

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros disponibles.");
            return;
        }

        String[] opciones = libros.stream()
                .filter(l -> l.getStock() > 0)
                .map(l -> l.getId() + " - " + l.getTitulo() + " ($" + l.getPrecio() + ")")
                .toArray(String[]::new);

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccioná un libro:",
                "Comprar", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == null) return;

        int idLibro = Integer.parseInt(seleccion.split(" - ")[0]);
        Libro libro = libros.stream().filter(l -> l.getId() == idLibro).findFirst().orElse(null);

        String cantStr = JOptionPane.showInputDialog(this, "Cantidad a comprar (stock: " + libro.getStock() + "):");
        if (cantStr == null) return;

        try {
            int cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0 || cantidad > libro.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.");
                return;
            }

            double total = cantidad * libro.getPrecio();
            libro.setStock(libro.getStock() - cantidad);
            libroDAO.actualizarLibro(libro);

            Venta nueva = new Venta(0, usuario.getId(), libro.getId(), cantidad, LocalDate.now(), total);
            ventaDAO.registrarVenta(nueva);

            JOptionPane.showMessageDialog(this, "Compra realizada. Total: $" + total);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la compra.");
        }
    }
}
