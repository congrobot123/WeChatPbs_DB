package bizlogic;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import base.DbApi;
import static bizlogic.Gender.*;
import static bizlogic.Auth.*;

public class ContactApi
{
	private static List<Contact> IndexPage(String sql, int cur, int pagesize, Object... args)
		throws Exception
	{
		List<Contact> contactlist = new LinkedList<>();
		
		List<Object[]> objectlist = DbApi.IndexPage(sql, cur, pagesize, args);
		if(objectlist == null)
		{
			return null;
		}
		
		Iterator<Object[]> it = objectlist.iterator();
		
		while(it.hasNext())
		{
			Object[] array = it.next();

			String telnum = (String)array[0];
			String name = (String)array[1];
			String passwd = (String)array[2];
			Gender gender = null;
			String tgender = (String)array[3];
			if(tgender.equals("Male"))
			{
				gender = MALE;
			}
			else if(tgender.equals("Female"))
			{
				gender = FEMALE;
			}
			else
			{
				gender = UNKNOWN;
			}
			
			String remark = (String)array[4];
			
			Auth auth = null;
			String tauth = (String)array[5];
			if(tauth.equals("User"))
			{
				auth = USER;
			}
			else if(tauth.equals("Admin"))
			{
				auth = ADMIN;
			}
			else
			{
				auth = ANONYMOUS;
			}
			
			Contact contact = new Contact(telnum, name, passwd, gender, remark, auth);
			contactlist.add(contact);
		}
		
		return contactlist;
	}
	
	private static List<Contact> Index(String sql, Object... args)
		throws Exception
	{
		return ContactApi.IndexPage(sql, 1, Integer.MAX_VALUE, args);
	}
	
	private static boolean Insert(String sql, Object... args)
		throws Exception
	{
		return DbApi.Insert(sql, args);
	}
	
	private static boolean Delete(String sql, Object... args)
		throws Exception
	{
		return DbApi.Delete(sql, args);
	}
	
	private static boolean Update(String sql, Object... args)
		throws Exception
	{
		return DbApi.Update(sql, args);
	}
	
	private static int GetSum(String sql, Object... args)
		throws Exception
	{
		
		return DbApi.GetSum(sql, args);
	}
	
	public static int Register(String telnum, String name, String passwd, String gender, String remark)
	{
		int gender_id;
		if(gender.equals("Male"))
		{
			gender_id = 1;
		}
		else if(gender.equals("Female"))
		{
			gender_id = 2;
		}
		else
		{
			gender_id = 0;
		}
		
		List<Contact> list = null;
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and telnum = ?", telnum);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Register 1");
			return 4;
		}
		
		
		if(list != null)
		{
			Iterator<Contact> it = list.iterator();
			if(it.hasNext())
			{
				return 2;
			}
		}
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and name = ?", name);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Register 2");
			return 4;
		}
		
		if(list != null)
		{
			Iterator<Contact> it = list.iterator();
			if(it.hasNext())
			{
				return 3;
			}
		}
		
		boolean result = false;
		try
		{
			result = Insert("insert into contact_table values(null, ?, ?, ?, ?, ?, 1)",
								telnum, name, passwd, gender_id, remark);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Register 3");
			return 4;
		}
		
		if(result == true)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public static int Login_ByTelnum(String telnum, String passwd)
	{
		List<Contact> list = null;
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and telnum = ?", telnum);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Login_ByTelnum 1");
			return 3;
		}
		
		if(list != null)
		{
			Iterator<Contact> it = list.iterator();
			if(it.hasNext())
			{
				Contact contact = it.next();
				if(passwd.equals(contact.GetPasswd()))
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}
		}
		return 2;
	}
	
	public static int Login_ByName(String name, String passwd)
	{
		List<Contact> list = null;
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and name = ?", name);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Login_ByName 1");
			return 3;
		}
		
		if(list != null)
		{
			Iterator<Contact> it = list.iterator();
			if(it.hasNext())
			{
				Contact contact = it.next();
				if(passwd.equals(contact.GetPasswd()))
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}
		}
		return 2;
	}
	
	
	
	public static Contact Contact_Info_ByTelnum(String telnum)
	{
		List<Contact> list = null;
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and telnum = ?", telnum);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Contact_Info_ByTelnum 1");
			return null;
		}
		
		if(list == null)
		{
			return null;
		}
		
		Iterator<Contact> it = list.iterator();
		if(it.hasNext())
		{
			Contact contact = it.next();
			return contact;
		}
		
		return null;
	}
	
	public static Contact Contact_Info_ByName(String name)
	{
		List<Contact> list = null;
		
		try
		{
			list = Index("select telnum, name, passwd, gender, remark, auth " + 
						"from contact_table, gender_table, auth_table " + 
							"where contact_table.gender_id = Gender_table.id " + 
								"and contact_table.auth_id = Auth_table.id " +
									"and name = ?", name);
		}
		catch(Exception ex)
		{
			System.out.println("bizlogic ContactApi Contact_Info_ByName 1");
			return null;
		}
		
		if(list == null)
		{
			return null;
		}
		
		Iterator<Contact> it = list.iterator();
		if(it.hasNext())
		{
			Contact contact = it.next();
			return contact;
		}
		
		return null;	
	}
	
	public static boolean Delete_ByTelnum(Contact owner, String telnum)
	{
		if(owner.GetAuth() == ADMIN)
		{
			try
			{
				return Delete("delete from contact_table where telnum = ?", telnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic ContactApi Delete_ByTelnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean Delete_ByName(Contact owner, String name)
	{
		if(owner.GetAuth() == ADMIN)
		{
			try
			{
				return Delete("delete from contact_table where name = ?", name);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic ContactApi Delete_ByName 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean Update_ByTelnum(Contact owner, String telnum, Contact contact)
	{
		if((owner.GetAuth() == ADMIN) || owner.GetTelnum().equals(telnum))
		{
			int gender_id;
			if(contact.GetGender() == MALE)
			{
				gender_id = 1;
			}
			else if(contact.GetGender() == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			try
			{
				return Update("update contact_table set passwd = ?," +
							"gender_id = ?, remark = ? where telnum = ?", 
								contact.GetPasswd(), gender_id, contact.GetRemark(), telnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic ContactApi Update_ByTelnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean Update_ByName(Contact owner, String name, Contact contact)
	{
		if((owner.GetAuth() == ADMIN) || owner.GetName().equals(name))
		{
			int gender_id;
			if(contact.GetGender() == MALE)
			{
				gender_id = 1;
			}
			else if(contact.GetGender() == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			try
			{
				return Update("update contact_table set passwd = ?," +
							"gender_id = ?, remark = ? where name = ?", 
								contact.GetPasswd(), gender_id, contact.GetRemark(), name);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic ContactApi Update_ByName 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}