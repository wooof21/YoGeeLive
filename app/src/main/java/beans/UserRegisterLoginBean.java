package beans;

/**
 * Created by Administrator on 2016/8/9.
 */
public class UserRegisterLoginBean{

	private String phone;
	private String passwd;

	public UserRegisterLoginBean(){
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPasswd(){
		return passwd;
	}

	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
}
