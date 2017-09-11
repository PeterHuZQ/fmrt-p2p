package com.fmrt.p2p.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.product.bean.CusContract;
import com.fmrt.p2p.util.UIUtils;
import com.fmrt.p2p.util.TimeUtil;
import com.fmrt.p2p.widget.TCustomProcessBar;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 推荐频道列表适配器
 */

public class RecommendListAdapter extends BaseAdapter
{
    List<CusContract> recommend_list;  //数据集
    LayoutInflater inflater;

    private Context mContext;
    //构造函数
    public RecommendListAdapter(Context context, List<CusContract> recommend_list) {
        this.recommend_list = recommend_list;
        this.inflater = LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public int getCount()
    {
        return recommend_list == null ? 0 : recommend_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return recommend_list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CusContract entity=recommend_list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_recommend_list,null);
            viewHolder.tvProjectId = (TextView) convertView.findViewById(R.id.tv_ProjectId);
            viewHolder.tvYearRate = (TextView) convertView.findViewById(R.id.tv_YearRate);
            viewHolder.tvEndDate = (TextView) convertView.findViewById(R.id.tv_EndDate);
            viewHolder.tvRemainSum = (TextView) convertView.findViewById(R.id.tv_RemainSum);
            viewHolder.tCustomProcessBar = (TCustomProcessBar) convertView.findViewById(R.id.t_CustomProcessBar);

            //对ViewHolder的优化
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据
        viewHolder.tvProjectId.setText(entity.getDisplayid());
        viewHolder.tvYearRate.setText(entity.getInvestorrate()+ "%");
        viewHolder.tvEndDate.setText(TimeUtil.longTime2Str(entity.getContrenddt()));
        viewHolder.tvRemainSum.setText(entity.getResidualamt()+ "元");
        //设置募集进度条
        int remainAmt = entity.getResidualamt();
        int loanAmt = entity.getBorramt();
        int progress = 100 * (loanAmt - remainAmt) / loanAmt;
        float x = (float) progress / 100;
        DecimalFormat df = new DecimalFormat("0.00");
        viewHolder.tCustomProcessBar.setProcess(Float.parseFloat(df.format(x)));
        viewHolder.tCustomProcessBar.setTextSize(UIUtils.dp2px(13));
        return convertView;
    }


    //ViewHolder模式，用来提升效率
    static class ViewHolder {
        TextView tvProjectId;  //项目编号
        TextView tvYearRate;   //投资人利率
        TextView tvEndDate;    //到期时间
        TextView tvRemainSum;  //剩余额度
        TCustomProcessBar tCustomProcessBar;
    }
}
