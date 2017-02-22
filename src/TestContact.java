import bizlogic.ContactApi;
import bizlogic.Contact;
import static bizlogic.Gender.*;

public class TestContact
{
	private static Contact owner;
	public static void main(String[] args)
	{
		int result1;
		result1 = ContactApi.Register("15879127000", "XiaoMing", "123456", "Male", "Test");
		if(result1 == 0)
		{
			System.out.println("Register successfully");
		}
		else
		{
			System.out.println("Register failed");
		}
		
		result1 = ContactApi.Login_ByTelnum("15879127000", "654321");
		if(result1 == 0)
		{
			System.out.println("Login successfully");
		}
		else
		{
			System.out.println("Login failed");
		}
		
		result1 = ContactApi.Login_ByTelnum("15879127000", "123456");
		if(result1 == 0)
		{
			System.out.println("Login successfully");
		}
		else
		{
			System.out.println("Login failed");
		}
		
		owner = ContactApi.Contact_Info_ByTelnum("15879127000");
		
		System.out.println("Contact Info:" + owner.GetTelnum() + 
						" " + owner.GetName() + 
						" " + owner.GetPasswd());
						
		owner.SetGender(FEMALE);
		
		boolean result2 = ContactApi.Update_ByTelnum(owner, "15879127000", owner);
		if(result2 == true)
		{
			System.out.println("Update successfully");
			owner = ContactApi.Contact_Info_ByTelnum("15879127000");
			if(owner.GetGender() == MALE)
			{
				System.out.println("male");
			}
			else if(owner.GetGender() == FEMALE)
			{
				System.out.println("female");
			}
			else
			{
				System.out.println("unknown");
			}
		}
		else
		{
			System.out.println("Update failed");
		}
		
		result2 = ContactApi.Delete_ByTelnum(owner, "15879127000");
		if(result2 == true)
		{
			System.out.println("Delete successfully");
		}
		else
		{
			System.out.println("Delete failed");
		}
	}
}