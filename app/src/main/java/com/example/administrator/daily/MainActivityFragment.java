package com.example.administrator.daily;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.daily.Beans.CostBean;
import com.example.administrator.daily.adapters.CostListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private List<CostBean> mCostList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = view.findViewById(R.id.lv_main);
        setHasOptionsMenu(true);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostList = new ArrayList<>();
        initCostData();
        mAdapter = new CostListAdapter(mCostList, getActivity());
        listView.setAdapter(mAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                view.setVisibility(View.INVISIBLE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = LayoutInflater.from(getActivity());
                                View viewDialog = inflater.inflate(R.layout.new_cost_data,null);
                                final EditText title = viewDialog.findViewById(R.id.et_cost_title);
                                final EditText money = viewDialog.findViewById(R.id.et_cost_money);
                                final DatePicker date = viewDialog.findViewById(R.id.dp_cost_date);
                                builder.setView(viewDialog);
                                builder.setTitle("New Cost");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        CostBean costBean = new CostBean();
                                        costBean.costTitle = title.getText().toString();
                                        costBean.costMoney = money.getText().toString();
                                        costBean.costDate = date.getYear() + "-" + (date.getMonth() + 1) + "-"
                                                + date.getDayOfMonth();
                                        mDatabaseHelper.insertCost(costBean);
                                        mCostList.add(costBean);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                                builder.setNegativeButton("Cancel",null);
                                builder.create().show();
                            }
                        }).  show();
            }
        });


        return view;
    }

    private void initCostData() {
//        mDatabaseHelper.deleteAllData();
//        for (int i = 0; i < 10; i++) {
//            CostBean costBean = new CostBean();
//            costBean.costTitle = "soup" + i;
//            costBean.costDate = "2017-7-5";
//            costBean.costMoney = "66";
//            mDatabaseHelper.insertCost(costBean);
//        }
        Cursor cursor = mDatabaseHelper.getAllCostData();
        if ((cursor != null)) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = ChartsActivity.newIntent(getActivity(),mCostList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
