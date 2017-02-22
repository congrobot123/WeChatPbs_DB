package bizlogic;

public class Contact
{
	private String telnum;
	private String name;
	private String passwd;
	private Gender gender;
	private String remark;
	private Auth auth;
	
	public String GetTelnum()
	{
		return telnum;
	}
	
	public String GetName()
	{
		return name;
	}
	
	public void SetPasswd(String passwd)
	{
		this.passwd = passwd;
	}
	
	public String GetPasswd()
	{
		return passwd;
	}
	
	public void SetGender(Gender gender)
	{
		this.gender = gender;
	}
	
	public Gender GetGender()
	{
		return gender;
	}
	
	public void SetRemark(String remark)
	{
		this.remark = remark;
	}
	
	public String GetRemark()
	{
		return remark;
	}

	public void SetAuth(Auth auth)
	{
		this.auth = auth;
	}
	
	public Auth GetAuth()
	{
		return auth;
	}
	
	public Contact(String telnum, String name, String passwd, Gender gender, String remark, Auth auth)
	{
		this.telnum = telnum;
		this.name = name;
		this.passwd = passwd;
		this.gender = gender;
		this.remark = remark;
		this.auth = auth;
	}
}