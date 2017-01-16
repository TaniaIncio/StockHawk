package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by juan on 15/01/2017.
 */

public class StockWidgetService extends RemoteViewsService {
    private Cursor data = null;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
       return new StockWidgetFactoryAdapter(this, intent);
    }
}
