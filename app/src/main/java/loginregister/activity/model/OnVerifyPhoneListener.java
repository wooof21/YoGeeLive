package loginregister.activity.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public interface OnVerifyPhoneListener{
	void onWrongLength();
	void onSuccess();
	void onEmpty();
	void onRegisteredPhone();
	void onFailed();
	void onSend();
	void onEmptyCode();
	void onRegisterFailed(String msg);
	void onRegisterScuess();
}
