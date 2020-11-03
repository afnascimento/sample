package com.unilever.julia.ui.base

import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.unilever.julia.logger.Logger
import com.unilever.julia.ui.component.JuliaButtonsDisambiguation

class RecyclerViewOnItemTouchListener : RecyclerView.OnItemTouchListener {

    private var mEnableScrollable = true

    fun enableScroll() {
        mEnableScrollable = true
    }

    fun disableScroll() {
        mEnableScrollable = false
    }

    private val DEBUG_TAG = "SCROLL"

    /*
    fun getAction(motionEvent: MotionEvent) {

        val action = motionEvent.actionMasked

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Logger.debug(DEBUG_TAG, "Action was DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Logger.debug(DEBUG_TAG, "Action was MOVE")
            }
            MotionEvent.ACTION_UP -> {
                Logger.debug(DEBUG_TAG, "Action was UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                Logger.debug(DEBUG_TAG, "Action was CANCEL")
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Logger.debug(DEBUG_TAG, "Movement occurred outside bounds of current screen element")
            }
            else -> {
                Logger.debug(DEBUG_TAG, "other moviment")
            }
        }
    }
    */

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        var scrollable = false

        if (!mEnableScrollable) {
            scrollable = true

            val view : View? = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null && view is JuliaButtonsDisambiguation) {
                scrollable = false
            }
        }

        //Logger.debug(DEBUG_TAG, "onInterceptTouchEvent() -> ${!scrollable}")

        return scrollable
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}