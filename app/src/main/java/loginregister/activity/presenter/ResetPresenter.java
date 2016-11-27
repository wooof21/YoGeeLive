package loginregister.activity.presenter;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import live.yogee.com.yogeelive.R;
import loginregister.activity.view.ResetView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ResetPresenter{

	private ResetView view;

	public ResetPresenter(ResetView view){
		this.view = view;
	}

	public void setTitle(TextView t){
		t.setText("重置密码");
	}

	public void setText(View v1, View v2, View v3){
		((TextView) v1).setText("设置新密码");
		v2.setVisibility(View.GONE);
		((TextView) v3).setText("完成");
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
}
