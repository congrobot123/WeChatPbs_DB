package base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.FileInputStream;
import java.util.Properties;
import java.sql.DriverManager;

public class DbDao
{
	private Connection conn;
	private PreparedStatement pstmt;
	private String url;
	private String user;
	private String passwd;

	public DbDao()
		throws Exception
	{
		conn = null;
		pstmt = null;

		String ip = null;
		String port = null;
		String database = null;
		
		try
		(
			FileInputStream fis = new FileInputStream("WeChatPbs_DB.ini");
		)
		{
			Properties props = new Properties();
			props.load(fis);
			ip = props.getProperty("ip");
			port = props.getProperty("port");
			database = props.getProperty("database");
			user = props.getProperty("user");
			passwd = props.getProperty("password");
			url = "jdbc:mysql://" + ip + ":" + port + "/" + database;	
		}
	}

	public boolean isConnClosed()
		throws Exception
	{
		if(conn != null)
		{
			return conn.isClosed();
		}
		else
		{
			return true;
		}
	}

	public void Connect()
		throws Exception
	{
		if(conn == null)
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passwd);
		}
	}

	public boolean ExecuteStmt(String sql, Object... args)
		throws Exception
	{
		System.out.println(sql);
		for(Object obj : args)
		{
			System.out.print(obj + " ");
		}
		System.out.println();
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i=0; i<args.length; ++i)
		{
			pstmt.setObject(i+1, args[i]);
		}

		if(pstmt.executeUpdate() != 1)
		{
			return false;
		}

		return true;
	}

	public ResultSet QueryStmt(String sql, Object... args)
		throws Exception
	{
		System.out.println(sql);
		for(Object obj : args)
		{
			System.out.print(obj + " ");
		}
		System.out.println();
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i=0; i<args.length; ++i)
		{
			pstmt.setObject(i+1, args[i]);
		}
		
		return pstmt.executeQuery();
	}

	public void CloseDb()
		throws Exception 
	{
		if(pstmt != null && !pstmt.isClosed())
		{
			pstmt.close();
		}

		if(conn != null && !conn.isClosed())
		{
			conn.close();
		}
	}
}
