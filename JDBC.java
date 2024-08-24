import java.sql.*;

class GFG {
    public static void main(String arg[]) {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital",
                    "root", "root");

            // mydb is database
            // mydbuser is name of database
            // mydbuser is password of database

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "select * from patients");
            int id, age;
            String name, gender;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                age = resultSet.getInt("age");
                gender = resultSet.getString("gender").trim();

                System.out.println("id : " + id);
                System.out.println("name:" + name);
                System.out.println("age:" + age);
                System.out.println("gender:" + gender);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    } // function ends
}