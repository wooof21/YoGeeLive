package loginregister.activity.model;

import beans.UserCheckBean;
import beans.UserCheckCallBackBean;
import beans.UserRegisterLoginBean;
import beans.UserRegisterCallBackBean;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import constant.RestService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/8.
 */
public class RegisterModel{

	private OnVerifyPhoneListener listener;

	public RegisterModel(OnVerifyPhoneListener listener){
		this.listener = listener;
	}

	public void getCode(String phone, RestService service){
		checkUser(phone, service);
	}

	private void checkUser(final String phone, RestService service){
		UserCheckBean bean = new UserCheckBean();
		bean.setPhone(phone);
		Observable<UserCheckCallBackBean> response = service.checkUser(bean);

		response.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserCheckCallBackBean>(){
					@Override
					public void onCompleted(){

					}

					@Override
					public void onError(Throwable e){

					}

					@Override
					public void onNext(UserCheckCallBackBean responseBody){
						if(responseBody.getState().equalsIgnoreCase("success")){
							if(responseBody.getData().getResult().equals("0")){
								SMSSDK.getVerificationCode("86", phone);
								SMSSDK.registerEventHandler(eh); //注册短信回调
								listener.onSend();
							}else{
								listener.onRegisteredPhone();
							}
						}else{
							listener.onFailed();
						}
					}
				});
	}

	public void register(String phone, String pwd, String code,
						 RestService service){
		UserRegisterLoginBean bean = new UserRegisterLoginBean();
		bean.setPhone(phone);
		bean.setPasswd(pwd);
		Observable<UserRegisterCallBackBean> response = service.register(bean);
		response.subscribeOn(Schedulers.io())
				.subscribe(new Subscriber<UserRegisterCallBackBean>(){
					@Override
					public void onCompleted(){

					}

					@Override
					public void onError(Throwable e){

					}

					@Override
					public void onNext(UserRegisterCallBackBean bean){
						if(bean.getState().equalsIgnoreCase("success")){
							listener.onRegisterScuess();
						}else{
							listener.onRegisterFailed(bean.getMsg());
						}
					}
				});
	}

	private EventHandler eh = new EventHandler(){

		@Override
		public void afterEvent(int event, int result, Object data){

			if(result == SMSSDK.RESULT_COMPLETE){
				//回调完成
				if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
					//提交验证码成功
					System.out.println("提交验证码成功  " + data.toString());
				}else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					//获取验证码成功
					System.out.println("获取验证码成功  " + data.toString());
				}else if(event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
					//返回支持发送验证码的国家列表
				}
			}else{
				((Throwable) data).printStackTrace();
			}
		}
	};
}
