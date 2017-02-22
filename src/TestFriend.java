import bizlogic.ContactApi;
import bizlogic.Contact;
import bizlogic.FriendApi;
import bizlogic.Friend;
import static bizlogic.Gender.*;
import java.util.Iterator;
import java.util.List;

public class TestFriend
{
	public static void main(String[] args)
	{
		int result1;
		result1 = ContactApi.Register("15879120000", "XiaoMing", "123456", "Male", "Test");
		if(result1 == 0)
		{
			System.out.println("Register successfully");
		}
		else
		{
			System.out.println("Register failed");
		}
		
		Contact owner = ContactApi.Contact_Info_ByTelnum("15879120000");
		
		System.out.println("Contact Info:" + owner.GetTelnum() + 
						" " + owner.GetName() + 
						" " + owner.GetPasswd());
		boolean result2;				
		Friend friend;
		
		friend = new Friend("1587913000", "XiaoWang", "15879120001", "XiaoLi001", MALE, "Test001");
		
		result2 = FriendApi.InsertFriend(owner, friend);
		if(result2 == true)
		{
			System.out.println("Insert a friend successfully");
		}
		else
		{
			System.out.println("Insert a friend failed");
		}
		
		friend = new Friend("15879120000", "XiaoMing", "15879120001", "XiaoLi001", MALE, "Test001");
		
		result2 = FriendApi.InsertFriend(owner, friend);
		if(result2 == true)
		{
			System.out.println("Insert a friend successfully");
		}
		else
		{
			System.out.println("Insert a friend failed");
		}
		
		friend = new Friend("15879120000", "XiaoMing", "15879120002", "XiaoLi002", MALE, "Test001");
		
		result2 = FriendApi.InsertFriend(owner, friend);
		if(result2 == true)
		{
			System.out.println("Insert a friend successfully");
		}
		else
		{
			System.out.println("Insert a friend failed");
		}
		
		friend.SetGender(FEMALE);
		result2 = FriendApi.UpdateFriend_HostTel_Telnum(owner, friend);
		if(result2 == true)
		{
			System.out.println("Update a friend successfully");
		}
		else
		{
			System.out.println("Update a friend failed");
		}
		
		friend = FriendApi.FriendInfo_HostNum_Telnum(owner, "15879120000", "15879120002");
		if(friend != null)
		{
			System.out.println("Get a friend successfully");
		}
		else
		{
			System.out.println("Get a friend failed");
		}
		
		friend = FriendApi.FriendInfo_HostNum_Telnum(owner, "15879120000", "15879120001");
		if(friend != null)
		{
			System.out.println("Get a friend successfully");
		}
		else
		{
			System.out.println("Get a friend failed");
		}
		
		List<Friend> friendlist;
		friendlist = FriendApi.ListAllFriends_HostNum(owner, "15879120000");
		if(friendlist != null)
		{
			Iterator<Friend> it = friendlist.iterator();
			while(it.hasNext())
			{
				Friend temp = it.next();
				System.out.println(temp.GetTelnum() + " " + temp.GetName());
			}
		}
		else
		{
			System.out.println("Index failed");
		}
		
		int sum = FriendApi.GetSum_HostNum(owner, "15879120000");
		System.out.println("sum = " + sum);
		
		result2 = FriendApi.DeleteFriend_HostNum_Telnum(owner, "15879120000", "15879120001");
		friendlist = FriendApi.ListAllFriends_HostNum(owner, "15879120000");
		if(friendlist != null)
		{
			Iterator<Friend> it = friendlist.iterator();
			while(it.hasNext())
			{
				Friend temp = it.next();
				System.out.println(temp.GetTelnum() + " " + temp.GetName());
			}
		}
		else
		{
			System.out.println("Index failed");
		}
	}
}