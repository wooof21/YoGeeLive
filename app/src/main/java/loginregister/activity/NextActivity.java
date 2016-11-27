package loginregister.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import application.BaseActivity;
import application.component.AppComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import live.yogee.com.yogeelive.R;
import loginregister.activity.component.DaggerNextComponent;
import loginregister.activity.module.NextModule;
import loginregister.activity.presenter.NextPresenter;
import loginregister.activity.view.NextView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import ui.RoundImageView;

import javax.inject.Inject;

public class NextActivity extends BaseActivity
		implements View.OnClickListener, NextView{


	@BindView(R.id.next_actionbar)
	RelativeLayout nextActionbar;
	@BindView(R.id.actionbar_back)
	ImageView actionbarBack;
	@BindView(R.id.actionbar_title)
	TextView actionbarTitle;
	@BindView(R.id.next_head)
	RoundImageView nextHead;
	@BindView(R.id.next_leftBtn)
	Button nextLeftBtn;
	@BindView(R.id.next_rightBtn)
	Button nextRightBtn;
	@BindView(R.id.next_enter)
	TextView nextEnter;
	private String sex = "male";

	//	private NextPresenter presenter;
	@Inject
	NextPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		ButterKnife.bind(this);
		beginAll();
	}

	@Override
	protected void beginAll(){
		nextLeftBtn.setOnClickListener(this);
		nextRightBtn.setOnClickListener(this);

		setTitle();
		randomHead();
	}


	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerNextComponent.builder().appComponent(appComponent)
						   .nextModule(new NextModule(this)).build()
						   .inject(this);
	}


	@Override
	public void onClick(View v){
		switch(v.getId()){
			case R.id.next_leftBtn:
				sex = "male";
				observeButtonClick();
				break;
			case R.id.next_rightBtn:
				sex = "female";
				observeButtonClick();
				break;
		}
	}

	private void observeButtonClick(){
		Observable.just(sex).observeOn(AndroidSchedulers.mainThread())
				  .subscribe(new Subscriber<String>(){
					  @Override
					  public void onCompleted(){

					  }

					  @Override
					  public void onError(Throwable e){

					  }

					  @Override
					  public void onNext(String s){
						  if(s.equals("male")){
							  left(nextLeftBtn, nextRightBtn);
						  }else{
							  right(nextLeftBtn, nextRightBtn);
						  }
					  }
				  });
	}

	@Override
	protected void onPause(){
		super.onPause();
		presenter.onPause();
	}

	@Override
	public void left(View left, View right){
		presenter.left(left, right);
	}

	@Override
	public void right(View left, View right){
		presenter.right(left, right);
	}

	@Override
	public void randomHead(){
		presenter.randomHead(nextHead);
	}

	@Override
	public void setTitle(){
		presenter.setTitle(
				(TextView) nextActionbar.findViewById(R.id.actionbar_title));
	}
}
