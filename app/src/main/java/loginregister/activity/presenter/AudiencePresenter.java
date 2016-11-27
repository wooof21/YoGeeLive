package loginregister.activity.presenter;

import android.content.*;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;
import com.example.livestreamingtestreceiver.app.IHeartBeatService;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLNetworkManager;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import constant.Config;
import loginregister.activity.model.AudienceModel;
import loginregister.activity.view.AudienceView;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import service.HeartBeatService;

import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AudiencePresenter{

	private AudienceView view;
	private AudienceModel model;
	private BroadcastReceiver mReceiver;

	public AudiencePresenter(AudienceView view){
		this.view = view;
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
	}

	public void onPause(Context context, ServiceConnection conn){
		// 注销广播 最好在onPause上注销
		context.unregisterReceiver(mReceiver);
		// 注销服务
		context.unbindService(conn);
	}

	public void onStop(Context context, Intent intent){
		context.stopService(intent);
	}

	public void startDNS(final Context context){
		Observable.create(new Observable.OnSubscribe<Void>(){
			@Override
			public void call(Subscriber<? super Void> subscriber){

			}
		}).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Void>(){
			@Override
			public void onCompleted(){

			}

			@Override
			public void onError(Throwable e){

			}

			@Override
			public void onNext(Void aVoid){
				try{
					PLNetworkManager.getInstance().setDnsServer(
							"100027e.live-rtmp.v1.pilidns.com");
					String[] strings = new String[]{Config.LIVE_BASE_URL};
					PLNetworkManager.getInstance()
									.startDnsCacheService(context, strings);
				}catch(UnknownHostException e){
					e.printStackTrace();
				}
			}
		});
	}

	public void initAvOption(AVOptions options, PLVideoTextureView plvv){
		options = new AVOptions();
		options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
		options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
		options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
		options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
		options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
		options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
		options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
		options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
		plvv.setAVOptions(options);
	}

	public void initPlvv(PLVideoTextureView plvv, String id){
		plvv.setDisplayAspectRatio(
				PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
		plvv.setMirror(false);

		plvv.setVideoPath(Config.LIVE_BASE_URL + id);
	}

	public void sendMsg(final String msg, final EditText et,
						final IHeartBeatService iHeartBeatService){
		Observable.just(msg).observeOn(AndroidSchedulers.mainThread())
				  .subscribeOn(Schedulers.io())
				  .subscribe(new Subscriber<String>(){
					  @Override
					  public void onCompleted(){

					  }

					  @Override
					  public void onError(Throwable e){

					  }

					  @Override
					  public void onNext(String s){
						  try{
							  JSONObject job = new JSONObject();
							  job.put("userid", "10001");
							  job.put("username", "androidtest");
							  job.put("liveid", "00000");
							  job.put("type", "1");
							  if(s.length() != 0){
								  if(s.contains("\n")){
									  s = s.replace("\n", " ");
								  }
								  job.put("msg", s);
								  if(iHeartBeatService
										  .sendMessage(job.toString())){
									  et.setText("");
								  }else{
									  et.setText(s);
								  }
							  }

						  }catch(JSONException e){
							  e.printStackTrace();
						  }catch(RemoteException e){
							  e.printStackTrace();
						  }
					  }
				  });
	}


}
