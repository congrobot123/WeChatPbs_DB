package bizlogic;
import java.util.Date;

public class Log
{
	private Date time;
	private String genr;
	private LogType type;
	private LogLevel level;
	private String content;
	
	public void SetDate(Date time)
	{
		this.time = time;
	}
	
	public Date GetDate()
	{
		return time;
	}
	
	public void SetGenr(String genr)
	{
		this.genr = genr;
	}
	
	public String GetGenr()
	{
		return genr;
	}
	
	public void SetType(LogType type)
	{
		this.type = type;
	}
	
	public LogType GetType()
	{
		return type;
	}
	
	public void SetLevel(LogLevel level)
	{
		this.level = level;
	}
	
	public LogLevel GetLevel()
	{
		return level;
	}
	
	public void SetContent(String content)
	{
		this.content = content;
	}
	
	public String GetContent()
	{
		return content;
	}
	
	public Log(Date time, String genr, LogType type, LogLevel level, String content)
	{
		this.time = time;
		this.genr = genr;
		this.type = type;
		this.level = level;
		this.content = content;
	}
}

