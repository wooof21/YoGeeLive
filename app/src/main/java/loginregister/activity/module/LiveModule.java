package loginregister.activity.module;

import android.app.Activity;
import android.content.Intent;
import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.presenter.StreamingPresenter;
import loginregister.activity.view.StreamingView;

/**
 * Created by Administrator on 2016/8/10.
 */
@Module
public class LiveModule{

	private Activity activity;
	private StreamingView view;
	private Class clazz;

	public LiveModule(Activity activity, StreamingView view, Class clazz){
		this.activity = activity;
		this.view = view;
		this.clazz = clazz;
	}

	@Provides
	@ActivityScope
	Intent provideIntent(){
		return new Intent(activity, clazz);
	}

	@Provides
	@ActivityScope
	StreamingPresenter providePresenter(){
		return new StreamingPresenter(view);
	}
}
