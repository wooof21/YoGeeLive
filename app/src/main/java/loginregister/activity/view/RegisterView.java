package loginregister.activity.view;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface RegisterView{
	void setTitle();
	void observeEye();
	void getCode();
	void onWrongLengthView();
	void onSuccessView();
	void onEmptyView();
	void onRegisterPhoneView();
	void onFailed();
	void onSend();
	void onEmptyCode();
	void onRegisterFailed(String msg);
	void onRegisterScuess();
}
