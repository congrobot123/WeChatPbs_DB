package bizlogic;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import base.DbApi;
import static bizlogic.LogType.*;
import static bizlogic.LogLevel.*;
import static bizlogic.Auth.*;

public class LogApi
{	
	private static final String ADMIN_TEL = "00000000000";
	
	private static List<Log> IndexPage(String sql, int cur, int pagesize, Object... args)
		throws Exception
	{
		List<Log> loglist = new LinkedList<>();
		List<Object[]> objectlist = DbApi.IndexPage(sql, cur, pagesize, args);
		
		if(objectlist == null)
		{
			return null;
		}
		
		Iterator<Object[]> it = objectlist.iterator();
		
		while(it.hasNext())
		{
			Object[] array = it.next();
			
			long time = Long.parseLong((String)array[0]);
			String genr = (String)array[1];
			LogType type = null;
			String ttype = (String)array[2];
			if(ttype.equals("CONTACT"))
			{
				type = CONTACT;
			}
			else if(ttype.equals("FRIEND"))
			{
				type = FRIEND;
			}
			else if(ttype.equals("LOG"))
			{
				type = LOG;
			}
			else if(ttype.equals("CONSOLE"))
			{
				type = CONSOLE;
			}
			else if(ttype.equals("WECHATPBS"))
			{
				type = WECHATPBS;
			}
			else if(ttype.equals("TEST"))
			{
				type = TEST;
			}
			else
			{
				type = SYSTEM;
			}
			
			LogLevel level = null;
			String tlevel = (String)array[3];
			if(tlevel.equals("ERROR"))
			{
				level = ERROR;
			}
			else if(tlevel.equals("CRIT"))
			{
				level = CRIT;
			}
			else if(tlevel.equals("WARN"))
			{
				level = WARN;
			}
			else
			{
				level = DEBUG;
			}
			
			String content = (String)array[4];
			
			Log log = new Log(new Date(time), genr, type, level, content);
			loglist.add(log);
		}
		
		return loglist;
	}
	
	private static boolean InsertSql(String sql, Object... args)
		throws Exception
	{
		return DbApi.Insert(sql, args);
	}
	
	private static boolean Delete(String sql, Object... args)
		throws Exception
	{
		return DbApi.Delete(sql, args);
	}
	
	private static List<Log> Index(String sql, Object... args)
		throws Exception
	{
		return IndexPage(sql, 1, Integer.MAX_VALUE, args);
	}
	
