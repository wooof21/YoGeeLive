package popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import live.yogee.com.yogeelive.R;

/**
 * Created by Administrator on 2016/8/12.
 */
public class StreamingDetailPopUp extends PopupWindow{

	public StreamingDetailPopUp(Context context, View parent){
		View view = View.inflate(context, R.layout.popup_stream_detail, null);

		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		setFocusable(true);
		setBackgroundDrawable(new BitmapDrawable());
		setOutsideTouchable(true);
		setContentView(view);

		showAtLocation(parent, Gravity.CENTER, 0, 0);
		update();
	}
}
