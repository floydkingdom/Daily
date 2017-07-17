package com.example.administrator.daily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.daily.Beans.CostBean;
import com.example.administrator.daily.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class CostListAdapter extends BaseAdapter {

    private List<CostBean> mCostBeanList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CostListAdapter(List<CostBean> costBeanList, Context context) {
        mCostBeanList = costBeanList;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCostBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCostBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.list_item,viewGroup,false);
            viewHolder.mTvCostTitle = view.findViewById(R.id.tv_title);
            viewHolder.mTvCostDate = view.findViewById(R.id.tv_date);
            viewHolder.mTvCostMoney = view.findViewById(R.id.tv_cost);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CostBean costBean = mCostBeanList.get(i);
        viewHolder.mTvCostTitle.setText(costBean.costTitle);
        viewHolder.mTvCostDate.setText(costBean.costDate);
        viewHolder.mTvCostMoney.setText(costBean.costMoney);
        return view;
    }

    private static class ViewHolder {
        public TextView mTvCostTitle;
        public TextView mTvCostDate;
        public TextView mTvCostMoney;
    }
}
