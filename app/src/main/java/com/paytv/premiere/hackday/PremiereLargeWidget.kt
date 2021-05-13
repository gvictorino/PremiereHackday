package com.paytv.premiere.hackday

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class PremiereLargeWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppLargeWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        // See the dimensions and
        val options = appWidgetManager!!.getAppWidgetOptions(appWidgetId)

        // Get min width and height.
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        val minHeight = options
            .getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)

        // Obtain appropriate widget and update it.
        appWidgetManager.updateAppWidget(
            appWidgetId,
            getRemoteViews(context!!, minWidth, minHeight)
        )

        super.onAppWidgetOptionsChanged(
            context, appWidgetManager, appWidgetId,
            newOptions
        )

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}

internal fun updateAppLargeWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.premiere_widget_2x2)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getRemoteViews(
    context: Context,
    minWidth: Int,
    minHeight: Int
): RemoteViews {
    // First find out rows and columns based on width provided.
    val rows = getCellsForSize(minHeight)
    val columns = getCellsForSize(minWidth)
    if (columns == 2) {
        return RemoteViews(
            context.packageName,
            R.layout.premiere_widget_2x2
        )
    }
    // Get appropriate remote view.
    return RemoteViews(
        context.packageName,
        R.layout.premiere_widget_2x3
    )
}

private fun getCellsForSize(size: Int): Int {
    var n = 2
    while (70 * n - 30 < size) {
        ++n
    }
    return n - 1
}
