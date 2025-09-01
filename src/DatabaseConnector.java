import java.sql.*;
import java.util.List;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/invoicedb";
    private static final String USER = "root";
    private static final String PASSWORD = "shifa59";

    public static int saveInvoice(String customerName, List<String> items, List<Double> prices) {
        int invoiceId = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String insertInvoice = "INSERT INTO invoices (customer_name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(insertInvoice, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, customerName);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                invoiceId = rs.getInt(1);
            }

            String insertItem = "INSERT INTO invoice_items (invoice_id, item_name, amount) VALUES (?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(insertItem);

            for (int i = 0; i < items.size(); i++) {
                itemStmt.setInt(1, invoiceId);
                itemStmt.setString(2, items.get(i));
                itemStmt.setDouble(3, prices.get(i));
                itemStmt.executeUpdate();
            }

            pstmt.close();
            itemStmt.close();
            conn.close();

            System.out.println("Invoice and items saved to database.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Make sure the MySQL connector JAR is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        }
        return invoiceId;
    }
}
