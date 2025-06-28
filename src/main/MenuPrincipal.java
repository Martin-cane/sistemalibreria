package main;

import java.util.Scanner;

public class MenuPrincipal {

    static Scanner sc = new Scanner(System.in); // Reutilizable

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN ===");
            System.out.println("1. Gestión de Libros");
            System.out.println("2. Registrar Venta");
            System.out.println("3. Ver Reportes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    menuLibros();
                    break;
                case 2:
                    System.out.println("→ Registro de venta (no implementado)");
                    break;
                case 3:
                    System.out.println("→ Reportes (no implementado)");
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);

        // No cierro sc.close(); porque cerraría System.in
    }

    public static void menuLibros() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE LIBROS ===");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Editar Libro");
            System.out.println("3. Eliminar Libro");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("→ Agregar libro (no implementado)");
                    break;
                case 2:
                    System.out.println("→ Editar libro (no implementado)");
                    break;
                case 3:
                    System.out.println("→ Eliminar libro (no implementado)");
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }
}

