import java.sql.*;

public class Libros {
    private Connection connection;
    private PreparedStatement statement;

    public Libros(Connection connection) {
        this.connection = connection;
    }

    public void crearColumnaPrecio() throws SQLException {
        // Verificar si la columna 'precio' ya existe en la tabla 'libros'
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, "libros", "precio");

        if (!resultSet.next()) {
            // La columna 'precio' no existe, añadirla a la tabla 'libros'
            String query = "ALTER TABLE libros ADD COLUMN precio DOUBLE";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Se ha añadido la columna 'precio' a la tabla 'libros'");
        } else {
            System.out.println("La columna 'precio' ya existe en la tabla 'libros'");
        }
    }

    public void anadirLibro(int isbn, String titulo, String autor, String editorial, int paginas, int copias, double precio) throws SQLException {
        String query = "INSERT INTO libros VALUES (?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, isbn);
        statement.setString(2, titulo);
        statement.setString(3, autor);
        statement.setString(4, editorial);
        statement.setInt(5, paginas);
        statement.setInt(6, copias);
        statement.setDouble(7, precio);
        statement.executeUpdate();
    }

    public void borraLibro(int isbn) throws SQLException {
        String query = "DELETE FROM libros WHERE isbn = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, isbn);
        statement.executeUpdate();
    }

    public void verCatalogo() throws SQLException {
        String query = "SELECT * FROM libros ORDER BY titulo ASC";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("isbn") + " | " +
                    resultSet.getString("titulo") + " | " +
                    resultSet.getString("autor") + " | " +
                    resultSet.getString("editorial") + " | " +
                    resultSet.getInt("paginas") + " | " +
                    resultSet.getInt("copias") + " | " +
                    resultSet.getDouble("precio"));
        }
    }

    public void actualizarCopias(int isbn, int nuevasCopias) throws SQLException {
        String query = "UPDATE libros SET copias = ? WHERE isbn = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, nuevasCopias);
        statement.setInt(2, isbn);
        statement.executeUpdate();
    }

    public void actualizarPrecioPorPagina(double precioPorPagina) throws SQLException {
        String query = "SELECT isbn, paginas FROM libros";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int isbn = resultSet.getInt("isbn");
            int paginas = resultSet.getInt("paginas");
            double nuevoPrecio = paginas * precioPorPagina;
            actualizarPrecio(isbn, nuevoPrecio);
        }
    }
    public void actualizarPrecioMaximo(int isbn1, int isbn2, double precioPorPagina) throws SQLException {
        double precioLibro1 = calcularPrecio(isbn1, precioPorPagina);
        double precioLibro2 = calcularPrecio(isbn2, precioPorPagina);

        double precioMaximo = Math.max(precioLibro1, precioLibro2);

        actualizarPrecio(isbn1, precioMaximo);
        actualizarPrecio(isbn2, precioMaximo);
    }

    private double calcularPrecio(int isbn, double precioPorPagina) throws SQLException {
        String query = "SELECT paginas FROM libros WHERE isbn = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, isbn);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int paginas = resultSet.getInt("paginas");
            return paginas * precioPorPagina;
        }
        return 0.0;
    }

    private void actualizarPrecio(int isbn, double nuevoPrecio) throws SQLException {
        String query = "UPDATE libros SET precio = ? WHERE isbn = ?";
        statement = connection.prepareStatement(query);
        statement.setDouble(1, nuevoPrecio);
        statement.setInt(2, isbn);
        statement.executeUpdate();
    }
}
