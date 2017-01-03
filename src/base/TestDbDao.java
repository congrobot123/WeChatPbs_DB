package base;

public class TestDbDao
{
	public static void main(String[] args)
		throws Exception
	{
		DbApi.Delete("delete from contact_table where auth_id = 1");

		for(int i=0; i<100; ++i)
		{
			DbApi.Insert("insert into contact_table values(null, ?, ?, ?, ?, ?, 1)",
					("XuChong" + i), ("158791270" + i), "123456", 1, ("test" + i)	
			);
		}
	}
}