package JDBC;

import java.sql.*;

public class MyExecuteUpdate {

    String dbURL = "jdbc:mysql://localhost:3306/music";
    String username = "root";
    String password = "password";

    public static void main(String[] args) {
        try{
            var obj = new MyExecuteUpdate();
            System.out.println(obj.insertRow(new Product("pf03", "Insert Test", 10000)));
            obj.select("pf03");
            System.out.println(obj.updateRow(new Product("pf03", "Update Test", 1000.0)));
            obj.select("pf03");
            System.out.println(obj.deleteRow("pf03"));

        } catch (SQLException e){
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }

    public void select(String productCode) throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        Statement statement = connection.createStatement();

        ResultSet products = statement.executeQuery(
                    "SELECT * FROM Product " +
                        "WHERE ProductCode = '" + productCode + "'");
        while (products.next()) {
            //create a product object from the products result set
            Product product = new Product(products.getString(2),
                    products.getString(3),
                    products.getDouble(4));
            System.out.println(product);
        }
    }

    public int insertRow(Product product) throws SQLException {
        String query = "INSERT INTO Product (ProductCode, ProductDescription, ProductPrice) " +
                "VALUES ('" + product.getCode() + "', " +
                "'" + product.getDescription() + "', " +
                "'" + product.getPrice() + "')";

        Connection connection = DriverManager.getConnection(dbURL, username, password);
        Statement statement = connection.createStatement();
        int rowCount = statement.executeUpdate(query);
        return rowCount;
    }

    public int updateRow(Product product) throws SQLException {
        String query = "UPDATE Product SET " +
                "ProductCode = '" + product.getCode() + "', " +
                "ProductDescription = '" + product.getDescription() + "', " +
                "ProductPrice = '" + product.getPrice() + "' " +
                "WHERE ProductCode = '" + product.getCode() + "'";
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        Statement statement = connection.createStatement();
        int rowCount = statement.executeUpdate(query);

        return rowCount;
    }

    public int deleteRow(String productCode) throws SQLException {
        String query = "DELETE FROM Product " +
                "WHERE ProductCode = '" + productCode + "'";

        Connection connection = DriverManager.getConnection(dbURL, username, password);
        Statement statement = connection.createStatement();
        int rowCount = statement.executeUpdate(query);
        return rowCount;
    }
}
