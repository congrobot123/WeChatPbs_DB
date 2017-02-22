package bizlogic;

public class Friend
{
	private String contact_tel;
	private String contact_name;
	private String telnum;
	private String name;
	private Gender gender;
	private String remark;
	
	public void SetContactTel(String contact_tel)
	{
		this.contact_tel = contact_tel;
	}
	
	public String GetContactTel()
	{	
		return contact_tel;
	}
	
	public void SetContactName(String contact_name)
	{
		this.contact_name = contact_name;
	}
	
	public String GetContactName()
	{
		return contact_name;
	}
	
	public void SetTelnum(String telnum)
	{
		this.telnum = telnum;
	}
	
	public String GetTelnum()
	{
		return telnum;
	}
	
	public void SetName(String name)
	{
		this.name = name;
	}
	
	public String GetName()
	{
		return name;
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
	
	public Friend(String contact_tel, String contact_name,String telnum, String name, Gender gender, String remark)
	{
		this.contact_tel = contact_tel;
		this.contact_name = contact_name;
		this.telnum = telnum;
		this.name = name;
		this.gender = gender;
		this.remark = remark;
	}
}