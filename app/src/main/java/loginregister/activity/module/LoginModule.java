package loginregister.activity.module;

import application.scope.ActivityScope;
import com.umeng.socialize.UMShareAPI;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.LoginActivity;
import loginregister.activity.presenter.LoginPresenter;
import loginregister.activity.view.LoginView;

/**
 * Created by Administrator on 2016/8/5.
 */
@Module
public class LoginModule{

	private LoginActivity activity;
	private LoginView view;

	public LoginModule(LoginActivity activity, LoginView view){
		this.activity = activity;
		this.view = view;
	}

	@Provides
	@ActivityScope
	LoginView provideLoginView(){
		return view;
	}

	@Provides
	@ActivityScope
	LoginActivity provideLoginActivity(){
		return activity;
	}

	@Provides
	@ActivityScope
	UMShareAPI provideUMShareAPI(){
		return UMShareAPI.get(activity);
	}

	@Provides
	@ActivityScope
	LoginPresenter provideLoginPresenter(UMShareAPI api){
		return new LoginPresenter(view, activity, api);
	}

}
