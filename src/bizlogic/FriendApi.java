package bizlogic;
import base.DbApi;
import static bizlogic.Gender.*;
import static bizlogic.Auth.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;


public class FriendApi
{
	private static List<Friend> IndexPage(String sql, int cur, int pagesize, Object... args)
		throws Exception
	{
		List<Friend> friendlist = new LinkedList<>();
		
		List<Object[]> objectlist = DbApi.IndexPage(sql, cur, pagesize, args);
		if(objectlist == null)
		{
			return null;
		}
		
		Iterator<Object[]> it = objectlist.iterator();
		while(it.hasNext())
		{
			Object[] temp = it.next();
			String contact_tel = (String)temp[0];
			String contact_name = (String)temp[1];
			String telnum = (String)temp[2];
			String name = (String)temp[3];
			Gender gender = null;
			String tgender = (String)temp[4];
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
			String remark = (String)temp[5];
			
			Friend friend = new Friend(contact_tel, contact_name, telnum, name, gender, remark);
			friendlist.add(friend);
		}
		
		return friendlist;
	}
	
	private static List<Friend> Index(String sql, Object... args)
		throws Exception
	{
		return IndexPage(sql, 1, Integer.MAX_VALUE, args);
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
	
	public static int GetSum_HostNum(Contact owner, String hostnum)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			String sql = "select * from friend_table where contact_tel = ?";
			try
			{
				return DbApi.GetSum(sql, hostnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi GetSum_HostNum 1");
				return -1;
			}
		}
		else
		{
			return -1;
		}
	}
	
	public static int GetSum_HostName(Contact owner, String hostname)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			String sql = "select * from friend_table where contact_name = ?";
		
