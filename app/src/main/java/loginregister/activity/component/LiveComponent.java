package loginregister.activity.component;

import android.content.Intent;
import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.LiveStreamingActivity;
import loginregister.activity.module.LiveModule;
import loginregister.activity.presenter.StreamingPresenter;

/**
 * Created by Administrator on 2016/8/10.
 */
@Component(modules = LiveModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface LiveComponent{
	void inject(LiveStreamingActivity activity);

	StreamingPresenter presenter();
	Intent intent();
}
