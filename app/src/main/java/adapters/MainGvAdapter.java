package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import beans.MainListCallBackBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import live.yogee.com.yogeelive.R;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MainGvAdapter extends BaseAdapter{

	private Context context;
	private MainListCallBackBean bean;
	private LayoutInflater inflater;

	public MainGvAdapter(Context context, MainListCallBackBean bean){
		this.context = context;
		this.bean = bean;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount(){
		return bean.getData().getList().size();
	}

	@Override
	public Object getItem(int position){
		return bean.getData().getList().get(position);
	}

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder vh = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.adapter_main_gv_item, null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}

		vh.mainGvItemName
				.setText(bean.getData().getList().get(position).getName());
		Picasso.with(context)
			   .load(bean.getData().getList().get(position).getImg())
			   .into(vh.mainGvItemPic);

		return convertView;
	}


	static class ViewHolder{
		@BindView(R.id.main_gv_item_pic)
		ImageView mainGvItemPic;
		@BindView(R.id.main_gv_item_name)
		TextView mainGvItemName;

		ViewHolder(View view){
			ButterKnife.bind(this, view);
		}
	}
}
