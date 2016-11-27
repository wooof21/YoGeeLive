/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package view.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
public class ImagePagerAdapter extends BaseAdapter{

	private Context context;
	private List<String> imageIdList;
	private List<String> linkUrlArray;
	private int size;
	private boolean isInfiniteLoop;

	public ImagePagerAdapter(Context context, List<String> imageIdList,
							 List<String> urllist
	){
		this.context = context;
		this.imageIdList = imageIdList;
		if(imageIdList != null){
			this.size = imageIdList.size();
		}
		this.linkUrlArray = urllist;
		isInfiniteLoop = false;
	}

	@Override
	public int getCount(){
		return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
	}
	private int getPosition(int position){
		return isInfiniteLoop ? position % size : position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container){
		final ViewHolder holder;
		if(view == null){
			holder = new ViewHolder();
			view = holder.imageView = new ImageView(context);
			holder.imageView
					.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}

		Picasso.with(context)
			   .load((String) this.imageIdList.get(getPosition(position)))
			   .into(holder.imageView);

		return view;
	}

	private static class ViewHolder{

		ImageView imageView;
	}

	public boolean isInfiniteLoop(){
		return isInfiniteLoop;
	}

	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop){
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	@Override
	public Object getItem(int arg0){
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0){
		// TODO Auto-generated method stub
		return arg0;
	}

}
