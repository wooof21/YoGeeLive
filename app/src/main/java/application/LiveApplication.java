package application;

import android.app.Application;
import android.content.Context;
import application.component.AppComponent;
import application.component.DaggerAppComponent;
import application.module.AppModule;
import cn.smssdk.SMSSDK;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by Administrator on 2016/8/2.
 */
public class LiveApplication extends Application{

	private AppComponent appComponent;

	@Override
	public void onCreate(){
		super.onCreate();
		appComponent = DaggerAppComponent.builder()
										 .appModule(new AppModule(this))
										 .build();
		initUM();
		SMSSDK.initSDK(this, "15dea7e110662",
					   "0f0bbe20a45d6d1cc4c61036ff2a0ecb");
		StreamingEnv.init(getApplicationContext());
	}

	private void initUM(){
		PlatformConfig.setWeixin("wxf55e6a099c75c93c",
								 "d5828ce836a5c74ab198188a7bdbda68");
		PlatformConfig
				.setSinaWeibo("142285859", "da5e05fb2718e6871d3f6224a58707b8");
		Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
		PlatformConfig.setQQZone("1105515601", "W1uOJlybfqrCtr4A");
	}

	public AppComponent getAppComponent(){
		return appComponent;
	}

	public static LiveApplication get(Context context){
		return (LiveApplication) context.getApplicationContext();
	}


}
