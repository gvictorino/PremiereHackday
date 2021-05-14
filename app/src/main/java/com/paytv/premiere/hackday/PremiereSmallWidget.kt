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
import com.paytv.premiere.core.service.MyTeamServiceImpl

class PremiereSmallWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        matchesService = MyTeamServiceImpl(context)
        matches = matchesService.getMatches()

        loadContent(
            context,
            appWidgetIds,
            matches[0],
            RemoteViews(context.packageName, R.layout.premiere_widget_1x2)
        )
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
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
        loadContent(
            context!!,
            IntArray(appWidgetId),
            matches[0],
            RemoteViews(context.packageName, R.layout.premiere_widget_1x2)
        )
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.premiere_widget_1x2)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
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

    remoteViews.setTextViewText(R.id.scoreText, "${match.homeScore} x ${match.awayScore}")
    remoteViews.setTextViewText(R.id.awayTeamText, match.away!!.abbreviation)
    remoteViews.setTextViewText(R.id.homeTeamText, match.home!!.abbreviation)
}
