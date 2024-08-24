import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class JDBCUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

class Implementation {
    public void selectEmployee() {
        System.out.println("select employee");
        String sql = "SELECT id, name, email, country, salary FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 105);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Country: " + rs.getString("country"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void insertEmployee() {
        System.out.println("insert employee");
        String sql = "INSERT INTO employees (name, email, country, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "ram");
            pstmt.setString(2, "ram.doe@example.com");
            pstmt.setString(3, "india");
            pstmt.setDouble(4, 60000.00);
            pstmt.executeUpdate();
            System.out.println("Record created.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void updateEmployee() {
        System.out.println("update employee");
        String sql = "UPDATE employees SET name = ?, email = ?, country = ?, salary = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "gita");
            pstmt.setString(2, "gita.doe@example.com");
            pstmt.setString(3, "Canada");
            pstmt.setDouble(4, 70000.00);
            pstmt.setInt(5, 102);
            pstmt.executeUpdate();
            System.out.println("Record updated.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void deleteEmployee() {
        System.out.println("delete employee");
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 103);
            pstmt.executeUpdate();
            System.out.println("Record deleted.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public static void main(String[] args) {
        Implementation obj = new Implementation();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to ABC Limited \nPlease Enter your Choice:");
            System.out.println(
                    "1. View Employee \n2. Add New Employee \n3. Update Existing Employee \n4. Delete Existing Employee \n5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    obj.selectEmployee();
                    break;
                case 2:
                    obj.insertEmployee();
                    break;
                case 3:
                    obj.updateEmployee();
                    break;
                case 4:
                    obj.deleteEmployee();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}