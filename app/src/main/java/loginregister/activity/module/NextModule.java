package loginregister.activity.module;

import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.presenter.NextPresenter;
import loginregister.activity.view.NextView;

/**
 * Created by Administrator on 2016/8/5.
 */
@Module
public class NextModule{
	private NextView view;

	public NextModule(NextView view){
		this.view = view;
	}

	@Provides
	@ActivityScope
	NextView provideNextView(){
		return view;
	}

	@Provides
	@ActivityScope
	NextPresenter provideNextPresenter(){
		return  new NextPresenter(view);
	}
}
