package com.paytv.premiere.hackday

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import com.paytv.premiere.core.entities.Match
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MatchesServiceImpl

/**
 * Implementation of App Widget functionality.
 */

lateinit var matchesService: MatchesService

class PremiereLargeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppLargeWidget(context, appWidgetManager, appWidgetId)
        }

        matchesService = MatchesServiceImpl(context)
        val matches = matchesService.getMatches()

        loadContent(context, appWidgetIds, matches[0])
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
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
    }
}

private fun loadContent(context: Context, appWidgetId: IntArray, match: Match) {
    var options = RequestOptions().override(20, 20)
    val views = RemoteViews(context.packageName, R.layout.premiere_widget_2x2)

    val homeTeamLogo: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.homeTeamLogo, views, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val awayTeamLogo: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.awayTeamLogo, views, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    Glide.with(context).asBitmap().load(match.home!!.image).apply(options).into(homeTeamLogo)
    Glide.with(context).asBitmap().load(match.away!!.image).apply(options).into(awayTeamLogo)

    views.setTextViewText(R.id.championshipText, match.championship)
    views.setTextViewText(R.id.scoreText, "${match.homeScore} X ${match.awayScore}")
}

internal fun updateAppLargeWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
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
