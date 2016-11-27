package live.yogee.com.yogeelive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import beans.MainListCallBackBean;
import constant.RestService;
import loginregister.activity.LiveAudienceActivity;
import loginregister.activity.LoginActivity;
import view.banner.CircleFlowIndicator;
import view.banner.ImagePagerAdapter;
import view.banner.ViewFlow;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MainPresenter implements OnDataLoadDone{

	private MainView view;
	private MainModel model;
	private Context context;

	private ArrayList<String> imageUrlList;
	private ArrayList<String> linkUrlArray;

	public MainPresenter(MainView view, Context context){
		this.view = view;
		model = new MainModel(this);
		this.context = context;
		imageUrlList = new ArrayList<String>();
		linkUrlArray = new ArrayList<String>();
	}

	public void prepareBanner(ViewFlow mainViewflow,
							  CircleFlowIndicator mainViewflowindic){
		imageUrlList
				.add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
		imageUrlList
				.add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
		imageUrlList
				.add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
		imageUrlList
				.add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");

		linkUrlArray
				.add("http://blog.csdn.net/finddreams/article/details/44301359");
		linkUrlArray
				.add("http://blog.csdn.net/finddreams/article/details/43486527");
		linkUrlArray
				.add("http://blog.csdn.net/finddreams/article/details/44648121");
		linkUrlArray
				.add("http://blog.csdn.net/finddreams/article/details/44619589");
		initBanner(mainViewflow, mainViewflowindic);
	}


	private void initBanner(ViewFlow mainViewflow,
							CircleFlowIndicator mainViewflowindic){

		mainViewflow.setAdapter(
				new ImagePagerAdapter(context, imageUrlList, linkUrlArray)
						.setInfiniteLoop(true));
		mainViewflow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
		// 我的ImageAdapter实际图片张数为3

		mainViewflow.setFlowIndicator(mainViewflowindic);
		mainViewflow.setTimeSpan(4500);
		mainViewflow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
		mainViewflow.startAutoFlowTimer(); // 启动自动播放
	}

	public void getList(String uid, String type, RestService service){
		model.getList(uid, type, service);
	}

	public void setOnItemClickListener(final Context context,
									   final MainListCallBackBean bean,
									   GridView gv){
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id){
				System.out.println(
						"streamid   " + bean.getData().getList().get(position)
											.getStreamid());
				startLive(context,
						  bean.getData().getList().get(position).getStreamid());
			}
		});
	}

	@Override
	public void setAdapter(MainListCallBackBean bean){
		view.setAdapter(bean);
	}

	public void startLive(Context context, String id){
		context.startActivity(new Intent(context, LiveAudienceActivity.class)
									  .putExtra("streamid", id));
	}


	public void startStream(Context context, String uid, String type,
							RestService
			service){
		model.requestStream(context, uid, type, service);
	}

	private boolean isLogin(SharedPreferences sp){
		String username = sp.getString("phone", "");
		String password = sp.getString("password", "");
		if("".equals(username) || "".equals(password)){
			return false;
		}
		return true;
	}

	public void checkLogin(Context context, SharedPreferences sp){
		if(!isLogin(sp)){
			context.startActivity(new Intent(context, LoginActivity.class));
		}
	}

}
