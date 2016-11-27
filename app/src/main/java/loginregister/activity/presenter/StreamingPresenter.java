package loginregister.activity.presenter;

import android.content.*;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import com.qiniu.pili.droid.streaming.*;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import loginregister.activity.model.StreamingModel;
import loginregister.activity.view.StreamingView;
import org.json.JSONException;
import org.json.JSONObject;
import service.HeartBeatService;

/**
 * Created by Administrator on 2016/8/10.
 */
public class StreamingPresenter{

	private StreamingView view;
	private StreamingModel model;
	private BroadcastReceiver mReceiver;
	private MediaStreamingManager mCameraStreamingManager;

	public StreamingPresenter(StreamingView view){
		this.view = view;
		model = new StreamingModel();
	}

	public void initReceiver(final TextView watchChatList){
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent){
				String action = intent.getAction();
				watchChatList.setMovementMethod(new ScrollingMovementMethod());
				final int scrollAmount = watchChatList.getLayout().getLineTop(
						watchChatList.getLineCount()) - watchChatList
						.getHeight();
				// 消息广播
				if(action.equals(HeartBeatService.MESSAGE_ACTION)){
					String stringExtra = intent.getStringExtra("message");
					watchChatList.append("\n");
					watchChatList.append(stringExtra);
				}else if(action
						.equals(HeartBeatService.HEART_BEAT_ACTION)){// 心跳广播
					//tv.setText("正常心跳");
				}
				if(scrollAmount > 0){
					watchChatList.scrollTo(0, scrollAmount);
				}else{
					watchChatList.scrollTo(0, 0);
				}
			}
		};
	}

	public void onStart(Context context, Intent intent, ServiceConnection conn,
						int i){
		context.bindService(intent, conn, i);
	}

	public void onResume(Context context){
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(HeartBeatService.HEART_BEAT_ACTION);
		intentFilter.addAction(HeartBeatService.MESSAGE_ACTION);
		context.registerReceiver(mReceiver, intentFilter);
		mCameraStreamingManager.resume();
	}

	public void onPause(Context context, ServiceConnection conn){
		try{
			// 注销广播 最好在onPause上注销
			context.unregisterReceiver(mReceiver);
			// 注销服务
			context.unbindService(conn);
			mCameraStreamingManager.pause();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onStop(Context context, Intent intent){
		try{
			context.stopService(intent);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private CameraStreamingSetting getSetting(){
		CameraStreamingSetting setting = new CameraStreamingSetting();
		setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
			   .setContinuousFocusModeEnabled(true)
			   .setFocusMode(CameraStreamingSetting.FOCUS_MODE_CONTINUOUS_VIDEO)
			   .setFrontCameraMirror(true).setCameraPrvSizeLevel(
				CameraStreamingSetting.PREVIEW_SIZE_LEVEL.SMALL)
				.setRecordingHint(false)
			   .setCameraPrvSizeRatio(
					   CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9)
			   .setBuiltInFaceBeautyEnabled(true).setFaceBeautySetting(
				new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f));

		return setting;
	}

	private MicrophoneStreamingSetting getPhoneSetting(){
		MicrophoneStreamingSetting mSetting = new MicrophoneStreamingSetting();
		mSetting.setBluetoothSCOEnabled(true);
		return mSetting;
	}

	public void setStreamingProfile(Context context,
									String streamJsonStrFromServer,
									AspectFrameLayout afl,
									GLSurfaceView glSurfaceView){
		try{
			JSONObject mJSONObject = new JSONObject(streamJsonStrFromServer);
			StreamingProfile.Stream stream = new StreamingProfile.Stream(
					mJSONObject);
			StreamingProfile mProfile = new StreamingProfile();
			mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_LOW3)
					.setAudioQuality(StreamingProfile.AUDIO_QUALITY_LOW1)
					.setEncodingSizeLevel(
							StreamingProfile.VIDEO_QUALITY_LOW1)
					.setEncoderRCMode(
							StreamingProfile.EncoderRCModes.BITRATE_PRIORITY)
					.setStream(stream);  // You can invoke this before
			// startStreaming, but not in initialization phase.

			mCameraStreamingManager = new MediaStreamingManager(
					context, afl, glSurfaceView,
					AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
			mCameraStreamingManager.setVideoFilterType(
					CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);

			mCameraStreamingManager
					.prepare(getSetting(), getPhoneSetting(), mProfile);
			view.onStreamingStateChanged(mCameraStreamingManager);

		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	public void startStreaming(){
		model.startStreaming(mCameraStreamingManager);
	}

}
