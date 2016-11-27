package loginregister.activity.module;

import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.presenter.RegisterPresenter;
import loginregister.activity.view.RegisterView;

/**
 * Created by Administrator on 2016/8/5.
 */
@Module
public class RegisterModule{

	private RegisterView view;

	public RegisterModule(RegisterView view){
		this.view = view;
	}

	@Provides
	@ActivityScope
	RegisterPresenter provideRegisterPresenter(){
		return new RegisterPresenter(view);
	}

}
