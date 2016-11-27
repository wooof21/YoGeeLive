package loginregister.activity.component;

import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.LoginActivity;
import loginregister.activity.module.LoginModule;
import loginregister.activity.presenter.LoginPresenter;

/**
 * Created by Administrator on 2016/8/5.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent{
	LoginActivity inject(LoginActivity activity);

	LoginPresenter presenter();
}
