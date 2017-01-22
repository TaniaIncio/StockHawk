package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by juan on 15/01/2017.
 */

public class StockWidgetService extends RemoteViewsService implements MainActivity.LoadCursor{
    private Cursor data = null;
    StockWidgetFactoryAdapter mAdapter;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mAdapter = new StockWidgetFactoryAdapter(this, intent);
        MainActivity mainActivity=new MainActivity();
        mainActivity.getApplicationContext();
        mainActivity.getData();
        return  mAdapter;
    }

    @Override
    public void getCursor(Cursor cursor) {
        data= cursor;
        mAdapter.onDataSetChanged();
    }
}
