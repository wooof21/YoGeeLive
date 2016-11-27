package loginregister.activity.component;

import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.RegisterActivity;
import loginregister.activity.module.RegisterModule;
import loginregister.activity.presenter.RegisterPresenter;

/**
 * Created by Administrator on 2016/8/5.
 */
@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent{
	void inject(RegisterActivity activity);

	RegisterPresenter presenter();
}
