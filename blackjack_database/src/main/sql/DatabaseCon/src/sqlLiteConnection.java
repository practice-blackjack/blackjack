import java.sql.*;
import java.sql.Connection;

public class sqlLiteConnection {
	public static void main(String [] args){
	Connection conn = null;
	
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\3 term\\Practice\\SqlLite\\mydatabase.sqlite");
			  System.out.println("Open Database");
			
		}
		catch(Exception e){
		 System.out.println(e);
		}
}
}