package com.fmrt.p2p.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.product.bean.InvestBeanData;
import com.fmrt.p2p.widget.RoundProgress;

import java.util.List;

/**
 * 投资频道列表适配器
 */

public class InvestListAdapter extends BaseAdapter
{
    List<InvestBeanData.InvestBean> invest_list;  //数据集
    LayoutInflater inflater;

    private Context mContext;
    //构造函数
    public InvestListAdapter(Context context, List<InvestBeanData.InvestBean> invest_list) {
        this.invest_list = invest_list;
        this.inflater = LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public int getCount()
    {
        return invest_list == null ? 0 : invest_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return invest_list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        InvestBeanData.InvestBean entity=invest_list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_invest_list,null);
            viewHolder.pName = (TextView) convertView.findViewById(R.id.p_name);
            viewHolder.pMoney = (TextView) convertView.findViewById(R.id.p_money);
            viewHolder.pYearlv = (TextView) convertView.findViewById(R.id.p_yearlv);
            viewHolder.pSuodingdays = (TextView) convertView.findViewById(R.id.p_suodingdays);
            viewHolder.pMinzouzi = (TextView) convertView.findViewById(R.id.p_minzouzi);
            viewHolder.pProgresss = (RoundProgress) convertView.findViewById(R.id.p_progresss);

            //对ViewHolder的优化
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据
        viewHolder.pName.setText(entity.getName());
        viewHolder.pMoney.setText(entity.getMoney());
        viewHolder.pYearlv.setText(entity.getYearLv());
        viewHolder.pSuodingdays.setText(entity.getSuodingDays());
        viewHolder.pMinzouzi.setText(entity.getMinTouMoney());
        viewHolder.pProgresss.setProgress(Integer.parseInt(entity.getProgress()));
        return convertView;
    }


    //ViewHolder模式，用来提升效率
    static class ViewHolder {
        TextView pName;
        TextView pMoney;
        TextView pYearlv;
        TextView pSuodingdays;
        TextView pMinzouzi;
        RoundProgress pProgresss;
    }
}
