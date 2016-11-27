package live.yogee.com.yogeelive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import beans.MainListBean;
import beans.MainListCallBackBean;
import beans.StreamBean;
import beans.StreamCallBackBean;
import constant.RestService;
import loginregister.activity.LiveStreamingActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MainModel{
	private OnDataLoadDone listener;

	public MainModel(OnDataLoadDone listener){
		this.listener = listener;
	}

	public void getList(String uid, String type, RestService service){
		MainListBean bean = new MainListBean();
		bean.setUserid(uid);
		bean.setType(type);

		Observable<MainListCallBackBean> response = service.mainList(bean);
		response.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<MainListCallBackBean>(){
					@Override
					public void onCompleted(){

					}

					@Override
					public void onError(Throwable e){

					}

					@Override
					public void onNext(MainListCallBackBean bean){
						System.out.println("state " + bean.getState());
						System.out.println("msg  " + bean.getMsg());
						System.out.println(
								"list  0  name  " + bean.getData().getList()
														.get(0).getName());
						System.out.println(
								"list  0  streamid  " + bean.getData().getList()
															.get(0)
															.getStreamid());
						System.out.println(
								"list  0  id  " + bean.getData().getList()
													  .get(0).getId());
						setGv(bean);
					}
				});
	}

	private void setGv(MainListCallBackBean bean){
		listener.setAdapter(bean);
	}

	public void requestStream(final Context context, String uid, String type,
			RestService
			service){
		StreamBean bean = new StreamBean();
		bean.setUserid(uid);
		bean.setType(type);
		final Observable<StreamCallBackBean> response = service.requestStream(bean);
		response.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<StreamCallBackBean>(){
					@Override
					public void onCompleted(){

					}

					@Override
					public void onError(Throwable e){
						Log.e("error   ", e.toString());
					}

					@Override
					public void onNext(StreamCallBackBean bean){
						Log.e("onnext  ", bean.toString());
					   context.startActivity(
							   new Intent(context, LiveStreamingActivity.class)
									   .putExtra("stream_json_str", bean.toString()));
					}
				});
	}


}
