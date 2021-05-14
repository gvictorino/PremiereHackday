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

/**
 * Implementation of App Widget functionality.
 */
class PremiereBigWidget : AppWidgetProvider() {
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
            matches
        )
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateBigAppWidget(context, appWidgetManager, appWidgetId)
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
            matches
        )
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}

internal fun updateBigAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.premiere_big_widget)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun loadContent(
    context: Context,
    appWidgetId: IntArray,
    matches: List<Match>
) {
    val views = RemoteViews(context.packageName, R.layout.premiere_big_widget)

    val homeTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(context.applicationContext, R.id.homeTeamLogo, views, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val awayTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(context.applicationContext, R.id.awayTeamLogo, views, *appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameOneHomeTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game1HomeTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameOneAwayTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game1AwayTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameTwoHomeTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game2HomeTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameTwoAwayTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game2AwayTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameThreeHomeTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game3HomeTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    val gameThreeAwayTeamLogo: AppWidgetTarget = object :
        AppWidgetTarget(
            context.applicationContext,
            R.id.game3AwayTeamLogo,
            views,
            *appWidgetId
        ) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }

    Glide.with(context).asBitmap().load(matches[0].home!!.image).into(homeTeamLogo)
    Glide.with(context).asBitmap().load(matches[0].away!!.image).into(awayTeamLogo)

    Glide.with(context).asBitmap().load(matches[1].home!!.image).into(gameOneHomeTeamLogo)
    Glide.with(context).asBitmap().load(matches[1].away!!.image).into(gameOneAwayTeamLogo)

    Glide.with(context).asBitmap().load(matches[2].home!!.image).into(gameTwoHomeTeamLogo)
    Glide.with(context).asBitmap().load(matches[2].away!!.image).into(gameTwoAwayTeamLogo)

    Glide.with(context).asBitmap().load(matches[3].home!!.image).into(gameThreeHomeTeamLogo)
    Glide.with(context).asBitmap().load(matches[3].away!!.image).into(gameThreeAwayTeamLogo)

    views.setTextViewText(R.id.championshipText, matches[0].championship)
    views.setTextViewText(R.id.scoreText, "${matches[0].homeScore} x ${matches[0].awayScore}")
    views.setTextViewText(R.id.awayTeamText, matches[0].away!!.abbreviation)
    views.setTextViewText(R.id.homeTeamText, matches[0].home!!.abbreviation)

    views.setTextViewText(R.id.game1ChampionshipText, matches[1].championship)
    views.setTextViewText(
        R.id.game1ScoreText,
        "${matches[1].homeScore} x ${matches[1].awayScore}"
    )
    views.setTextViewText(R.id.game1AwayTeamText, matches[1].away!!.abbreviation)
    views.setTextViewText(R.id.game1HomeTeamText, matches[1].home!!.abbreviation)

    views.setTextViewText(R.id.game2ChampionshipText, matches[2].championship)
    views.setTextViewText(
        R.id.game2ScoreText,
        "${matches[2].homeScore} x ${matches[2].awayScore}"
    )
    views.setTextViewText(R.id.game2AwayTeamText, matches[2].away!!.abbreviation)
    views.setTextViewText(R.id.game2HomeTeamText, matches[2].home!!.abbreviation)
}
