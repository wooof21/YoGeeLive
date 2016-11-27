package loginregister.activity.presenter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import live.yogee.com.yogeelive.R;
import loginregister.activity.view.NextView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import ui.RoundImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/8/4.
 */
public class NextPresenter{

	private NextView view;
	private int count = 0;
	private Random random = new Random();
	private Timer timer;
	private TimerTask task;
	private int[] head = {R.mipmap.default_head1, R.mipmap.ic_launcher,
			R.mipmap.login_weibo, R.mipmap.login_qq, R.mipmap.login_weixin,
			R.mipmap.login_eye_close, R.mipmap.login_eye_open};

	public NextPresenter(NextView view){
		this.view = view;
	}

	public void left(View left, View right){
		left.setBackgroundResource(R.drawable.switch_left_checked);
		right.setBackgroundResource(R.drawable.switch_right_normal);
		((Button) left).setTextColor(Color.rgb(255, 255, 255));
		((Button) right).setTextColor(Color.rgb(255, 95, 92));
	}

	public void right(View left, View right){
		left.setBackgroundResource(R.drawable.switch_left_normal);
		right.setBackgroundResource(R.drawable.switch_button_right_checked);
		((Button) left).setTextColor(Color.rgb(255, 95, 92));
		((Button) right).setTextColor(Color.rgb(255, 255, 255));
	}

	public void randomHead(final View view){
		timer = new Timer(true);
		task = new TimerTask(){
			@Override
			public void run(){
				int num = random.nextInt(head.length);
				count++;
				setHead(num, view);
			}
		};
		timer.schedule(task, 100, 100);
	}

	private void setHead(int num, final View view){
		Observable.just(num).subscribeOn(AndroidSchedulers.mainThread())
				  .subscribe(new Subscriber<Integer>(){
								 @Override
								 public void onCompleted(){

								 }

								 @Override
								 public void onError(Throwable e){

								 }

								 @Override
								 public void onNext(Integer integer){
									 ((RoundImageView) view).setImageResource(
											 head[integer.intValue()]);
								 }
							 });

		Observable.just(count).subscribe(new Subscriber<Integer>(){
			@Override
			public void onCompleted(){

			}

			@Override
			public void onError(Throwable e){

			}

			@Override
			public void onNext(Integer integer){
				Log.e("integer ", integer.intValue() + "");
				if(integer.intValue() == head.length){
					count = 0;
					try{
						Thread.sleep(3000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void setTitle(TextView t){
		t.setText("完善个人资料");
	}

	public void onPause(){
		timer.cancel();
	}
}
