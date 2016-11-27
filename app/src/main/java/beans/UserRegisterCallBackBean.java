package beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/9.
 */
public class UserRegisterCallBackBean implements Serializable{

	private String state;
	private String msg;
	private Data data;

	public UserRegisterCallBackBean(){
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getMsg(){
		return msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public Data getData(){
		return data;
	}

	public void setData(Data data){
		this.data = data;
	}

	public class Data{
		private User user;

		public Data(){
		}

		public User getUser(){
			return user;
		}

		public void setUser(User user){
			this.user = user;
		}
	}

	public class User{
		private String userid;
		private String name;
		private String img;
		private String sex;
		private String code;
		private String phone;

		public User(){
		}

		public String getUserid(){
			return userid;
		}

		public void setUserid(String userid){
			this.userid = userid;
		}

		public String getName(){
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getImg(){
			return img;
		}

		public void setImg(String img){
			this.img = img;
		}

		public String getSex(){
			return sex;
		}

		public void setSex(String sex){
			this.sex = sex;
		}

		public String getCode(){
			return code;
		}

		public void setCode(String code){
			this.code = code;
		}

		public String getPhone(){
			return phone;
		}

		public void setPhone(String phone){
			this.phone = phone;
		}
	}
}
