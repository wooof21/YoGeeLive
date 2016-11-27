package loginregister.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import constant.RestService;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerLoginComponent;
import loginregister.activity.module.LoginModule;
import loginregister.activity.presenter.LoginPresenter;
import loginregister.activity.view.LoginView;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity
		implements View.OnClickListener, LoginView{


	@Inject
	UMShareAPI mShareAPI;
	@Inject
	LoginPresenter presenter;
	@Inject
	RestService service;
	@BindView(R.id.login_logo)
	TextView loginLogo;
	@BindView(R.id.login_phone)
	EditText loginPhone;
	@BindView(R.id.login_pwd)
	EditText loginPwd;
	@BindView(R.id.login_forget)
	TextView loginForget;
	@BindView(R.id.login_register)
	TextView loginRegister;
	@BindView(R.id.login_login)
	TextView loginLogin;
	@BindView(R.id.login_weibo)
	ImageView loginWeibo;
	@BindView(R.id.login_weixin)
	ImageView loginWeixin;
	@BindView(R.id.login_qq)
	ImageView loginQq;
	@BindView(R.id.login_parent)
	RelativeLayout parent;
	@Inject
	SharedPreferences sp;
	@Inject
	SharedPreferences.Editor editor;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		beginAll();
	}

	@Override
	protected void beginAll(){
		loginWeibo.setOnClickListener(this);
		loginWeixin.setOnClickListener(this);
		loginQq.setOnClickListener(this);
		loginLogin.setOnClickListener(this);
	}


	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerLoginComponent.builder().appComponent(appComponent)
							.loginModule(new LoginModule(this, this)).build()
							.inject(this);
	}


	@Override
	public void onClick(View v){
		switch(v.getId()){
			case R.id.login_weibo:
				loginThirdParty(SHARE_MEDIA.SINA);
				break;
			case R.id.login_weixin:
				loginThirdParty(SHARE_MEDIA.WEIXIN);
				break;
			case R.id.login_qq:
				loginThirdParty(SHARE_MEDIA.QQ);
				break;
			case R.id.login_login:
				presenter.login(loginPhone.getText().toString(),
								loginPwd.getText().toString(), service);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void requestPermission(Activity activity){
		presenter.permission(LoginActivity.this);
	}

	@Override
	public void loginThirdParty(SHARE_MEDIA platform){
		presenter.loginThirdParty(platform, this);
	}

	@Override
	public void onBeginning(){
	}

	@Override
	public void onLoginScuess(String name, String phone, String pwd, String id,
							  String type){
		Toast.makeText(this, name + ", 欢迎回来!", Toast.LENGTH_SHORT).show();
		presenter.saveInfo(editor, phone, pwd, id, type);
		finish();
	}


	@Override
	public void onLoginFailed(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFinished(){
	}

}
