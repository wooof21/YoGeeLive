package loginregister.activity.component;

import android.content.Intent;
import application.component.AppComponent;
import application.scope.ActivityScope;
import dagger.Component;
import loginregister.activity.LiveAudienceActivity;
import loginregister.activity.module.AudienceModule;
import loginregister.activity.presenter.AudiencePresenter;

/**
 * Created by Administrator on 2016/8/10.
 */
@Component(modules = AudienceModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface AudienceComponent{
	void inject(LiveAudienceActivity activity);
	Intent intent();
	AudiencePresenter presenter();
}
