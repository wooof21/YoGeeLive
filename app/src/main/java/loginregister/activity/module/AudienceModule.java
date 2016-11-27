package loginregister.activity.module;

import android.content.Intent;
import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;
import loginregister.activity.LiveAudienceActivity;
import loginregister.activity.presenter.AudiencePresenter;
import loginregister.activity.view.AudienceView;

/**
 * Created by Administrator on 2016/8/10.
 */
@Module
public class AudienceModule{

	private LiveAudienceActivity activity;
	private AudienceView view;
	private Class clazz;


	public AudienceModule(LiveAudienceActivity activity, AudienceView view,
						  Class clazz){
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
	AudiencePresenter providePresenter(){
		return  new AudiencePresenter(view);
	}
}
