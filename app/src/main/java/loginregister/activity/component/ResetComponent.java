package loginregister.activity.component;

import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.ResetActivity;
import loginregister.activity.module.ResetModule;
import loginregister.activity.presenter.ResetPresenter;

/**
 * Created by Administrator on 2016/8/5.
 */
@ActivityScope
@Component(modules = ResetModule.class, dependencies = AppComponent.class)
public interface ResetComponent{
	void inject(ResetActivity activity);

	ResetPresenter presenter();
}
