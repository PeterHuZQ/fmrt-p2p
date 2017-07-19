package com.fmrt.p2p.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseViewHolder;


/**
 * UserFragmentAdapter：个人中心的适配器（GridView的适配器）
 * 在UserFragment的initData()方法里被调用
 * 注：类似ChannelAdapter
 */
public class UserCenterFragmentAdapter extends BaseAdapter {
	private Context mContext;

	//图片文字数据存在本地
	public String[] img_text = { "交易记录", "橙子信用", "我的邀请", "我的富民币", "我的银行卡", "我的任务", "我的奖品", "富民小善", "帮助中心" };
	public int[] imgs = { R.drawable.user_menu_a, R.drawable.user_menu_h, R.drawable.user_menu_i,
			R.drawable.user_menu_b, R.drawable.user_menu_c, R.drawable.user_menu_d, R.drawable.user_menu_e,
			R.drawable.user_menu_f, R.drawable.user_menu_g, };

	public UserCenterFragmentAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user_grid_view, parent, false);
		}
		//BaseViewHolder用于优化ViewHolder
		ImageView iv_icon = BaseViewHolder.get(convertView, R.id.iv_grid);
		TextView tv_title = BaseViewHolder.get(convertView, R.id.tv_grid);

        //根据位置得到对应的数据
		iv_icon.setImageResource(imgs[position]);
		tv_title.setText(img_text[position]);
		return convertView;
	}

}
