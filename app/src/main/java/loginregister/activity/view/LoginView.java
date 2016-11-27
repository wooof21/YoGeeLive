package loginregister.activity.view;

import android.app.Activity;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface LoginView{
	void requestPermission(Activity activity);

	void loginThirdParty(SHARE_MEDIA platform);

	void onBeginning();

	void onLoginScuess(String name, String phone, String pwd, String id,
					   String type);

	void onLoginFailed(String msg);

	void onFinished();
}
