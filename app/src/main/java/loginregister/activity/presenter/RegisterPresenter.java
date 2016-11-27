package loginregister.activity.presenter;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import constant.RestService;
import live.yogee.com.yogeelive.R;
import loginregister.activity.model.OnVerifyPhoneListener;
import loginregister.activity.model.RegisterModel;
import loginregister.activity.view.RegisterView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/8/4.
 */
public class RegisterPresenter implements OnVerifyPhoneListener{

	private RegisterView view;
	private RegisterModel model;

	public RegisterPresenter(RegisterView view){
		this.view = view;
		model = new RegisterModel(this);
	}

	public void setTitle(TextView t){
		t.setText("注册新用户");
	}

	public void eye(boolean isEyeClosed, final View v1, final View v2){
		Observable.just(isEyeClosed).observeOn(AndroidSchedulers.mainThread())
				  .subscribe(new Subscriber<Boolean>(){
					  @Override
					  public void onCompleted(){

					  }

					  @Override
					  public void onError(Throwable e){

					  }

					  @Override
					  public void onNext(Boolean aBoolean){
						  if(aBoolean){
							  ((ImageView) v1).setImageResource(
									  R.mipmap.login_eye_close);
							  ((EditText) v2).setTransformationMethod(
									  PasswordTransformationMethod
											  .getInstance());
						  }else{
							  ((ImageView) v1).setImageResource(
									  R.mipmap.login_eye_open);
							  ((EditText) v2).setTransformationMethod(
									  HideReturnsTransformationMethod
											  .getInstance());
						  }
					  }
				  });
	}

	public void getCode(String phone, RestService service){
		if(phone.length() == 0){
			onEmpty();
		}else if(phone.length() != 11){
			onWrongLength();
		}else{
			model.getCode(phone, service);
		}
	}

	public void register(String phone, String pwd, String code,
						 RestService service){
		if(phone.length() == 0){
			onEmpty();
		}else if(phone.length() != 11){
			onWrongLength();
		}else if(code.length() == 0){
			onEmptyCode();
		}else{
			model.register(phone, pwd, code, service);
		}
	}

	@Override
	public void onWrongLength(){
		view.onWrongLengthView();
	}

	@Override
	public void onSuccess(){
		view.onSuccessView();
	}

	@Override
	public void onEmpty(){
		view.onEmptyView();
	}

	@Override
	public void onRegisteredPhone(){
		view.onRegisterPhoneView();
	}

	@Override
	public void onFailed(){
		view.onFailed();
	}

	@Override
	public void onSend(){
		view.onSend();
	}

	@Override
	public void onEmptyCode(){
		view.onEmptyCode();
	}

	@Override
	public void onRegisterFailed(String msg){
		view.onRegisterFailed(msg);
	}

	@Override
	public void onRegisterScuess(){
		view.onRegisterScuess();
	}
}
