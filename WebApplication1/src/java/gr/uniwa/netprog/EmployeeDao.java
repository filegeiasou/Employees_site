package gr.uniwa.netprog;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fileg
 */
public class EmployeeDao {

    static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    static final String SQLITE_SUB = "jdbc:sqlite:";
    static final String DB_SERVER = "C:\\Users\\fileg\\OneDrive - University of West Attica\\"; //use your path here
    static final String DB_NAME = "company_db";
    static final String DB_URL = SQLITE_SUB + DB_SERVER + DB_NAME;

    private static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName(SQLITE_DRIVER);
            conn = DriverManager.getConnection(DB_URL);

            if (!tableExists(conn, "employees")) {
                createTable(conn);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }

        return conn;
    }

    private static void createTable(Connection conn) {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS employees ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "password TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "country TEXT NOT NULL, "
                + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP);";

        try {
            Statement st = conn.createStatement();
            st.execute(sqlQuery); // create a new table
            System.out.println("The table has been created.");
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private static boolean tableExists(Connection conn, String tableName) {
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            while (rs.next()) {
                return tableName.equals(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static int save(Employee employee) {
        int status = 0;
        String sql = "INSERT INTO employees(name, password, email, country) VALUES (?, ?, ?, ?)";
        Connection conn = EmployeeDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getCountry());
            status = ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int update(Employee employee) {
        int status = 0;
        String sql = "UPDATE employees SET name=?, password=?, email=?, country=? WHERE id=?";
        Connection conn = EmployeeDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getCountry());
            ps.setInt(5, employee.getId());
            status = ps.executeUpdate();
            conn.close();
        } catch (NullPointerException | SQLException ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static int delete(int id) {
        int status = 0;
        String sql = "DELETE FROM employees WHERE id=?";
        Connection conn = EmployeeDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            status = ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return status;
    }

    public static Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employees WHERE id=?";
        Connection conn = EmployeeDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setPassword(rs.getString(3));
                employee.setEmail(rs.getString(4));
                employee.setCountry(rs.getString(5));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return employee;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        Connection conn = EmployeeDao.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setPassword(rs.getString(3));
                employee.setEmail(rs.getString(4));
                employee.setCountry(rs.getString(5));
                list.add(employee);
            }
            conn.close();
        } catch (NullPointerException | SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
