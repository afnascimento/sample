package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.unilever.julia.utils.Utils

class IpvTabLayout : RelativeLayout, ViewPager.OnPageChangeListener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var scrollView : HorizontalScrollView
    private var contentItems : LinearLayout

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_tab_layout, this)
        scrollView = findViewById(R.id.scrollView)
        contentItems = findViewById(R.id.contentItems)
    }

    data class Item(val color: String, val text: String)

    private var mPosition = 0
    private var mSelected : IpvTabButton? = null

    private fun getMargins(left: Float, top: Float, right: Float, bottom: Float): LayoutParams {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.setMargins(getMargin(left), getMargin(top), getMargin(right), getMargin(bottom))
        return params
    }

    private fun getMargin(value: Float): Int {
        return Utils.getDimension(context.resources, value, TypedValue.COMPLEX_UNIT_DIP).toInt()
    }

    interface Listener {
        fun onChangeTab(position: Int)
    }

    private var mListener : Listener? = null

    fun setListener(listener: Listener) {
        this.mListener = listener
    }

    private fun onChangeTab(position: Int, tabButton: IpvTabButton) {
        if (position != mPosition) {
            mSelected?.setActive(false)

            mPosition = position
            mSelected = tabButton
            mSelected?.setActive(true)

            mViewPager?.setCurrentItem(position, true)

            //Log.d("SCROLL", "x -> ${tabButton.x} y -> ${tabButton.y}")
            scrollView.smoothScrollTo(tabButton.x.toInt() - 24, 0)

            mListener?.onChangeTab(position)
        }
    }

    private var mViewPager: ViewPager? = null

    fun setupWithViewPager(viewPager: ViewPager) {
        this.mViewPager = viewPager
        this.mViewPager?.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int) { }

    override fun onPageSelected(position: Int) {
        onChangeTab(position, mTabButtons[position])
    }

    private var mTabButtons = arrayListOf<IpvTabButton>()

    fun addTabs(items: List<Item>) {
        for ((index, it) in items.withIndex()) {
            val tabButton = IpvTabButton(context)
            tabButton.setText(it.text)
            tabButton.setColorRound(it.color)
            tabButton.setListener(index, object : IpvTabButton.Listener {
                override fun onIpvButtonClick(index: Int, tabButton: IpvTabButton) {
                    onChangeTab(index, tabButton)
                }
            })
            when (index) {
                0 -> {
                    mPosition = index
                    mSelected = tabButton
                    tabButton.setActive(true)
                    tabButton.layoutParams = getMargins(24f, 16f, 0f, 0f)
                }
                (items.size - 1) -> {
                    tabButton.setActive(false)
                    tabButton.layoutParams = getMargins(24f, 16f, 24f, 0f)
                }
                else -> {
                    tabButton.setActive(false)
                    tabButton.layoutParams = getMargins(24f, 16f, 0f, 0f)
                }
            }
            mTabButtons.add(tabButton)
            contentItems.addView(tabButton)
        }
    }
}