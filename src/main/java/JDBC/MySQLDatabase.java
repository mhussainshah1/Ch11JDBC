package JDBC;

import java.sql.*;

public class MySQLDatabase {
    public static void main(String[] args) {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/murach";
            String username = "root";
            String password = "password";

            Connection connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println(connection);
            Statement statement = connection.createStatement();
            ResultSet userIDResult = statement.executeQuery(
                    """
                               SELECT userID
                               FROM user
                               WHERE Email = 'jsmith@gmail.com'
                            """
            );
            boolean userIDExists = userIDResult.next();

            dbURL = "jdbc:mysql://localhost:3306/music";
            connection = DriverManager.getConnection(dbURL, username, password);
            statement = connection.createStatement();
            ResultSet products = statement.executeQuery("SELECT * FROM Product");
            while (products.next()) {
                String code = products.getString("ProductCode");//products.getString(2);
                String description = products.getString("ProductDescription");//products.getString(3);
                double price = products.getDouble("ProductPrice");//products.getDouble(4);
                System.out.println("code = " + code + ", description = " + description + ", price = " + price);
            }

            products = statement.executeQuery("SELECT * FROM Product");
            while (products.next()) {
                //create a product object from the products result set
                Product product = new Product(products.getString(2),
                        products.getString(3),
                        products.getDouble(4));
                System.out.println(product);
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }
}