			try
			{
				return DbApi.GetSum(sql, hostname);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi GetSum_HostName 1");
				return -1;
			}
		}
		else
		{
			return -1;
		}
	}
	
	public static List<Friend> ListFriend_HostNum(Contact owner, String hostnum, int cur, int pagesize)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			try
			{
				return IndexPage("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_tel = ?", cur, pagesize, hostnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi ListFriend_HostNum 1");
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static List<Friend> ListFriend_HostName(Contact owner, String hostname, int cur, int pagesize)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			try
			{
				return IndexPage("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_name = ?", cur, pagesize, hostname);	
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi ListFriend_HostName 1");
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static List<Friend> ListAllFriends_HostNum(Contact owner, String hostnum)
	{
		return ListFriend_HostNum(owner, hostnum, 1, Integer.MAX_VALUE);
	}
	
	public static List<Friend> ListAllFriends_HostName(Contact owner, String hostname)
	{
		return ListFriend_HostName(owner, hostname, 1, Integer.MAX_VALUE);
	}
	
	public static boolean DeleteFriend_HostNum_Telnum(Contact owner, String hostnum, String telnum)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			try
			{
				return Delete("delete from friend_table where contact_tel = ? and telnum = ?", hostnum, telnum);	
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi DeleteFriend_HostNum_Telnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	
	public static boolean DeleteFriend_HostNum_Name(Contact owner, String hostnum, String name)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			try
			{
				return Delete("delete from friend_table where contact_tel = ? and name = ?", hostnum, name);	
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi DeleteFriend_HostNum_Name 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean DeleteFriend_HostName_Telnum(Contact owner, String hostname, String telnum)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			try
			{
				return Delete("delete from friend_table where contact_name = ? and telnum = ?", hostname, telnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi DeleteFriend_HostName_Telnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean DeleteFriend_HostName_Name(Contact owner, String hostname, String name)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			try
			{
				return Delete("delete from friend_table where contact_name = ? and name = ?", hostname, name);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi DeleteFriend_HostName_Name 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static Friend FriendInfo_HostNum_Telnum(Contact owner, String hostnum, String telnum)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			List<Friend> friendlist = null;
			
			try
			{
				friendlist = Index("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_tel = ? and telnum = ?", hostnum, telnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi FriendInfo_HostNum_Telnum 1");
				return null;
			}
			
			if(friendlist == null)
			{
				return null;
			}
			Iterator<Friend> it = friendlist.iterator();
			if(it.hasNext())
			{
				Friend friend = it.next();
				return friend;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static Friend FriendInfo_HostNum_Name(Contact owner, String hostnum, String name)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(hostnum))
		{
			List<Friend> friendlist = null;
		
			try
			{
				friendlist = Index("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_tel = ? and name = ?", hostnum, name);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi FriendInfo_HostNum_Name 1");
				return null;
			}
			
			if(friendlist == null)
			{
				return null;
			}
			
			Iterator<Friend> it = friendlist.iterator();
			if(it.hasNext())
			{
				Friend friend = it.next();
				return friend;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}
	
	public static Friend FriendInfo_HostName_Telnum(Contact owner, String hostname, String telnum)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			List<Friend> friendlist = null;
		
			try
			{
				friendlist = Index("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_name = ? and telnum = ?", hostname, telnum);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi FriendInfo_HostName_Telnum 1");
				return null;
			}
			
			if(friendlist == null)
			{
				return null;
			}
			
			Iterator<Friend> it = friendlist.iterator();
			if(it.hasNext())
			{
				Friend friend = it.next();
				return friend;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static Friend FriendInfo_HostName_Name(Contact owner, String hostname, String name)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(hostname))
		{
			List<Friend> friendlist = null;
		
			try
			{
				friendlist = Index("select contact_tel, contact_name, telnum, name, gender, remark from friend_table,gender_table where friend_table.gender_id = gender_table.id and contact_name = ? and name = ?", hostname, name);
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi FriendInfo_HostName_Name 1");
				return null;
			}
			
			if(friendlist == null)
			{
				return null;
			}
			
			Iterator<Friend> it = friendlist.iterator();
			if(it.hasNext())
			{
				Friend friend = it.next();
				return friend;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static boolean UpdateFriend_HostTel_Telnum(Contact owner, Friend friend)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(friend.GetContactTel()))
		{
			String sql;
			int gender_id;
			Gender gender = friend.GetGender();
			if(gender == MALE)
			{
				gender_id = 1;
			}
			else if(gender == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			sql = "update friend_table set gender_id = ?, remark = ?"
					+ " where contact_tel = ? and telnum = ?";
			
			try
			{
				return Update(sql, gender_id, friend.GetRemark(), friend.GetContactTel(), friend.GetTelnum());
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi UpdateFriend_HostTel_Telnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean UpdateFriend_HostTel_Name(Contact owner, Friend friend)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(friend.GetContactTel()))
		{
			String sql;
			int gender_id;
			Gender gender = friend.GetGender();
			if(gender == MALE)
			{
				gender_id = 1;
			}
			else if(gender == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			sql = "update friend_table set gender_id = ?, remark = ?"
					+ " where contact_tel = ? and name = ?";
			
			try
			{
				return Update(sql, gender_id, friend.GetRemark(), friend.GetContactTel(), friend.GetName());
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi UpdateFriend_HostTel_Name 1");
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	
	public static boolean UpdateFriend_HostName_Telnum(Contact owner, Friend friend)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(friend.GetContactName()))
		{
			String sql;
			int gender_id;
			Gender gender = friend.GetGender();
			if(gender == MALE)
			{
				gender_id = 1;
			}
			else if(gender == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			sql = "update friend_table set gender_id = ?, remark = ?"
					+ " where contact_name = ? and telnum = ?";
			
			try
			{
				return Update(sql, gender_id, friend.GetRemark(), friend.GetContactName(), friend.GetTelnum());
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi UpdateFriend_HostName_Telnum 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean UpdateFriend_HostName_Name(Contact owner, Friend friend)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetName().equals(friend.GetContactName()))
		{
			String sql;
			int gender_id;
			Gender gender = friend.GetGender();
			if(gender == MALE)
			{
				gender_id = 1;
			}
			else if(gender == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			sql = "update friend_table set gender_id = ?, remark = ?"
					+ " where contact_name = ? and name = ?";
			
			try
			{
				return Update(sql, gender_id, friend.GetRemark(), friend.GetContactName(), friend.GetName());
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi UpdateFriend_HostName_Name 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean InsertFriend(Contact owner, Friend friend)
	{
		if( (owner.GetAuth() == ADMIN ) || owner.GetTelnum().equals(friend.GetContactTel()))
		{
			String sql;
			int gender_id;
			Gender gender = friend.GetGender();
			if(gender == MALE)
			{
				gender_id = 1;
			}
			else if(gender == FEMALE)
			{
				gender_id = 2;
			}
			else
			{
				gender_id = 0;
			}
			
			sql = "insert into friend_table values(null, ?, ?, ?, ?, ?, ?)";
			
			try
			{
				return Insert(sql, friend.GetContactTel(), friend.GetContactName(),
					friend.GetTelnum(), friend.GetName(), gender_id, friend.GetRemark());
			}
			catch(Exception ex)
			{
				System.out.println("bizlogic FriendApi InsertFriend 1");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
}