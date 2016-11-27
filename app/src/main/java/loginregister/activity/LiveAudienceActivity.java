package loginregister.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.livestreamingtestreceiver.app.IHeartBeatService;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerAudienceComponent;
import loginregister.activity.module.AudienceModule;
import loginregister.activity.presenter.AudiencePresenter;
import loginregister.activity.view.AudienceView;
import service.HeartBeatService;

import javax.inject.Inject;

public class LiveAudienceActivity extends BaseActivity
		implements AudienceView, PLMediaPlayer.OnPreparedListener,
		PLMediaPlayer.OnInfoListener, PLMediaPlayer.OnCompletionListener,
		PLMediaPlayer.OnVideoSizeChangedListener, PLMediaPlayer.OnErrorListener,
		View.OnClickListener{

	@BindView(R.id.PLVideoTextureView)
	com.pili.pldroid.player.widget.PLVideoTextureView PLVideoTextureView;
	@BindView(R.id.watch_chat_list)
	TextView watchChatList;
	@BindView(R.id.watch_et)
	EditText watchEt;
	@BindView(R.id.watch_send)
	TextView watchSend;
	@BindView(R.id.bottom_2)
	ImageView bottom2;
	@BindView(R.id.audience_parent)
	RelativeLayout parent;
	@BindView(R.id.bottom_3)
	ImageView bottom3;
	@Inject
	Intent intent;
	@Inject
	AudiencePresenter presenter;
	private IHeartBeatService iHeartBeatService;
	private AVOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_audience);
		ButterKnife.bind(this);

		beginAll();

	}

	@Override
	protected void beginAll(){
		presenter.startDNS(this);
		presenter.initAvOption(options, PLVideoTextureView);
		System.out.println("id   " + getIntent().getStringExtra("streamid"));
		presenter.initPlvv(PLVideoTextureView,
						   getIntent().getStringExtra("streamid"));

		PLVideoTextureView.setOnPreparedListener(this);
		PLVideoTextureView.setOnInfoListener(this);
		PLVideoTextureView.setOnCompletionListener(this);
		PLVideoTextureView.setOnVideoSizeChangedListener(this);
		PLVideoTextureView.setOnErrorListener(this);
		watchSend.setOnClickListener(this);

		bottom2.setOnClickListener(this);
		parent.setOnClickListener(this);
	}

	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerAudienceComponent.builder().appComponent(appComponent)
							   .audienceModule(new AudienceModule(this, this,
																  HeartBeatService.class))
							   .build().inject(this);
	}

	@Override
	protected void onStart(){
		super.onStart();
		presenter.onStart(this, intent, conn, BIND_AUTO_CREATE);
		presenter.initReceiver(watchChatList);
	}

	@Override
	protected void onResume(){
		super.onResume();
		presenter.onResume(this);
	}

	@Override
	protected void onPause(){
		super.onPause();
		presenter.onPause(this, conn);
	}

	@Override
	protected void onStop(){
		super.onStop();
		presenter.onStop(this, intent);

	}

	private ServiceConnection conn = new ServiceConnection(){
		@Override
		public void onServiceDisconnected(ComponentName name){
			// 未连接为空
			iHeartBeatService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service){
			// 已连接
			iHeartBeatService = IHeartBeatService.Stub.asInterface(service);
		}
	};

	@Override
	public void onPrepared(PLMediaPlayer plMediaPlayer){
		PLVideoTextureView.start();
	}

	@Override
	public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1){
		switch(i){
			case 1:
				Log.d("onInfo", "未知消息");
				break;
			case 3:
				Log.d("onInfo", "第一帧视频已成功渲染");
				break;
			case 701:
				Log.d("onInfo", "开始缓冲");
				break;
			case 702:
				Log.d("onInfo", "停止缓冲");
				break;
			case 10001:
				Log.d("onInfo", "获取到视频的播放角度");
				break;
			case 10002:
				Log.d("onInfo", "第一帧音频已成功播放");
				break;
		}
		return false;
	}

	@Override
	public void onCompletion(PLMediaPlayer plMediaPlayer){
		Log.d("onCompletion", "直播结束");
	}

	@Override
	public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int i, int i1){
		Log.e("width: ", "" + i);
		Log.e("height: ", "" + i1);
	}

	@Override
	public boolean onError(PLMediaPlayer plMediaPlayer, int i){
		switch(i){
			case -1:
				Log.d("onError", "未知错误");
				break;
			case -2:
				Log.d("onError", "无效的 URL");
				break;
			case -5:
				Log.d("onError", "网络异常");
				break;
			case -11:
				Log.d("onError", "与服务器连接断开");
				break;
			case -541478725:
				Log.d("onError", "空的播放列表");
				break;
			case -875574520:
				Log.d("onError", "播放资源不存在");
				break;
			case -111:
				Log.d("onError", "服务器拒绝连接");
				break;
			case -110:
				Log.d("onError", "连接超时");
				break;
			case -825242872:
				Log.d("onError", "未授权，播放一个禁播的流");
				break;
			case -2001:
				Log.d("onError", "播放器准备超时");
				break;
			case -2002:
				Log.d("onError", "读取数据超时");
				break;

		}
		return false;
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
			case R.id.watch_send:
				presenter.sendMsg(watchEt.getText().toString(), watchEt,
								  iHeartBeatService);
				break;
			case R.id.bottom_2:
				break;
			case R.id.audience_parent:
				break;
		}
	}



	@Override
	public void onBackPressed(){
		super.onBackPressed();
		finish();
	}
}
