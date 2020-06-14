package Connection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

public class Download_URL {
	public static void main(String[] args) throws SQLException {
		String jdbcURL = "localhost";
		String username = "root";
		String password = "123456";
		Connection connection = null;
		String nguon = null;
		String tenfile = null;
		String dinhdang=null;
		String trangthai;
		String dich1 = null;
		String id = null;
		//thoi giannnnnnnnnnnnnnnnnnnnnnnnnnnn
		long start = System.currentTimeMillis();
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		   LocalDateTime now = LocalDateTime.now();
		   String time= dtf.format(now);
		   System.out.println(dtf.format(now));
		   System.out.println(time);
		System.out.println(start);

		String connectionURL = "jdbc:mysql://localhost:3306/datacontrol?rewriteBatchedStatements=true&relaxAutoCommit=true";
		connection = DriverManager.getConnection(connectionURL, username, password);
		System.out.println("connect success");
		Statement statement = connection.createStatement();
		String sql = ("SELECT * FROM datacontrol where id like '01'");
		ResultSet rs = statement.executeQuery(sql);
		if (rs.next()) {
			id = rs.getString("id");
			tenfile=rs.getString("tenfile");
			nguon = rs.getString("nguon");
			dinhdang = rs.getString("dinhdang");
			trangthai = rs.getString("trangthai");
			dich1 = rs.getString("dich1");
System.out.println(id);
			System.out.println("AA");
			System.out.println(dich1);
			System.out.println(nguon);
		}

	
		try {
			saveFileFromUrlWithJavaIO(dich1+ tenfile+dinhdang,
					nguon);
			System.out.println("finished");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String sql1 = "update datacontrol set thoigiantao = + '"+time+"' where id = '"+id+"'";
		System.out.println("chay duoc sql moi");
		statement = connection.prepareStatement(sql1);
		statement.execute(sql1);
		connection.close();
		long end = System.currentTimeMillis();
		   System.out.println(dtf.format(now));
		System.out.println(end);
		System.out.printf("Import done in %d ms\n", (end - start));
	}

	// Using Java IO
	public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
			throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(fileUrl).openStream());
			fout = new FileOutputStream(fileName);
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}

	// Using Commons IO library
	// Available at http://commons.apache.org/io/download_io.cgi
	public static void saveFileFromUrlWithCommonsIO(String fileName, String fileUrl)
			throws MalformedURLException, IOException {
		FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
	
	}
}