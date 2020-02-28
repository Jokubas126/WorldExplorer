package com.example.worldexplorer.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.SupportMapFragment

class TouchableSupportMapFragment : SupportMapFragment() {

    lateinit var listener: OnMapTouchListener

    interface OnMapTouchListener {
        fun onMapTouch(event: MotionEvent)
    }

    fun setOnTouchListener(listener: OnMapTouchListener){
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout: View? = super.onCreateView(inflater, parent, savedInstanceState)

        val frameLayout = TouchableWrapper(activity!!.applicationContext)
        frameLayout.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, android.R.color.transparent))

        (layout as ViewGroup).addView(
            frameLayout,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        return layout
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {
        override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
            when(event!!.action){
                MotionEvent.ACTION_DOWN -> listener.onMapTouch(event)
            }
            return super.dispatchTouchEvent(event)
        }
    }
}