	public static List<Log> Index(Contact owner, Date begin, Date end, LogType type, LogLevel level)
	{
		long begintime = begin.getTime();
		long endtime = end.getTime();
		
		int type_id = 0;
		if(type == SYSTEM)
		{
			type_id = 1;
		}
		else if(type == CONTACT)
		{
			type_id = 2; 
		}
		else if(type == FRIEND)
		{
			type_id = 3;
		}
		else if(type == LOG)
		{
			type_id = 4;
		}
		else if(type == CONSOLE)
		{
			type_id = 5;
		}
		else if(type == WECHATPBS)
		{
			type_id = 6;
		}
		else if(type == TEST)
		{
			type_id = 7;
		}
		else
		{
			type_id = 0;
		}
		
		int level_id = 0;
		if(level == ERROR)
		{
			level_id = 1;
		}
		else if(level == CRIT)
		{
			level_id = 2;
		}
		else if(level == WARN)
		{
			level_id = 3;
		}
		else if(level == DEBUG)
		{
			level_id = 4;
		}
		else
		{
			level_id = 0;
		}
		
		String sql = null;
		if(owner.GetAuth() == ADMIN)
		{
			if(type_id !=0 && level_id != 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ?"
								+ " and type_id = ? and level_id = ?";
				try
				{
					return Index(sql, begintime, endtime, type_id, level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 1");
					return null;
				}
			}
			else if(type_id != 0 && level_id == 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ?"
								+ " and type_id = ?";
				try
				{
					return Index(sql, begintime, endtime, type_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 2");
					return null;
				}
			}
			else if(type_id == 0 && level_id != 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ?"
								+ " and level_id = ?";
				try
				{
					return Index(sql, begintime, endtime, level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 3");
					return null;
				}
			}
			else
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ?";
				try
				{
					return Index(sql, begintime, endtime);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 4");
					return null;
				}
			}
		}
		else
		{
			if(type_id !=0 && level_id != 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ? and genr = ?"
								+ " and type_id = ? and level_id = ?";
				try
				{
					return Index(sql, begintime, endtime, owner.GetTelnum(), type_id, level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 5");
					return null;
				}
			}
			else if(type_id != 0 && level_id == 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ? and genr = ?"
								+ " and type_id = ?";
				try
				{
					return Index(sql, begintime, endtime, owner.GetTelnum(), type_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 6");
					return null;
				}
			}
			else if(type_id == 0 && level_id != 0)
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ? and genr = ?"
								+ " and level_id = ?";
				try
				{
					return Index(sql, begintime, endtime, owner.GetTelnum(), level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 7");
					return null;
				}
			}
			else
			{
				sql = "select time, genr, type, level, content from log_table, type_table, level_table"
					+ " where log_table.type_id = type_table.id"
						+ " and log_table.level_id = level_table.id"
							+ " and time >= ? and time <= ? and genr = ?";
				try
				{
					return Index(sql, begintime, endtime, owner.GetTelnum());
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Index 8");
					return null;
				}
			}
		}
	}
	
	public static boolean Delete(Contact owner, Date begin, Date end, LogType type, LogLevel level)
	{
		long begintime = begin.getTime();
		long endtime = end.getTime();
		
		int type_id = 0;
		if(type == SYSTEM)
		{
			type_id = 1;
		}
		else if(type == CONTACT)
		{
			type_id = 2; 
		}
		else if(type == FRIEND)
		{
			type_id = 3;
		}
		else if(type == LOG)
		{
			type_id = 4;
		}
		else if(type == CONSOLE)
		{
			type_id = 5;
		}
		else if(type == WECHATPBS)
		{
			type_id = 6;
		}
		else if(type == TEST)
		{
			type_id = 7;
		}
		else
		{
			type_id = 0;
		}
		
		int level_id = 0;
		if(level == ERROR)
		{
			level_id = 1;
		}
		else if(level == CRIT)
		{
			level_id = 2;
		}
		else if(level == WARN)
		{
			level_id = 3;
		}
		else if(level == DEBUG)
		{
			level_id = 4;
		}
		else
		{
			level_id = 0;
		}
		
		String sql = null;
		if(owner.GetAuth() == ADMIN)
		{
			if(type_id !=0 && level_id != 0)
			{
				sql = "delete from log_table "
						+ " where time >= ? and time <= ?"
							+ " and type_id = ? and level_id = ?";
				try
				{
					return Delete(sql, begintime, endtime, type_id, level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Delete 1");
					return false;
				}
			}
			else if(type_id != 0 && level_id == 0)
			{
				sql = "delete from log_table"
						+ " where and time >= ? and time <= ?"
							+ " and type_id = ?";
				try
				{
					return Delete(sql, begintime, endtime, type_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Delete 2");
					return false;
				}
			}
			else if(type_id == 0 && level_id != 0)
			{
				sql = "delete from log_table"
						+ " where time >= ? and time <= ?"
							+ " and level_id = ?";
				try
				{
					return Delete(sql, begintime, endtime, level_id);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Delete 3");
					return false;
				}
			}
			else
			{
				sql = "delete from log_table"
						+ " where time >= ? and time <= ?";
				try
				{
					return Delete(sql, begintime, endtime);
				}
				catch(Exception ex)
				{
					LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Delete 4");
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
	
	private static boolean isAllNumber(String str)
	{
		char[] array = str.toCharArray();
		for(int i=0; i<array.length; ++i)
		{
			if(array[i] <'0' || array[i] >'9')
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean Insert(String genr, LogType type, LogLevel level, String content)
	{	
		int type_id = 1;
		if(type == CONTACT)
		{
			type_id = 2; 
		}
		else if(type == FRIEND)
		{
			type_id = 3;
		}
		else if(type == LOG)
		{
			type_id = 4;
		}
		else if(type == CONSOLE)
		{
			type_id = 5;
		}
		else if(type == WECHATPBS)
		{
			type_id = 6;
		}
		else if(type == TEST)
		{
			type_id = 7;
		}
		else
		{
			type_id = 1;
		}
		
		int level_id = 3;
		if(level == ERROR)
		{
			level_id = 1;
		}
		else if(level == CRIT)
		{
			level_id = 2;
		}
		else if(level == DEBUG)
		{
			level_id = 4;
		}
		else
		{
			level_id = 3;
		}
		
		if(genr == null)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert genr 1");
			return false;
		}
		
		if(genr.length() != 11)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert genr 2");
			return false;
		}
		
		if(isAllNumber(genr) == false)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert genr 3");
			return false;
		}
		
		if(type == TYPEALL)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert type 1");
			return false;
		}
		
		if(level == LEVELALL)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert level 1");
			return false;
		}
		
		if(content == null)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert content 1");
			return false;
		}
		
		if(content.length() > 64)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert content 2");
			return false;
		}
		
		String sql = "insert into log_table values(null, ?, ?, ?, ?, ?)";
		
		long time = System.currentTimeMillis();
		try
		{
			return InsertSql(sql, time, genr, type_id, level_id, content);
		}
		catch(Exception ex)
		{
			LogApi.Insert(ADMIN_TEL, LOG, ERROR, "Insert Insert 1");
			return false;
		}
	}
}