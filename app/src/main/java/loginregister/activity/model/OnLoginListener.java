package loginregister.activity.model;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface OnLoginListener{
	void onBeginning();
	void onLoginScuess(String name, String phone, String pwd, String id,
					   String type);
	void onLoginFailed(String msg);
	void onFinished();
}
