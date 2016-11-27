package loginregister.activity.module;

import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.presenter.ResetPresenter;
import loginregister.activity.view.ResetView;

/**
 * Created by Administrator on 2016/8/5.
 */
@Module
public class ResetModule{
	private ResetView view;

	public ResetModule(ResetView view){
		this.view = view;
	}

	@Provides
	@ActivityScope
	ResetPresenter provideResetPresenter(){
		return new ResetPresenter(view);
	}

}
