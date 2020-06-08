package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
 
public class MySQLConnUtils {
 
 // Kết nối vào MySQL.
 public static Connection getMySQLConnection() throws SQLException,
         ClassNotFoundException {
     String hostName = "localhost";
 
     String dbName = "sinhvien";
     String userName = "root";
     String password = "123456";
 
     return getMySQLConnection(hostName, dbName, userName, password);
 }
 
 public static Connection getMySQLConnection(String hostName, String dbName,
         String userName, String password) throws SQLException,
         ClassNotFoundException {
     // Khai báo class Driver cho DB MySQL
     // Việc này cần thiết với Java 5
     // Java6 tự động tìm kiếm Driver thích hợp.
     // Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
     Class.forName("com.mysql.jdbc.Driver");
 
     // Cấu trúc URL Connection dành cho Oracle
     // Ví dụ: jdbc:mysql://localhost:3306/simplehr
     String connectionURL = "jdbc:mysql://localhost:3306/sinhvien?autoReconnect=true&useSSL=false" ;
 
     Connection conn = DriverManager.getConnection(connectionURL, userName,
             password);
     System.out.println("success");
     return conn;
 }
 public static void main(String[] args) throws ClassNotFoundException, SQLException {
	 Connection connection = MySQLConnUtils.getMySQLConnection();
	    // Lấy ra kết nối tới cơ sở dữ liệu.
//     Connection connection = ConnectionUtils.getMyConnection();

     Statement statement = connection.createStatement();

     String sql = "Insert into sinhvien (ten,lop)  values ('A','2') ";

     // Thực thi câu lệnh.
     // executeUpdate(String) sử dụng cho các loại lệnh Insert,Update,Delete.
     int rowCount = statement.executeUpdate(sql);

     // In ra số dòng được trèn vào bởi câu lệnh trên.
     System.out.println("Row Count affected = " + rowCount);
}
}