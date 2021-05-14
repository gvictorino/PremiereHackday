package com.paytv.premiere.hackday

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import com.paytv.premiere.core.entities.Match
import com.paytv.premiere.core.service.MatchesService
import com.paytv.premiere.core.service.MyTeamServiceImpl

/**
 * Implementation of App Widget functionality.
 */

lateinit var matchesService: MatchesService
lateinit var matches: List<Match>

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

        matchesService = MyTeamServiceImpl(context)
        matches = matchesService.getMatches()

        loadContent(
            context,
            appWidgetIds,
            matches[0],
            RemoteViews(context.packageName, R.layout.premiere_widget_2x2)
        )
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

        val remoteViews = getRemoteViews(context!!, minWidth)

        loadContent(context, IntArray(appWidgetId), matches[0], remoteViews)

        // Obtain appropriate widget and update it.
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

        super.onAppWidgetOptionsChanged(
            context, appWidgetManager, appWidgetId,
            newOptions
        )
    }
}

private fun loadContent(
    context: Context,
    appWidgetId: IntArray,
    match: Match,
    remoteViews: RemoteViews
) {
    val homeTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(context.applicationContext, R.id.homeTeamLogo, remoteViews, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val awayTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(context.applicationContext, R.id.awayTeamLogo, remoteViews, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    Glide.with(context).asBitmap().load(match.home!!.image).into(homeTeamLogo)
    Glide.with(context).asBitmap().load(match.away!!.image).into(awayTeamLogo)

    remoteViews.setTextViewText(R.id.championshipText, match.championship)
    remoteViews.setTextViewText(R.id.scoreText, "${match.homeScore} x ${match.awayScore}")
    remoteViews.setTextViewText(R.id.awayTeamText, match.away!!.abbreviation)
    remoteViews.setTextViewText(R.id.homeTeamText, match.home!!.abbreviation)
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
    minWidth: Int
): RemoteViews {
    val columns = getCellsForSize(minWidth)
    if (columns == 2) {
        return RemoteViews(
            context.packageName,
            R.layout.premiere_widget_2x2
        )
    }
    if (columns == 5) {
        return RemoteViews(
            context.packageName,
            R.layout.premiere_widget_2x5
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
