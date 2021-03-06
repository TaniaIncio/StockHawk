package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.StockHawkAppWidgetProvider;

import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by juan on 16/01/2017.
 */

public class StockWidgetFactoryAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Cursor data;
    Context context;
    //other values
    private DecimalFormat dollarFormatWithPlus;
    private DecimalFormat dollarFormat;
    private DecimalFormat percentageFormat;

    public StockWidgetFactoryAdapter(Context context, Intent intent){
        this.context = context;
    }
    @Override
    public void onCreate() {
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

    }

    @Override
    public void onDataSetChanged() {
        Collections.shuffle(Arrays.asList(data), new Random());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        int count = 0;
        if (data != null) {
            count = data.getCount();
        }
        return count;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            return null;
        }
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.list_item_widget);

        /******/

        data.moveToPosition(position);
        float rawAbsoluteChange = data.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        float percentageChange = data.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);

        String percentage = percentageFormat.format(percentageChange / 100);

        views.setTextViewText(R.id.symbol, data.getString(Contract.Quote.POSITION_SYMBOL));
        views.setTextViewText(R.id.price,dollarFormat.format(data.getFloat(Contract.Quote.POSITION_PRICE)));
        views.setTextViewText(R.id.change,percentage);
        /*****/
        final Intent i = new Intent();
        final Bundle extra = new Bundle();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(extra);
        i.setAction(StockHawkAppWidgetProvider.INTENT_ACTION);
        views.setOnClickFillInIntent(R.id.linear_widget, i);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setCursor(Cursor cursor){
        this.data = cursor;
    }
}
