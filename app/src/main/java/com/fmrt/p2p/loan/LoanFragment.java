package com.fmrt.p2p.loan;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.loan.adapter.LoanProductListAdapter;
import com.fmrt.p2p.loan.bean.LoanData;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.LoadListView;

import java.util.ArrayList;
import butterknife.BindView;

/**
 * 贷款产品列表Fragment
 */

public class LoanFragment extends BaseFragment implements LoadListView.ILoadListener
{

    //ListView绑定适配器，适配器绑定数据源
    @BindView(R.id.lv_loanproduct)
    LoadListView lv_loanproduct;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;

    LoanProductListAdapter adapter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_loan;
    }

    @Override
    protected void initView()
    {
        initTitle();
    }

    private void initTitle()
    {
        mIvLeft.setVisibility(View.INVISIBLE);
        mTvTitle.setText("借款");
        mIvRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData()
    {
        LoanData data1 = new LoanData();
        data1.setIconResource(R.drawable.borrow_fmd);
        data1.setLoanName("富民贷");
        data1.setLoanDescribe(AppConstant.FMD_TEXT);
        data1.setMaxProperty("最高可贷10万");
        data1.setProperty1("贷款期限最高 12个月");
        data1.setProperty2("按月付息，到期还本");
        data1.setProperty3("循环使用");
        ArrayList<String> condition1 = new ArrayList<>();

        condition1.add("年龄18-63周岁");
        condition1.add("信用良好的县域个体工商户、工薪阶层及农户。");
        data1.setConditions(condition1);

        LoanData data2 = new LoanData();
        data2.setIconResource(R.drawable.borrow_b);
        data2.setLoanName("农信人贷");
        data2.setLoanDescribe("纯信用担保方式");
        data2.setMaxProperty("最高可贷20万");
        data2.setProperty1("贷款期限最高 12个月");
        data2.setProperty2("月利率 6.5‰");
        data2.setProperty3("按月付息，到期还本");
        ArrayList<String> condition2 = new ArrayList<>();
        condition2.add("年龄为23周岁-60周岁。");
        condition2.add("浙江省内各农信机构已缴存公积金的员工。");

        data2.setConditions(condition2);

        LoanData data3 = new LoanData();
        data3.setIconResource(R.drawable.borrow_c);
        data3.setLoanName("e个钱包");
        data3.setLoanDescribe("在校学生生活不愁");
        data3.setMaxProperty("最高可贷6千");
        data3.setProperty1("贷款期限最高24个月");
        data3.setProperty2("日利率 0.4‰");
        data3.setProperty3("等额本息获或等本等息");
        ArrayList<String> condition3 = new ArrayList<>();
        condition3.add("年龄18周岁以上。");
        condition3.add("浙江省温州市各高校在校学生。");
        data3.setConditions(condition3);


        ArrayList<LoanData> loanproduct_list = new ArrayList<>();
        loanproduct_list.add(data1);
        loanproduct_list.add(data2);
        loanproduct_list.add(data3);

        //给ListView设置ILoadListener
        lv_loanproduct.setInterface(this);
        //给ListView设置适配器adapter
        adapter = new LoanProductListAdapter(getActivity(),loanproduct_list);
        lv_loanproduct.setAdapter(adapter);
        //设置点击事件
        lv_loanproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.getInstance().showToast("该产品正在升级中，暂时无法申请，敬请期待", Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        ToastUtil.getInstance().showToast("该产品正在升级中，暂时无法申请，敬请期待", Toast.LENGTH_SHORT);
                        break;
                    case 2:
                        ToastUtil.getInstance().showToast("该产品正在升级中，暂时无法申请，敬请期待", Toast.LENGTH_SHORT);
                        break;
                    default:
                        break;
                }


            }
        });
    }



    @Override
    public void onLoad()
    {

    }
}
