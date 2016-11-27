package loginregister.activity.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import constant.RestService;
import library.tool.clm.utils.SDHelper;
import loginregister.activity.model.LoginModel;
import loginregister.activity.model.OnLoginListener;
import loginregister.activity.view.LoginView;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */
public class LoginPresenter implements OnLoginListener{

	private LoginView view;
	private Activity activity;
	private UMShareAPI mShareAPI;
	private LoginModel model;

	public LoginPresenter(LoginView view, Activity activity,
						  UMShareAPI mShareAPI){
		this.view = view;
		this.activity = activity;
		this.mShareAPI = mShareAPI;
		model = new LoginModel(this);
	}

	public void login(String phone, String pwd, RestService service){
		model.login(phone, pwd, service);
	}

	public void permission(Activity activity){
		String[] mPermissionList = new String[]{
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
				Manifest.permission.READ_PHONE_STATE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.SET_DEBUG_APP,
				Manifest.permission.SYSTEM_ALERT_WINDOW,
				Manifest.permission.GET_ACCOUNTS};
		ActivityCompat.requestPermissions(activity, mPermissionList, 100);
	}

	public void loginThirdParty(SHARE_MEDIA platform, Activity activity){
		mShareAPI.doOauthVerify(activity, platform, umAuthListener);
	}

	private UMAuthListener umAuthListener = new UMAuthListener(){
		@Override
		public void onComplete(SHARE_MEDIA platform, int action,
							   Map<String, String> data){
			Toast.makeText(activity, "Authorize succeed " + platform.name(),
						   Toast.LENGTH_SHORT).show();
			mShareAPI.getPlatformInfo(activity, platform, umDataListener);
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t){
			Toast.makeText(activity, "Authorize fail " + platform.name(),
						   Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action){
			Toast.makeText(activity, "Authorize cancel " + platform.name(),
						   Toast.LENGTH_SHORT).show();
		}
	};

	private UMAuthListener umDataListener = new UMAuthListener(){
		@Override
		public void onComplete(SHARE_MEDIA share_media, int i,
							   Map<String, String> map){
			if(map != null){
				Log.d("auth callbacl", "getting data");
				Toast.makeText(activity, map.toString(), Toast.LENGTH_SHORT)
					 .show();
				SDHelper.writeToSDByBufferedWriter(map.toString());
			}
		}

		@Override
		public void onError(SHARE_MEDIA share_media, int i,
							Throwable throwable){

		}

		@Override
		public void onCancel(SHARE_MEDIA share_media, int i){

		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}

	public void saveInfo(SharedPreferences.Editor
			editor, String phone, String pwd, String id, String type){
		editor.putString("phone", phone);
		editor.putString("password", pwd);
		editor.putString("userid", id);
		editor.putString("type", type);
		editor.commit();
	}

	@Override
	public void onBeginning(){
		view.onBeginning();
	}

	@Override
	public void onLoginScuess(String name, String phone, String pwd, String id,
							  String type){
		view.onLoginScuess(name, phone, pwd, id, type);
	}


	@Override
	public void onLoginFailed(String msg){
		view.onLoginFailed(msg);
	}

	@Override
	public void onFinished(){
		view.onFinished();
	}
}
