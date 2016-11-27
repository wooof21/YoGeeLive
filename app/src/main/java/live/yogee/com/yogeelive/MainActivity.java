package live.yogee.com.yogeelive;

import adapters.MainGvAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import application.BaseActivity;
import application.component.AppComponent;
import beans.MainListCallBackBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import constant.RestService;
import loginregister.activity.LoginActivity;
import loginregister.activity.RegisterActivity;
import view.banner.CircleFlowIndicator;
import view.banner.ViewFlow;
import view.ptr.PullToRefreshLayout;
import view.ptr.PullableScrollView;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
		implements MainView, PullToRefreshLayout.OnRefreshListener{


	@BindView(R.id.main_viewflow)
	ViewFlow mainViewflow;
	@BindView(R.id.main_viewflowindic)
	CircleFlowIndicator mainViewflowindic;
	@BindView(R.id.main_framelayout)
	FrameLayout mainFramelayout;
	@BindView(R.id.main_notice)
	FrameLayout mainNotice;
	@Inject
	MainPresenter presenter;
	@Inject
	RestService service;
	@BindView(R.id.main_graidview)
	GridView mainGraidview;
	@BindView(R.id.sv)
	PullableScrollView sv;
	@BindView(R.id.loadmore_view)
	RelativeLayout loadmoreView;
	@BindView(R.id.refresh_view)
	PullToRefreshLayout refreshView;
	@Inject
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		findViewById(R.id.register)
				.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v){
						startActivity(new Intent(MainActivity.this,
												 RegisterActivity.class));
					}
				});

		findViewById(R.id.login).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(
						new Intent(MainActivity.this, LoginActivity.class));
			}
		});

		findViewById(R.id.live).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				presenter.startStream(MainActivity.this,
									  sp.getString("userid", ""),
									  sp.getString("type", ""), service);
			}
		});

		beginAll();
	}

	@Override
	protected void beginAll(){
		presenter.checkLogin(this, sp);
		sv.smoothScrollBy(0, 0);
		refreshView.setOnRefreshListener(this);
		presenter.prepareBanner(mainViewflow, mainViewflowindic);
		presenter.getList(sp.getString("userid", ""), sp.getString("type", ""),
						  service);
	}


	@Override
	protected void setupActivityComponent(AppComponent appComponent){
		DaggerMainComponent.builder().appComponent(appComponent)
						   .mainModule(new MainModule(this, this)).build()
						   .inject(this);
	}


	@Override
	public void setAdapter(final MainListCallBackBean bean){
		MainGvAdapter adapter = new MainGvAdapter(this, bean);
		mainGraidview.setAdapter(adapter);
		presenter
				.setOnItemClickListener(MainActivity.this, bean, mainGraidview);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout){
		presenter.getList(sp.getString("userid", ""), sp.getString("type", ""),
						  service);
		refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout){
		refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
	}
}
