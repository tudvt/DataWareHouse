package Connection;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * Sample Java program that imports data from an Excel file to MySQL database.
 *
 * @author Nam Ha Minh - https://www.codejava.net
 *
 */
public class Excel2DatabaseTest {

	public static void main(String[] args) throws SQLException {
		String jdbcURL = "localhost";
		String username = "root";
		String password = "123456";

		String excelFilePath = "C:\\Users\\Tuong Tu\\Desktop\\ds sv.xlsx";
		PreparedStatement statement = null;
		int batchSize = 20;

		Connection connection = null;

		try {
			long start = System.currentTimeMillis();
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			   LocalDateTime now = LocalDateTime.now();
			   System.out.println(dtf.format(now));
			System.out.println(start);

			FileInputStream inputStream = new FileInputStream(excelFilePath);

			Workbook workbook = new XSSFWorkbook(inputStream);

			// Sheet firstSheet = workbook.getSheetAt(0);
			// Iterator<Row> rowIterator = firstSheet.iterator();
			String connectionURL = "jdbc:mysql://localhost:3306/sinhvien?rewriteBatchedStatements=true&relaxAutoCommit=true";
			connection = DriverManager.getConnection(connectionURL, username, password);

			int count = 0;

			// rowIterator.next(); // skip the header row
			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Row row;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {//không lấy dòng đầu tiên nếu i=1
				row = (Row) sheet.getRow(i);
				String stt = formatter.formatCellValue(row.getCell(0));
				String ho = formatter.formatCellValue(row.getCell(1));
				String ten = formatter.formatCellValue(row.getCell(2));
				String facebook = formatter.formatCellValue(row.getCell(3));
				String sdt = formatter.formatCellValue(row.getCell(4));
				String nct = formatter.formatCellValue(row.getCell(5));

				String sql = "INSERT INTO sinhvien VALUES ('"
						+ stt + "','" + ho + "','" + ten + "','" + facebook + "','" + sdt + "','" + nct + "')";
				statement = connection.prepareStatement(sql);
				statement.execute();
				// String sql = "INSERT INTO employee (name, address, contactNo, email)
				// VALUES('" + name + "','" + add + "'," + contact + ",'" + email + "')";
				// pstm = (PreparedStatement) con.prepareStatement(sql);
				// pstm.execute();
				System.out.println("Import rows " + i);
			}
			workbook.close();
			connection.commit();
			// execute the remaining queries
			statement.close();


			connection.close();
			System.out.println("Success import excel to mysql table");
			long end = System.currentTimeMillis();
			   System.out.println(dtf.format(now));
			System.out.println(end);
			System.out.printf("Import done in %d ms\n", (end - start));
		}
			  catch (IOException e) {
		            e.printStackTrace();
		        }

	}
}