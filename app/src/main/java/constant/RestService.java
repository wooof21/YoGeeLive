package constant;

import beans.*;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/8.
 */
public interface RestService{

	@Headers({"Content-Type: application/json","Accept: application/json"})
	@POST("userCheck")
	Observable<UserCheckCallBackBean> checkUser(@Body UserCheckBean bean);

	@POST("userRegistration")
	Observable<UserRegisterCallBackBean> register(@Body UserRegisterLoginBean bean);

	@POST("userLogin")
	Observable<UserLoginCallBackBean> login(@Body UserRegisterLoginBean bean);

	@POST("indexList")
	Observable<MainListCallBackBean> mainList(@Body MainListBean bean);

	@POST("createStream")
	Observable<StreamCallBackBean> requestStream(@Body StreamBean bean);
}
