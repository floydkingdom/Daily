package com.example.administrator.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.daily.Beans.CostBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends AppCompatActivity {

    public static final String COST_LIST = "cost_list";
    private LineChartView mChart;
    private Map<String,Integer> table = new TreeMap<>();
    private LineChartData mData;

    public static Intent newIntent (Context context, List<CostBean> beanList){
        Intent intent = new Intent(context,ChartsActivity.class);
        intent.putExtra(COST_LIST, (Serializable) beanList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChart = (LineChartView) findViewById(R.id.chart);
        List<CostBean> allDate = (List<CostBean>) getIntent().getSerializableExtra(COST_LIST);
        generateValues(allDate);
        generateData();
    }

    private void generateData() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int indexX = 0;
        for (Integer value: table.values()) {
            values.add(new PointValue(indexX,value));
            indexX++;
        }
        Line line = new Line(values)
                .setColor(ChartUtils.COLOR_BLUE)
                .setShape(ValueShape.DIAMOND)
                .setPointColor(ChartUtils.COLOR_GREEN);
        lines.add(line);
        mData = new LineChartData(lines);
        mChart.setLineChartData(mData);
    }

    //处理数据，相同日期的金额相加
    private void generateValues(List<CostBean> allDate) {
        if (allDate != null){
            for (int i = 0; i < allDate.size(); i++) {
                CostBean costBean = allDate.get(i);
                String costDate = costBean.costDate;
                int costMoney = Integer.parseInt(costBean.costMoney);
                if (!table.containsKey(costDate)) {
                    table.put(costDate,costMoney);
                } else {
                    int originMoney = table.get(costDate);
                    table.put(costDate,originMoney + costMoney);
                }
            }
        }
    }
}
