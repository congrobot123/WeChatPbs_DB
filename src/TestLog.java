import bizlogic.LogApi;
import bizlogic.ContactApi;
import bizlogic.Contact;
import bizlogic.Log;
import static bizlogic.LogType.*;
import static bizlogic.LogLevel.*;
import java.util.Date;
import java.util.List;

public class TestLog
{
	public static void main(String[] args)
	{
		Contact owner = ContactApi.Contact_Info_ByTelnum("00000000000");
		
		boolean result1;
		
		result1 = LogApi.Insert("15879120000", TEST, DEBUG, "TEST");
		if(result1 == true)
		{
			System.out.println("insert a log successfully!");
		}
		else
		{
			System.out.println("insert a log failed!");
		}
		
		result1 = LogApi.Insert("15879120001", TEST, WARN, "TEST");
		if(result1 == true)
		{
			System.out.println("insert a log successfully!");
		}
		else
		{
			System.out.println("insert a log failed!");
		}
		
		result1 = LogApi.Insert("15879120002", TEST, WARN, "TEST");
		if(result1 == true)
		{
			System.out.println("insert a log successfully!");
		}
		else
		{
			System.out.println("insert a log failed!");
		}
		
		Date begintime = new Date(0);
		Date endtime = new Date(System.currentTimeMillis() + 100);
		result1 = LogApi.Delete(owner, begintime, endtime, TYPEALL, DEBUG);
		if(result1 == true)
		{
			System.out.println("delete a log successfully!");
		}
		else
		{
			System.out.println("delete a log failed!");
		}
		
		Date begintime1 = new Date(1484136422589L);
		Date endtime1 = new Date(1484136422591L);
		
		
		List<Log> list = LogApi.Index(owner, begintime1, endtime1, TEST, WARN);
		if(list != null)
		{
			System.out.println("index a log successfully!");
		}
		else
		{
			System.out.println("index a log failed!");
		}
	}
}