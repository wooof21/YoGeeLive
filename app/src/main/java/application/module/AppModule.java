package application.module;

import android.app.Application;
import android.content.SharedPreferences;
import constant.Config;
import constant.RestService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/2.
 */
@Module
public class AppModule{

	Application app;

	public AppModule(Application app){
		this.app = app;
	}

	@Provides
	@Singleton
	Application provideApplication(){
		return app;
	}

	@Provides
	@Singleton
	SharedPreferences provideSharedPreference(){
		return app.getSharedPreferences("liveGo", app.MODE_PRIVATE);
	}

	@Provides
	@Singleton
	SharedPreferences.Editor provideEditor(SharedPreferences spf){
		return spf.edit();
	}

	@Provides
	@Singleton
	RestService provideRestService(Retrofit retrofit){
		return retrofit.create(RestService.class);
	}

	@Provides
	@Singleton
	Retrofit provideRetrofit(OkHttpClient client){
		return new Retrofit.Builder().baseUrl(Config.BASE_URL)
									 .addConverterFactory(
											 GsonConverterFactory.create())
									 .addCallAdapterFactory(
											 RxJavaCallAdapterFactory.create())
									 .client(client).build();
	}


	@Provides
	@Singleton
	OkHttpClient provideClient(HttpLoggingInterceptor interceptor){
		return new OkHttpClient.Builder().retryOnConnectionFailure(true)
										 .connectTimeout(30, TimeUnit.SECONDS)
										 .readTimeout(60 * 1000,
													  TimeUnit.MILLISECONDS)
										 .addInterceptor(interceptor)
										 .build();
	}

	@Provides
	@Singleton
	HttpLoggingInterceptor provideInterceptor(){
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		// config log
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return interceptor;
	}


}
