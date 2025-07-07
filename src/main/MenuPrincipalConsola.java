package main;
import java.sql.Connection;
import dao.Conexion;
import dao.VentaDAO;
import dao.LibroDAO;
import dao.CategoriaDAO;
import dao.UsuarioDAO;
import modelo.Libro;
import modelo.Categoria;
import modelo.Usuario;
import modelo.Venta;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class MenuPrincipalConsola {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibroDAO libroDAO = new LibroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogueado = null;
        new LoginFrame(); // Lanza la ventana
       
        // Ciclo login / registro
        while (usuarioLogueado == null) {
            System.out.println("=== BIENVENIDO A LA LIBRERÍA ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.print("Seleccione opción: ");

            String opcionLogin = sc.nextLine();

            if (opcionLogin.equals("1")) {
                // LOGIN
                System.out.print("Email: ");
                String emailLogin = sc.nextLine();

                System.out.print("Contraseña: ");
                String passLogin = sc.nextLine();

                System.out.println("Intentando login...");
                usuarioLogueado = usuarioDAO.login(emailLogin, passLogin);
                System.out.println("Login terminado.");

                if (usuarioLogueado == null) {
                    System.out.println("Email o contraseña incorrectos. Intente de nuevo."); 
                } else {
                    break; // Salir del ciclo login
                }
            }
                else if (opcionLogin.equals("2")) {
                    // REGISTRARSE
                    System.out.println("--- Registrar nuevo usuario ---");

                    // Pedir nombre
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    // Validar nombre no vacío
                    while (nombre.trim().isEmpty()) {
                        System.out.println("El nombre no puede estar vacío. Intente de nuevo.");
                        System.out.print("Nombre: ");
                        nombre = sc.nextLine();
                    }

                    // Pedir y validar email
                    String email;
                    do {
                        System.out.print("Email: ");
                        email = sc.nextLine();

                        if (!esEmailValido(email)) {
                            System.out.println("Email con formato inválido. Intente de nuevo.");
                            continue;
                        }
                       
                        if (usuarioDAO.existeEmail(email)) {
                            System.out.println("El email ya está registrado. Intente con otro.");
                            continue;
                        }

                        break; // Email válido y no registrado
                    } while (true);

                    // Pedir contraseña
                    System.out.print("Contraseña: ");
                    String contraseña = sc.nextLine();

                    // Validar contraseña no vacía 
                    while (contraseña.trim().isEmpty()) {
                        System.out.println("La contraseña no puede estar vacía. Intente de nuevo.");
                        System.out.print("Contraseña: ");
                        contraseña = sc.nextLine();
                    }

                    // Pedir rol y validar que sea ADMIN o CLIENTE
                    String rol = "";
                    do {
                        System.out.print("Rol (ADMIN/CLIENTE): ");
                        rol = sc.nextLine().toUpperCase();
                        if (!rol.equals("ADMIN") && !rol.equals("CLIENTE")) {
                            System.out.println("Rol inválido. Ingrese ADMIN o CLIENTE.");
                        }
                    } while (!rol.equals("ADMIN") && !rol.equals("CLIENTE"));

                    // Crear usuario
                    Usuario nuevoUsuario = new Usuario(0, nombre, email, contraseña, rol);

                    boolean exito = usuarioDAO.registrarUsuario(nuevoUsuario);

                    if (exito) {
                        System.out.println("Usuario registrado con éxito. Iniciando sesión automáticamente...");
                        usuarioLogueado = nuevoUsuario; // Iniciar sesión automáticamente
                    } else {
                        System.out.println("Error al registrar el usuario.");
                    }
                }
        
            
            
        // Bienvenida tras login
        System.out.println("Bienvenido " + usuarioLogueado.getNombre() + " - Rol: " + usuarioLogueado.getRol());

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ LIBRERÍA ---");

            if (usuarioLogueado.getRol().equalsIgnoreCase("ADMIN")) {
                System.out.println("1. Agregar libro");
                System.out.println("2. Actualizar libro");
                System.out.println("3. Listar libros");
                System.out.println("4. Registrar nuevo usuario");
                System.out.println("5. Listar ventas");
                System.out.println("6. Salir");
                System.out.print("Seleccione opción: ");
            } else {
                System.out.println("1. Listar libros");
                System.out.println("2. Comprar libro");
                System.out.println("3. Salir");
                System.out.print("Seleccione opción: ");
            }

            int opcion = 0;
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar buffer
            } else {
                System.out.println("Error: Debe ingresar un número.");
                sc.nextLine(); // limpiar buffer inválido
                continue;
            }

            if (usuarioLogueado.getRol().equalsIgnoreCase("ADMIN")) {
                switch (opcion) {
                    case 1:
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();

                        System.out.print("Autor: ");
                        String autor = sc.nextLine();

                        double precio;
                        do {
                            System.out.print("Precio (no negativo): ");
                            while (!sc.hasNextDouble()) {
                                System.out.println("Error: Debe ingresar un número decimal.");
                                sc.next();
                            }
                            precio = sc.nextDouble();
                            if (precio < 0) {
                                System.out.println("Error: El precio no puede ser negativo.");
                            }
                        } while (precio < 0);
                        sc.nextLine();

                        int stock;
                        do {
                            System.out.print("Stock (no negativo): ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Error: Debe ingresar un número entero.");
                                sc.next();
                            }
                            stock = sc.nextInt();
                            if (stock < 0) {
                                System.out.println("Error: El stock no puede ser negativo.");
                            }
                        } while (stock < 0);
                        sc.nextLine();

                        List<Categoria> categorias = categoriaDAO.listarCategorias();
                        if (categorias.isEmpty()) {
                            System.out.println("No hay categorías disponibles. Agrega alguna antes.");
                            break;
                        }
                        System.out.println("Seleccione categoría:");
                        for (Categoria c : categorias) {
                            System.out.printf("%d - %s%n", c.getId(), c.getNombre());
                        }

                        int idCategoria = -1;
                        boolean categoriaValida;
                        do {
                            System.out.print("ID categoría: ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Error: Debe ingresar un número entero.");
                                sc.next();
                            }
                            int idCatTemp = sc.nextInt();
                            final int idCatFinal = idCatTemp;
                            categoriaValida = categorias.stream().anyMatch(c -> c.getId() == idCatFinal);
                            if (!categoriaValida) {
                                System.out.println("Error: Categoría inválida. Intente de nuevo.");
                            } else {
                                idCategoria = idCatTemp;
                            }
                        } while (!categoriaValida);
                        sc.nextLine();

                        boolean exitoAgregar = libroDAO.agregarLibro(new Libro(0, titulo, autor, precio, stock, idCategoria));
                        if (exitoAgregar) {
                            System.out.println("Libro agregado con éxito.");
                        } else {
                            System.out.println("Error al agregar el libro.");
                        }
                        break;

                    case 2:
                        System.out.print("Ingrese ID del libro a actualizar: ");
                        int idLibroActualizar = sc.nextInt();
                        sc.nextLine();

                        List<Libro> listaLibros = libroDAO.listarLibros();
                        Libro libroActualizar = null;
                        for (Libro l : listaLibros) {
                            if (l.getId() == idLibroActualizar) {
                                libroActualizar = l;
                                break;
                            }
                        }
                        if (libroActualizar == null) {
                            System.out.println("Libro no encontrado.");
                            break;
                        }

                        System.out.print("Nuevo título (actual: " + libroActualizar.getTitulo() + "): ");
                        String nuevoTitulo = sc.nextLine();

                        System.out.print("Nuevo autor (actual: " + libroActualizar.getAutor() + "): ");
                        String nuevoAutor = sc.nextLine();

                        double nuevoPrecio;
                        do {
                            System.out.print("Nuevo precio (actual: " + libroActualizar.getPrecio() + "): ");
                            while (!sc.hasNextDouble()) {
                                System.out.println("Error: Debe ingresar un número decimal.");
                                sc.next();
                            }
                            nuevoPrecio = sc.nextDouble();
                            if (nuevoPrecio < 0) {
                                System.out.println("Error: El precio no puede ser negativo.");
                            }
                        } while (nuevoPrecio < 0);
                        sc.nextLine();

                        int nuevoStock;
                        do {
                            System.out.print("Nuevo stock (actual: " + libroActualizar.getStock() + "): ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Error: Debe ingresar un número entero.");
                                sc.next();
                            }
                            nuevoStock = sc.nextInt();
                            if (nuevoStock < 0) {
                                System.out.println("Error: El stock no puede ser negativo.");
                            }
                        } while (nuevoStock < 0);
                        sc.nextLine();

                        categorias = categoriaDAO.listarCategorias();
                        System.out.println("Seleccione nueva categoría:");
                        for (Categoria c : categorias) {
                            System.out.printf("%d - %s%n", c.getId(), c.getNombre());
                        }

                        idCategoria = -1;
                        do {
                            System.out.print("ID categoría: ");
                            while (!sc.hasNextInt()) {
                                System.out.println("Error: Debe ingresar un número entero.");
                                sc.next();
                            }
                            int idCatTemp = sc.nextInt();
                            final int idCatFinal = idCatTemp;
                            categoriaValida = categorias.stream().anyMatch(c -> c.getId() == idCatFinal);
                            if (!categoriaValida) {
                                System.out.println("Error: Categoría inválida. Intente de nuevo.");
                            } else {
                                idCategoria = idCatTemp;
                            }
                        } while (!categoriaValida);
                        sc.nextLine();

                        libroActualizar.setTitulo(nuevoTitulo);
                        libroActualizar.setAutor(nuevoAutor);
                        libroActualizar.setPrecio(nuevoPrecio);
                        libroActualizar.setStock(nuevoStock);
                        libroActualizar.setIdCategoria(idCategoria);

                        boolean exitoActualizar = libroDAO.actualizarLibro(libroActualizar);
                        if (exitoActualizar) {
                            System.out.println("Libro actualizado con éxito.");
                        } else {
                            System.out.println("Error al actualizar el libro.");
                        }
                        break;

                    case 3:
                        List<Libro> libros = libroDAO.listarLibros();
                        if (libros.isEmpty()) {
                            System.out.println("No hay libros registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE LIBROS ---");
                            for (Libro libro : libros) {
                                System.out.printf("ID: %d | Título: %s | Autor: %s | Precio: %.2f | Stock: %d | Categoria ID: %d%n",
                                        libro.getId(), libro.getTitulo(), libro.getAutor(),
                                        libro.getPrecio(), libro.getStock(), libro.getIdCategoria());
                            }
                        }
                        break;

                    case 4:
                        System.out.println("--- Registrar nuevo usuario ---");
                        System.out.print("Nombre de usuario: ");
                        String nuevoNombre = sc.nextLine();

                        System.out.print("Email: ");
                        String nuevoEmail = sc.nextLine();

                        System.out.print("Contraseña: ");
                        String nuevaContraseña = sc.nextLine();

                        String nuevoRol = "";
                        do {
                            System.out.print("Rol (ADMIN/CLIENTE): ");
                            nuevoRol = sc.nextLine().toUpperCase();
                            if (!nuevoRol.equals("ADMIN") && !nuevoRol.equals("CLIENTE")) {
                                System.out.println("Rol inválido. Ingrese ADMIN o CLIENTE.");
                            }
                        } while (!nuevoRol.equals("ADMIN") && !nuevoRol.equals("CLIENTE"));

                        boolean exito = usuarioDAO.registrarUsuario(new Usuario(0, nuevoNombre, nuevoEmail, nuevaContraseña, nuevoRol));

                        if (exito) {
                            System.out.println("Usuario registrado con éxito.");
                        } else {
                            System.out.println("Error al registrar el usuario.");
                        }
                        break;

                    case 5:
                        Connection connVentas = Conexion.getConnection();
                        VentaDAO ventaDAO = new VentaDAO(connVentas);
                        List<Venta> ventas = ventaDAO.listarVentas();

                        if (ventas.isEmpty()) {
                            System.out.println("No hay ventas registradas.");
                        } else {
                            System.out.println("\n--- LISTADO DE VENTAS ---");
                            for (Venta venta : ventas) {
                                System.out.printf("ID: %d | Usuario ID: %d | Libro ID: %d | Cantidad: %d | Total: $%.2f | Fecha: %s%n",
                                        venta.getId(), venta.getIdUsuario(), venta.getIdLibro(),
                                        venta.getCantidad(), venta.getTotal(), venta.getFecha());
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Chau. ¡Gracias por usar el sistema!");
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción inválida, intenta de nuevo.");
                        break;
                }
            } else {
                // MENÚ CLIENTE
                switch (opcion) {
                    case 1:
                        List<Libro> librosCliente = libroDAO.listarLibros();
                        if (librosCliente.isEmpty()) {
                            System.out.println("No hay libros registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE LIBROS ---");
                            for (Libro libro : librosCliente) {
                                System.out.printf("ID: %d | Título: %s | Autor: %s | Precio: %.2f | Stock: %d | Categoria ID: %d%n",
                                        libro.getId(), libro.getTitulo(), libro.getAutor(),
                                        libro.getPrecio(), libro.getStock(), libro.getIdCategoria());
                            }
                        }
                        break;

                    case 2:
                        System.out.print("Ingrese ID del libro que desea comprar: ");
                        int idLibroComprar = sc.nextInt();
                        sc.nextLine();

                        Libro libroCompra = null;
                        List<Libro> libros = libroDAO.listarLibros();
                        for (Libro l : libros) {
                            if (l.getId() == idLibroComprar) {
                                libroCompra = l;
                                break;
                            }
                        }

                        if (libroCompra == null) {
                            System.out.println("Libro no encontrado.");
                            break;
                        }

                        if (libroCompra.getStock() == 0) {
                            System.out.println("Lo sentimos, este libro está agotado.");
                            break;
                        }

                        int cantidadComprar;
                        do {
                            System.out.printf("Ingrese cantidad a comprar (stock disponible: %d): ", libroCompra.getStock());
                            while (!sc.hasNextInt()) {
                                System.out.println("Debe ingresar un número entero.");
                                sc.next();
                            }
                            cantidadComprar = sc.nextInt();
                            sc.nextLine();
                            if (cantidadComprar <= 0) {
                                System.out.println("La cantidad debe ser mayor que 0.");
                            } else if (cantidadComprar > libroCompra.getStock()) {
                                System.out.println("No hay stock suficiente para esa cantidad.");
                            }
                        } while (cantidadComprar <= 0 || cantidadComprar > libroCompra.getStock());

                        double total = libroCompra.getPrecio() * cantidadComprar;

                        libroCompra.setStock(libroCompra.getStock() - cantidadComprar);
                        boolean exitoCompra = libroDAO.actualizarLibro(libroCompra);

                        Connection conn = Conexion.getConnection();  
                        VentaDAO ventaDAOCliente = new VentaDAO(conn); 
                        Venta nuevaVenta = new Venta(0, usuarioLogueado.getId(), libroCompra.getId(), cantidadComprar, LocalDate.now(), total);

                        boolean exitoVenta = ventaDAOCliente.registrarVenta(nuevaVenta);

                        if (exitoCompra && exitoVenta) {
                            System.out.printf("Compra exitosa: %d ejemplares de '%s'. Total: $%.2f%n",
                                    cantidadComprar, libroCompra.getTitulo(), total);
                        } else {
                            System.out.println("Error al procesar la compra o registrar la venta.");
                        }
                        break;

                    case 3:
                        salir = true;
                        System.out.println("¡Chau!");
                        break;

                    default:
                        System.out.println("Opción inválida, intenta de nuevo.");
                        break;
                }
            }
        }

        sc.close();
    }
  }
 public static boolean esEmailValido(String email) {
    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return email.matches(regex);
  }
}
