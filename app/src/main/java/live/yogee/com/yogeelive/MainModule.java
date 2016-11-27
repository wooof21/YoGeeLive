package live.yogee.com.yogeelive;

import android.content.Context;
import application.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/8/9.
 */
@Module
public class MainModule{

	private MainView view;
	private Context context;

	public MainModule(MainView view, Context context){
		this.view = view;
		this.context = context;
	}

	@Provides
	@ActivityScope
	Context provideContext(){
		return context;
	}

	@Provides
	@ActivityScope
	MainView provideView(){
		return view;
	}

	@Provides
	@ActivityScope
	MainPresenter providePresenter(){
		return new MainPresenter(view, context);
	}

}
