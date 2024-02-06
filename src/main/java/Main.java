import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/libreria";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Libros libros = new Libros(connection);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Bienvenido a la aplicación de gestión de libros");

            // Verificar y crear la columna 'precio' si no existe
            libros.crearColumnaPrecio();

            while (true) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Añadir libro");
                System.out.println("2. Borrar libro");
                System.out.println("3. Ver catálogo");
                System.out.println("4. Actualizar copias");
                System.out.println("5. Actualizar precio por página");
                System.out.println("6. Actualizar precio máximo para dos libros");
                System.out.println("7. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese los datos del libro:");
                        int isbn = 0;
                        boolean entradaCorrecta = false;
                        do {
                            try {
                                System.out.print("ISBN: ");
                                isbn = scanner.nextInt();
                                scanner.nextLine(); // Consumir el salto de línea
                                entradaCorrecta = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el ISBN.");
                                scanner.nextLine();
                            }

                        } while (!entradaCorrecta);


                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Editorial: ");
                        String editorial = scanner.nextLine();
                        int paginas = 0;
                        boolean entradaPaginasCorrecta = false;
                        do {
                            try {
                                System.out.print("Páginas: ");
                                paginas = scanner.nextInt();
                                entradaPaginasCorrecta = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para las páginas.");
                                scanner.nextLine();
                            }
                        } while (!entradaPaginasCorrecta);
                        int copias = 0;
                        boolean entradaCopiasCorrecta = false;
                        do {
                            try {
                                System.out.print("Copias: ");
                                copias = scanner.nextInt();
                                entradaCopiasCorrecta = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para las copias.");
                                scanner.nextLine();
                            }
                        } while (!entradaCopiasCorrecta);
                        double precio = 0;
                        boolean entradaPrecioCorrecta = false;
                        do {
                            try {
                                System.out.print("Precio: ");
                                precio = scanner.nextDouble();
                                entradaPrecioCorrecta = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número double para el precio.");
                                scanner.nextLine();
                            }
                        } while (!entradaPrecioCorrecta);

                        System.out.print("Precio: ");
                        //double precio = scanner.nextDouble();
                        libros.anadirLibro(isbn, titulo, autor, editorial, paginas, copias, precio);
                        break;
                    case 2:
                        int isbnBorrar = 0;
                        boolean entradaCorrectaISBNBorrar = false;
                        do {
                            try {
                                System.out.print("Ingrese el ISBN del libro a borrar: ");
                                isbnBorrar = scanner.nextInt();
                                scanner.nextLine(); // Consumir el salto de línea
                                entradaCorrectaISBNBorrar = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el ISBN.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaISBNBorrar);
                        libros.borraLibro(isbnBorrar);
                        break;
                    case 3:
                        System.out.println("Catálogo de libros:");
                        libros.verCatalogo();
                        break;
                    case 4:
                        int isbnActualizar = 0;
                        int nuevasCopias = 0;
                        boolean entradaCorrectaISBNActualizar = false;
                        boolean entradaCorrectaNuevasCopias = false;
                        do {
                            try {
                                System.out.print("Ingrese el ISBN del libro a actualizar: ");
                                isbnActualizar = scanner.nextInt();
                                entradaCorrectaISBNActualizar = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el ISBN del libro a actualizar.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaISBNActualizar);
                        do {
                            try {
                                System.out.print("Ingrese el nuevo número de copias: ");
                                nuevasCopias = scanner.nextInt();
                                entradaCorrectaNuevasCopias = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el nuevo número de copias.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaNuevasCopias);

                        libros.actualizarCopias(isbnActualizar, nuevasCopias);
                        break;
                    case 5:
                        double precioPorPagina = 0;
                        boolean entradaCorrectaPrecioPagina = false;
                        do {
                            try {
                                System.out.print("Ingrese el precio por página: ");
                                precioPorPagina = scanner.nextDouble();
                                scanner.nextLine(); // Limpiar el buffer del scanner
                                entradaCorrectaPrecioPagina = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número decimal para el precio por página.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaPrecioPagina);

                        // Actualizar los precios basados en el precio por página
                        libros.actualizarPrecioPorPagina(precioPorPagina);

                        // Mostrar el mensaje de éxito
                        System.out.println("¡Los precios se han actualizado correctamente!");
                        break;
                    case 6:
                        int isbn1 = 0;
                        int isbn2 = 0;
                        boolean entradaCorrectaISBN1 = false;
                        boolean entradaCorrectaISBN2 = false;
                        do {
                            try {
                                System.out.print("Ingrese el ISBN del primer libro: ");
                                isbn1 = scanner.nextInt();
                                scanner.nextLine(); // Consumir el salto de línea
                                entradaCorrectaISBN1 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el ISBN del primer libro.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaISBN1);
                        do {
                            try {
                                System.out.print("Ingrese el ISBN del segundo libro: ");
                                isbn2 = scanner.nextInt();
                                scanner.nextLine(); // Consumir el salto de línea
                                entradaCorrectaISBN2 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número entero para el ISBN del segundo libro.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaISBN2);

                        System.out.print("Ingrese el precio por página: ");
                        double precioPorPaginaTwo = 0;
                        boolean entradaCorrectaPrecioPaginaTwo = false;
                        do {
                            try {
                                precioPorPaginaTwo = scanner.nextDouble();
                                scanner.nextLine(); // Limpiar el buffer del scanner
                                entradaCorrectaPrecioPaginaTwo = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Por favor, ingrese un número decimal para el precio por página.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        } while (!entradaCorrectaPrecioPaginaTwo);

                        // Actualizar el precio máximo para ambos libros
                        libros.actualizarPrecioMaximo(isbn1, isbn2, precioPorPaginaTwo);

                        // Mostrar el mensaje de éxito
                        System.out.println("¡Los precios máximos se han actualizado correctamente!");
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
