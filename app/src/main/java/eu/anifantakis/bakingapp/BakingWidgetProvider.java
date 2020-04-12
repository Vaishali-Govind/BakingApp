package eu.anifantakis.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.BuildConfig;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import eu.anifantakis.bakingapp.activities.MainActivity;
import eu.anifantakis.bakingapp.utils.AppUtils;


public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

      
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        SharedPreferences SharePreference = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        view.setTextViewText(R.id.tv_widget_title, SharePreference.getString(AppUtils.PREFERENCES_WIDGET_TITLE, ""));
        view.setTextViewText(R.id.tv_widget_ingredients, SharePreference.getString(AppUtils.PREFERENCES_WIDGET_CONTENT, ""));

    
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        view.setOnClickPendingIntent(R.id.tv_widget_ingredients, pendingIntent);
        view.setOnClickPendingIntent(R.id.tv_widget_title, pendingIntent);

      
        appWidgetManager.updateAppWidget(appWidgetId, view);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

 
}

