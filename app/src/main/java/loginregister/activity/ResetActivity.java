package loginregister.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerResetComponent;
import loginregister.activity.module.ResetModule;
import loginregister.activity.presenter.ResetPresenter;
import loginregister.activity.view.ResetView;

import javax.inject.Inject;
import java.util.Map;

public class ResetActivity extends BaseActivity
		implements ResetView, View.OnClickListener{

	@BindView(R.id.register_actionbar)
	RelativeLayout registerActionbar;
	@BindView(R.id.actionbar_back)
	ImageView actionbarBack;
	@BindView(R.id.actionbar_title)
	TextView actionbarTitle;
	@BindView(R.id.register_phone)
	EditText registerPhone;
	@BindView(R.id.register_certifycode)
	EditText registerCertifycode;
	@BindView(R.id.register_getcode)
	TextView registerGetcode;
	@BindView(R.id.register_setpwd)
	TextView registerSetpwd;
	@BindView(R.id.register_pwd)
	EditText registerPwd;
	@BindView(R.id.register_eyeclose)
	ImageView registerEyeclose;
	@BindView(R.id.register_hint)
	TextView registerHint;
	@BindView(R.id.register_next)
	TextView registerNext;

	private boolean isEyeClosed = true;

	@Inject
	ResetPresenter presenter;

	Map<String, Object> hashMap;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ButterKnife.bind(this);
		beginAll();
	}

	@Override
	protected void beginAll(){
		registerEyeclose.setOnClickListener(this);

		setTitle();
		setText();
		observeEye();
	}

	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerResetComponent.builder().appComponent(appComponent)
							.resetModule(new ResetModule(this)).build()
							.inject(this);
	}


	@Override
	public void setTitle(){
		presenter.setTitle((TextView) registerActionbar
				.findViewById(R.id.actionbar_title));
	}

	@Override
	public void observeEye(){
		presenter.eye(isEyeClosed, registerEyeclose, registerPwd);
	}

	@Override
	public void setText(){
		presenter.setText(registerSetpwd, registerHint, registerNext);
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
			case R.id.register_eyeclose:
				if(isEyeClosed){
					isEyeClosed = false;
				}else{
					isEyeClosed = true;
				}
				observeEye();
				break;
		}
	}
}
