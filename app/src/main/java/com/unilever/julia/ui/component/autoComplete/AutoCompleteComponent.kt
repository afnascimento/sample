package com.unilever.julia.ui.component.autoComplete

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.ui.component.IAutoCompleteModel
import com.unilever.julia.ui.component.autoComplete.adapter.AutoCompleteAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.apache.commons.lang3.StringUtils
import java.util.concurrent.TimeUnit

abstract class AutoCompleteComponent <T : IAutoCompleteModel> : RelativeLayout {

    constructor(context: Context) : super(context, null) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    protected lateinit var mAdapter : AutoCompleteAdapter<T>

    private lateinit var btnClose : View

    private lateinit var autoCompleteTextView : AutoCompleteTextView

    private lateinit var btnSendMessage : View

    abstract fun filterItems(text: String)

    abstract fun getAdapter() : AutoCompleteAdapter<T>

    abstract fun addItems(items: MutableList<T>)

    abstract fun setOnItemClickListener(position: Int)

    abstract fun onCloseAutoComplete(model : ButtonClickListenerModel)

    abstract fun onTextSendInput(text: String)

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var textHint = context.getString(R.string.autocomplete_hint)
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.AutoCompleteComponent, 0, 0
            )
            try {
                val hintTemp : String? = typedArray.getString(R.styleable.AutoCompleteComponent_acp_hint)
                if (StringUtils.isNotEmpty(hintTemp)) {
                    textHint = StringUtils.normalizeSpace(hintTemp)
                }
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.autocomplete_edittext, this)

        mAdapter = getAdapter()

        btnClose = findViewById(R.id.btCloseAutocomplete)
        btnClose.setOnClickListener {
            onCloseAutoComplete(mCloseButton)
        }
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.threshold = 1
        autoCompleteTextView.dropDownWidth = resources.displayMetrics.widthPixels
        autoCompleteTextView.setAdapter(mAdapter)
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            setOnItemClickListener(position)
        }
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mProcessor.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnSendMessage = findViewById(R.id.btnSendMessage)
        btnSendMessage.setOnClickListener {
            val text = StringUtils.normalizeSpace(autoCompleteTextView.text.toString())
            if (StringUtils.isNotEmpty(text)) {
                onTextSendInput(text)
            }
        }

        setHint(textHint)
    }

    private var mProcessor : PublishProcessor<String> = PublishProcessor.create()

    interface HandlerDisposable {
        fun addDisposable(disposable: Disposable)
    }

    fun addHandlerDisposable(handlerDisposable: HandlerDisposable) {
        handlerDisposable.addDisposable(
            mProcessor
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .debounce(300, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .subscribeWith(object : DisposableSubscriber<String>() {
                    override fun onNext(text: String) {
                        filterItems(text)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(t: Throwable?) {
                    }
                })
        )
    }

    fun setText(text: String) {
        autoCompleteTextView.setText(text, TextView.BufferType.EDITABLE)
    }

    fun setHint(text: String) {
        autoCompleteTextView.hint = text
    }

    private var mCloseButton = ButtonClickListenerModel()

    fun setCloseButton(closeButton: ButtonClickListenerModel) {
        mCloseButton = closeButton
    }

    protected var mIntent = ""

    fun setIntent(intent: String) {
        mIntent = intent
    }
}