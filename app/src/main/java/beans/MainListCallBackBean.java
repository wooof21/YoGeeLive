package beans;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MainListCallBackBean{

	private String state;
	private String msg;
	private Data data;

	public MainListCallBackBean(){
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
		private ArrayList<Item> list;

		public Data(){
		}

		public ArrayList<Item> getList(){
			return list;
		}

		public void setList(ArrayList<Item> list){
			this.list = list;
		}
	}

	public class Item{
		private String id;
		private String userid;
		private String name;
		private String streamid;
		private String img;
		private String code;

		public Item(){
		}

		public String getId(){
			return id;
		}

		public void setId(String id){
			this.id = id;
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

		public String getStreamid(){
			return streamid;
		}

		public void setStreamid(String sreamid){
			this.streamid = sreamid;
		}

		public String getImg(){
			return img;
		}

		public void setImg(String img){
			this.img = img;
		}

		public String getCode(){
			return code;
		}

		public void setCode(String code){
			this.code = code;
		}
	}
}
