package loginregister.activity.component;

import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.NextActivity;
import loginregister.activity.module.NextModule;
import loginregister.activity.presenter.NextPresenter;

/**
 * Created by Administrator on 2016/8/5.
 */
@ActivityScope
@Component(modules = NextModule.class, dependencies = AppComponent.class)
public interface NextComponent{
	void inject(NextActivity activity);
	NextPresenter presenter();
}
