package application.component;

import android.app.Application;
import android.content.SharedPreferences;
import application.module.AppModule;
import constant.RestService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/8/2.
 */
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent{
	Application application();
	SharedPreferences sharedPreferences();
	SharedPreferences.Editor editor();
	RestService restService();
}
