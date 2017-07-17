package com.example.administrator.daily;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.daily.Beans.CostBean;

/**
 * Created by Administrator on 2017/7/7.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";
    public static final String WU_COST = "wu_cost";

    public DatabaseHelper(Context context) {
        super(context, "wu_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists wu_cost(" +
        "id integer primary key, " +
        "cost_title varchar, " +
        "cost_date varchar, " +
        "cost_money varchar)");
    }

    public void insertCost(CostBean costBean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COST_TITLE, costBean.costTitle);
        contentValues.put(COST_DATE, costBean.costDate);
        contentValues.put(COST_MONEY, costBean.costMoney);
        database.insert(WU_COST,null,contentValues);
    }

    //查询所有数据，按"cost_date"排序
    public Cursor getAllCostData(){
        return getWritableDatabase().query("wu_cost",null,null,null,null,null,COST_DATE + " ASC");
    }

    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(WU_COST,null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
