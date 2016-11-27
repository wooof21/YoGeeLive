package loginregister.activity.model;

import com.qiniu.pili.droid.streaming.MediaStreamingManager;

/**
 * Created by Administrator on 2016/8/10.
 */
public class StreamingModel{



	public void startStreaming(final MediaStreamingManager mCameraStreamingManager){
		new Thread(new Runnable(){
			@Override
			public void run(){
				if(mCameraStreamingManager != null){
					mCameraStreamingManager.startStreaming();
				}
			}
		}).start();
//		Observable.create(new Observable.OnSubscribe<Void>(){
//			@Override
//			public void call(Subscriber<? super Void> subscriber){
//				subscriber.onNext(null);
//				subscriber.onCompleted();
//			}
//		}).subscribeOn(Schedulers.io())
//				  .observeOn(AndroidSchedulers.mainThread())
//				  .subscribe(new Subscriber<Void>(){
//					  @Override
//					  public void onCompleted(){
//
//					  }
//
//					  @Override
//					  public void onError(Throwable e){
//
//					  }
//
//					  @Override
//					  public void onNext(Void aVoid){
//						  if(mCameraStreamingManager != null){
//							  mCameraStreamingManager.startStreaming();
//						  }
//					  }
//				  });
	}
}
