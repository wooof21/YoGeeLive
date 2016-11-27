package live.yogee.com.yogeelive;

import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;

/**
 * Created by Administrator on 2016/8/9.
 */
@Component(modules = MainModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface MainComponent{
	void inject(MainActivity activity);
	MainPresenter presenter();
}
