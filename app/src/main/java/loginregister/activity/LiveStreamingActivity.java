package loginregister.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.*;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.livestreamingtestreceiver.app.IHeartBeatService;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import constant.RestService;
import library.tool.clm.UI.LikeLayout;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerLiveComponent;
import loginregister.activity.module.LiveModule;
import loginregister.activity.presenter.StreamingPresenter;
import loginregister.activity.view.StreamingView;
import service.HeartBeatService;

import javax.inject.Inject;

public class LiveStreamingActivity extends BaseActivity
		implements StreamingView, StreamingStateChangedListener{

	@BindView(R.id.cameraPreview_surfaceView)
	GLSurfaceView cameraPreviewSurfaceView;
	@BindView(R.id.cameraPreview_afl)
	AspectFrameLayout cameraPreviewAfl;
	@BindView(R.id.camera_cameraswitch)
	TextView cameraCameraswitch;
	@BindView(R.id.camera_lightswitch)
	TextView cameraLightswitch;
	@BindView(R.id.bottom_chat)
	ImageView bottomChat;
	@BindView(R.id.camera_bottom_fl)
	FrameLayout cameraBottomFl;
	@BindView(R.id.camera_stream_et)
	EditText cameraStreamEt;
	@BindView(R.id.camera_stream_send)
	TextView cameraStreamSend;
	@BindView(R.id.camera_msg_ll)
	LinearLayout cameraMsgLl;
	@BindView(R.id.camera_lllllllll)
	LinearLayout cameraLllllllll;
	@BindView(R.id.camera_stream_chat_list)
	TextView cameraStreamChatList;
	@BindView(R.id.camera_like)
	LikeLayout cameraLike;
	@BindView(R.id.content)
	RelativeLayout content;
	@Inject
	Intent intent;
	@Inject
	RestService service;
	@Inject
	StreamingPresenter presenter;
	private IHeartBeatService iHeartBeatService;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_streaming);
		ButterKnife.bind(this);
		beginAll();
	}

	@Override
	protected void beginAll(){
		presenter.setStreamingProfile(this, getIntent().getExtras().getString(
				"stream_json_str"), cameraPreviewAfl, cameraPreviewSurfaceView);
	}

	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerLiveComponent.builder().appComponent(appComponent).liveModule(
				new LiveModule(this, this, HeartBeatService.class)).build()
						   .inject(this);
	}

	@Override
	protected void onStart(){
		super.onStart();
		presenter.onStart(this, intent, conn, BIND_AUTO_CREATE);
		presenter.initReceiver(cameraStreamChatList);
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
	public void onStreamingStateChanged(MediaStreamingManager manager){
		manager.setStreamingStateListener(this);
	}

	@Override
	public void onStateChanged(StreamingState streamingState, Object o){
		switch(streamingState){
			case PREPARING:
				Log.e("Stream ", "PREPARING...");
				break;
			case READY:
				// start streaming when READY
				Log.e("Stream ", "READY...");
				presenter.startStreaming();
				break;
			case CONNECTING:
				Log.e("Stream ", "CONNECTING...");
				break;
			case STREAMING:
				// The av packet had been sent.
				Log.e("Stream ", "STREAMING...");
				break;
			case SHUTDOWN:
				// The streaming had been finished.
				Log.e("Stream ", "SHUTDOWN...");
				break;
			case IOERROR:
				// Network connect error.
				Log.e("Stream ", "IOERROR...");
				break;
			case SENDING_BUFFER_EMPTY:
				Log.e("Stream ", "SENDING_BUFFER_EMPTY...");
				break;
			case SENDING_BUFFER_FULL:
				Log.e("Stream ", "SENDING_BUFFER_FULL...");
				break;
			case AUDIO_RECORDING_FAIL:
				// Failed to record audio.
				Log.e("Stream ", "AUDIO_RECORDING_FAIL...");
				break;
			case OPEN_CAMERA_FAIL:
				// Failed to open camera.
				Log.e("Stream ", "开启摄像头失败回调...");
				break;
			case DISCONNECTED:
				// The socket is broken while streaming
				Log.e("Stream ", "DISCONNECTED...");
				break;
		}
	}
}
