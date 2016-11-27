package loginregister.activity.model;

import beans.UserLoginCallBackBean;
import beans.UserRegisterLoginBean;
import constant.RestService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/9.
 */
public class LoginModel{

	private OnLoginListener listener;

	public LoginModel(OnLoginListener listener){
		this.listener = listener;
	}

	public void login(String phone, final String pwd, RestService service){
		listener.onBeginning();
		UserRegisterLoginBean bean = new UserRegisterLoginBean();
		bean.setPasswd(pwd);
		bean.setPhone(phone);

		Observable<UserLoginCallBackBean> response = service.login(bean);
		response.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserLoginCallBackBean>(){
					@Override
					public void onCompleted(){
						listener.onFinished();
					}

					@Override
					public void onError(Throwable e){
						listener.onFinished();
					}

					@Override
					public void onNext(UserLoginCallBackBean bean){
						System.out.println("state   " + bean.getState());
						System.out.println("msg   " + bean.getMsg());
						if(bean.getState().equalsIgnoreCase("success")){
							listener.onLoginScuess(
									bean.getData().getUser().getName(),
									bean.getData().getUser().getPhone(), pwd,
									bean.getData().getUser().getUserid(),
									bean.getData().getUser().getType());
							System.out.println(
									"name   " + bean.getData().getUser()
													.getName());

						}else{
							listener.onLoginFailed(bean.getMsg());
						}
					}
				});

	}
}
