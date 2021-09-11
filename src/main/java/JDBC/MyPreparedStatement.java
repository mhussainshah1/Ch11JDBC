package JDBC;

import java.sql.*;

public class MyPreparedStatement {
    String dbURL = "jdbc:mysql://localhost:3306/music";
    String username = "root";
    String password = "password";

    public static void main(String[] args) {
        try {
            var obj = new MyPreparedStatement();
            System.out.println(obj.insertRow(new Product("pf03", "Insert Test", 10000)));
            obj.select("pf03");
            System.out.println(obj.updateRow(new Product("pf03", "Update Test", 1000.0)));
            obj.select("pf03");
            System.out.println(obj.deleteRow("pf03"));
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }

    public void select(String productCode) throws SQLException {
        String preparedSQL = """
                SELECT ProductCode, ProductDescription, ProductPrice
                FROM Product
                WHERE ProductCode = ?
                """;
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        PreparedStatement ps = connection.prepareStatement(preparedSQL);
        ps.setString(1, productCode);
        ResultSet products = ps.executeQuery();
        while (products.next()) {
            //create a product object from the products result set
            Product product = new Product(products.getString(1),
                    products.getString(2),
                    products.getDouble(3));
            System.out.println(product);
        }
    }

    public int updateRow(Product product) throws SQLException {
        String preparedSQL = """
                UPDATE Product
                SET ProductCode = ?, ProductDescription = ?, ProductPrice = ?
                WHERE ProductCode = ?
                """;
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        PreparedStatement ps = connection.prepareStatement(preparedSQL);
        ps.setString(1, product.getCode());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setString(4,product.getCode());
        return ps.executeUpdate();
    }

    public int insertRow(Product product) throws SQLException {
        String preparedQuery = """
                INSERT INTO Product (ProductCode, ProductDescription, ProductPrice)
                VALUES (?, ?, ?)
                """;
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        PreparedStatement ps = connection.prepareStatement(preparedQuery);
        ps.setString(1, product.getCode());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        return ps.executeUpdate();
    }

    public int deleteRow(String productCode) throws SQLException {
        String preparedQuery = """
                DELETE FROM Product
                WHERE ProductCode = ?
                """;
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        PreparedStatement ps = connection.prepareStatement(preparedQuery);
        ps.setString(1, productCode);
        return ps.executeUpdate();
    }
}