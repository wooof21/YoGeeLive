package loginregister.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import constant.RestService;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerRegisterComponent;
import loginregister.activity.module.RegisterModule;
import loginregister.activity.presenter.RegisterPresenter;
import loginregister.activity.view.RegisterView;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity
		implements RegisterView, View.OnClickListener{


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
	//	private RegisterPresenter presenter;
	private boolean isEyeClosed = true;

	@Inject
	RegisterPresenter presenter;
	@Inject
	RestService service;

	private Uri SMS_INBOX = Uri.parse("content://sms/");
	private SmsObserver smsObserver;
	private int count = 0;
	private Timer timer;
	private TimerTask timerTask;


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
		registerGetcode.setOnClickListener(this);
		registerNext.setOnClickListener(this);
		setTitle();
	}


	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerRegisterComponent.builder().appComponent(appComponent)
							   .registerModule(new RegisterModule(this)).build()
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
	public void getCode(){
		presenter.getCode(registerPhone.getText().toString().trim(), service);
	}

	@Override
	public void onWrongLengthView(){
		Toast.makeText(this, "手机号长度小于11位!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSuccessView(){
		Toast.makeText(this, "验证码获取成功!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEmptyView(){
		Toast.makeText(this, "请填写手机号!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRegisterPhoneView(){
		Toast.makeText(this, "此手机号已被注册!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailed(){
		Toast.makeText(this, "获取验证码失败, 请重试!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSend(){
		Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
		startCount(60);
		smsObserver = new SmsObserver(this, msgHandler);
		getContentResolver()
				.registerContentObserver(SMS_INBOX, true, smsObserver);
	}

	@Override
	public void onEmptyCode(){
		Toast.makeText(this, "验证码为空, 请填写验证码!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRegisterFailed(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRegisterScuess(){
		Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
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
			case R.id.register_getcode:
				presenter.getCode(registerPhone.getText().toString(), service);
				break;
			case R.id.register_next:
				presenter.register(registerPhone.getText().toString(),
								   registerPwd.getText().toString(),
								   registerCertifycode.getText().toString(),
								   service);
				break;
		}
	}


	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);

		}
	};

	/**
	 * 获取短信, 倒计时handler
	 */
	private Handler msgHandler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
				case 1: // 开始倒计时
					startCount(60);
					break;
				case 2: // 计时中
					registerGetcode.setText("重新发送" + "(" + count + "s)");
					registerGetcode.invalidate();
					registerGetcode.setClickable(false);
					break;
				case 3: // 计时结束
					registerGetcode.setText("重新发送");
					timer.cancel();
					registerGetcode.setClickable(true);
					registerGetcode.setOnClickListener(RegisterActivity.this);
					break;
				case 5: // 自动填写验证码
					String code = (String) msg.obj;
					registerCertifycode.setText(code);
					break;
				default:
					break;
			}
		}

	};

	/**
	 * 倒计时
	 *
	 * @param
	 */
	private void startCount(int c){

		count = c;
		timer = new Timer();
		timerTask = new TimerTask(){
			@Override
			public void run(){
				if(count > 0){
					Message msg = msgHandler.obtainMessage();
					msg.what = 2;
					msgHandler.sendMessage(msg);
				}else{

					Message msg = msgHandler.obtainMessage();
					msg.what = 3;
					msgHandler.sendMessage(msg);
				}
				count--;

			}
		};
		timer.schedule(timerTask, 0, 1000);
	}

	/**
	 * 监听短信
	 */
	class SmsObserver extends ContentObserver{

		public SmsObserver(Context context, Handler handler){
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange){
			super.onChange(selfChange);
			// 每当有新短信到来时，使用我们获取短消息的方法
			readSMS();
		}
	}

	/**
	 * 读取短信
	 *
	 * @param
	 */
	private void readSMS(){
		ContentResolver cr = getContentResolver();
		String[] projection = new String[]{"body"};// "_id", "address",
		// "person",, "date",
		// "type
		String where = "address = '10690032980066' AND date >  " + (System
				.currentTimeMillis() - 10 * 60 * 1000);
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		if(null == cur)
			return;
		if(cur.moveToNext()){
			// String number =
			// cur.getString(cur.getColumnIndex("address"));//手机号
			// String name =
			// cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
			String body = cur.getString(cur.getColumnIndex("body"));
			System.out.println(body);
			// 这里我是要获取自己短信服务号码中的验证码~~
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]{4}");
			Matcher matcher = pattern.matcher(body);
			if(matcher.find()){ // 发送验证码
				String res = matcher.group().substring(0, 4);
				Message msg = msgHandler.obtainMessage();
				msg.what = 5;
				msg.obj = res;
				msg.sendToTarget();
			}
		}
	}
}
