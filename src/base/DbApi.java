package base;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.LinkedList;

public class DbApi 
{
	private static boolean Excute(String sql, Object... args)
		throws Exception
	{
		DbDao dao = new DbDao();

		dao.Connect();

		if(dao.isConnClosed())
		{
			return false;
		}
		
		boolean result = dao.ExecuteStmt(sql, args);

		dao.CloseDb();

		return result;
	}

	public static int GetSum(String sql, Object... args)
		throws Exception
	{
		DbDao dao = new DbDao();

		dao.Connect();

		if(dao.isConnClosed())
		{
			return -1;
		}
		
		ResultSet rs = dao.QueryStmt(sql, args);
		if(rs == null)
		{
			return -1;
		}
		
		rs.last();
		int size = rs.getRow();
		
		dao.CloseDb();

		return size;
	}
	
	public static List<Object[]> IndexPage(String sql, int cur, int pagesize, Object... args)
		throws Exception
	{
		DbDao dao = new DbDao();

		dao.Connect();

		if(dao.isConnClosed())
		{
			return null;
		}
		
		List<Object[]> list = new LinkedList<>();

		ResultSet rs = dao.QueryStmt(sql, args);
		if(rs == null)
		{
			return null;
		}

		if(!rs.absolute(cur))
		{
			return null;
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		int columCount = rsmd.getColumnCount();

		int count = 0;
		
		do
		{
			if(count >= pagesize)
			{
				break;
			}

			Object[] array = new Object[columCount];
			for(int i=0; i<columCount; ++i)
			{
				array[i] = rs.getString(i+1);
			}
			
			list.add(array);
			count++;
		}
		while (rs.next());
		
		dao.CloseDb();

		return list;
	}

	public static List<Object[]> Index(String sql, Object... args)
		throws Exception
	{
		return IndexPage(sql, 1, Integer.MAX_VALUE, args);
	}


	public static boolean Insert(String sql, Object... args)
		throws Exception
	{
		return Excute(sql, args);
	}

	public static boolean Delete(String sql, Object... args)
		throws Exception
	{
		return Excute(sql, args);
	}

	public static boolean Update(String sql, Object... args)
		throws Exception
	{
		return Excute(sql, args);
	}
}
