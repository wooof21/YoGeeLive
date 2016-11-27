package beans;

/**
 * Created by Administrator on 2016/8/8.
 */
public class UserCheckCallBackBean{
	private String state;
	private String msg;
	private Data data;

	public UserCheckCallBackBean(){
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
		private String result;

		public Data(){
		}

		public String getResult(){
			return result;
		}

		public void setResult(String result){
			this.result = result;
		}
	}
}
