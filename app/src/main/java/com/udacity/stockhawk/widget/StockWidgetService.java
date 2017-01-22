package com.udacity.stockhawk.widget;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by juan on 15/01/2017.
 */

public class StockWidgetService extends RemoteViewsService implements Loader.OnLoadCompleteListener<Cursor> {
    private Cursor data = null;
    StockWidgetFactoryAdapter mAdapter;
    CursorLoader mCursorLoader;
    private static final int STOCK_LOADER = 0;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mAdapter = new StockWidgetFactoryAdapter(this, intent);
        mCursorLoader = new CursorLoader(this,
                Contract.Quote.uri,
                Contract.Quote.QUOTE_COLUMNS,
                null, null, Contract.Quote.COLUMN_SYMBOL);
        mCursorLoader.registerListener(STOCK_LOADER, this);
        mCursorLoader.startLoading();
        return  mAdapter;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
        Log.i("cursor stop ",""+cursor.getCount());
        mAdapter.setCursor(cursor);
        mAdapter.onDataSetChanged();
    }
}
