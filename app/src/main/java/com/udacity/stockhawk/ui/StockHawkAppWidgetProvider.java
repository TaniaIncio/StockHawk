package com.udacity.stockhawk.ui;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.widget.StockWidgetService;

public class StockHawkAppWidgetProvider extends AppWidgetProvider {

    public static final String INTENT_ACTION = "com.udacity.stockhawk.ui.INTENT_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(INTENT_ACTION)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, StockWidgetService.class);
            intent.setAction(INTENT_ACTION);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
            views.setRemoteAdapter(R.id.widget_list, intent);
            views.setEmptyView(R.id.widget_list, R.id.widget_empty);

            Intent mIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntentRed = PendingIntent.getActivity(context, 0, mIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntentRed);

            views.setPendingIntentTemplate(R.id.widget_list, pendingIntentRed);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
