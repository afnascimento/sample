package com.unilever.julia.ui.boletim.multiple

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.boletim_multiple_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class BoletimMultipleActivity : BaseViewActivity(), BoletimMultipleView {

    @Inject
    lateinit var mPresenter: BoletimMultiplePresenter<BoletimMultipleView, BoletimMultipleInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boletim_multiple_activity)

        setSupportActionBar(toolbarBack)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        button.setOnClickListener {
            onBackPressed()
        }

        val title = intent.getStringExtra(RedirectIntents.EXTRA_TITLE)
        val hint = intent.getStringExtra(RedirectIntents.EXTRA_HINT)
        val typeOrdinal = intent.getIntExtra(RedirectIntents.EXTRA_TYPE, 0)
        val type = BulletinsMultipleFiltersResponse.Type.values()[typeOrdinal]
        val serviceArea = intent.getParcelableExtra<AttendanceFilter>(RedirectIntents.EXTRA_ATTENDANCE_FILTER)

        tvToolbarTitle.text = title
        boletimRecyclerFilter.setHint(hint)

        mPresenter.init(type, serviceArea)
    }

    override fun onBackPressed() {
        val items : ArrayList<String> = arrayListOf()
        for (it in boletimRecyclerFilter.getItemsSelected()) {
            items.add(it)
        }

        if (items.isEmpty()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val intent = Intent()
            intent.putExtra(RedirectIntents.EXTRA_ARRAY_STRING, items)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContentViews(): List<View?>? {
        return listOf(boletimRecyclerFilter, button)
    }

    override fun addItems(items : List<String>) {
        boletimRecyclerFilter.addItems(items)
    }
}