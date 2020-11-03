package com.unilever.julia.ui.ipv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.unilever.julia.R
import com.unilever.julia.components.IpvContentProgress
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.data.model.ipv.IpvFaseamento
import com.unilever.julia.components.LoadView
import com.unilever.julia.ui.ipv.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FaseamentoFragment : Fragment() {

    private lateinit var mView: IpvView

    private lateinit var mPresenter: IpvPresenter<IpvView, IpvInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = (context as IpvActivity)
        mPresenter = (context as IpvActivity).mPresenter
    }

    private lateinit var loadView : LoadView
    private lateinit var contentMain : View

    private lateinit var ipvContentProgress : IpvContentProgress

    private lateinit var contentActive : View
    private lateinit var tvActiveTarget : TextView
    private lateinit var tvActiveDate : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ipv_faseamento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ipvContentProgress = view.findViewById(R.id.ipvContentProgress)
        contentMain = view.findViewById(R.id.contentMain)
        loadView = view.findViewById(R.id.loadView)

        // active
        contentActive = view.findViewById(R.id.contentActive)
        tvActiveTarget = contentActive.findViewById(R.id.tvTextLeft)
        tvActiveDate = contentActive.findViewById(R.id.tvTextRight)

        // expired
        contentMessage = view.findViewById(R.id.contentMessage)
        iconStatus = contentMessage.findViewById(R.id.iconStatus)
        lottieAnimation = contentMessage.findViewById(R.id.lottieAnimation)
        tvStatusTitle = contentMessage.findViewById(R.id.tvStatusTitle)
        tvStatusMessage = contentMessage.findViewById(R.id.tvStatusMessage)

        init()
    }

    private var mIpvFaseamento : IpvFaseamento? = null

    private fun init() {
        mView.addDisposable(
            mPresenter.getIpvFaseamento("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    mView.showLoadView(contentMain, loadView)
                }
                .subscribeWith(object : DisposableObserver<IpvFaseamento>() {
                    override fun onNext(value: IpvFaseamento) {
                        mIpvFaseamento = value
                        update(value)
                    }

                    override fun onComplete() {
                        mView.hideLoadView(contentMain, loadView)
                    }

                    override fun onError(e: Throwable) {
                        mView.onErrorHandler(contentMain, loadView, e, LoadView.OnClickListener { init() })
                    }
                })
        )
    }

    private fun update(value: IpvFaseamento) {
        when {
            value.getType() == IpvFaseamento.Type.active -> {
                setIpvActive(value)
            }
            value.getType() == IpvFaseamento.Type.expired -> {
                setIpvExpired(value)
            }
            value.getType() == IpvFaseamento.Type.inactive -> {
                setIpvInactive(value)
            }
        }
    }

    private fun setIpvActive(value: IpvFaseamento) {
        ipvContentProgress.setProgress(
            value.label ?: "",
            value.score ?: 0.0,
            value.colorCode ?: "",
            true)

        tvActiveTarget.text = getString(R.string.num_toneladas, value.getTargetText())
        tvActiveDate.text = value.getLimitDayText()
        contentActive.visibility = View.VISIBLE
    }

    private lateinit var contentMessage : View
    private lateinit var iconStatus : JuliaTextViewIcon
    private lateinit var lottieAnimation : LottieAnimationView
    private lateinit var tvStatusTitle : TextView
    private lateinit var tvStatusMessage : TextView

    private fun setIpvExpired(value: IpvFaseamento) {
        ipvContentProgress.setProgress(
            value.label ?: "",
            value.score ?: 0.0,
            value.colorCode ?: "",
            false)

        setIcon(value.message?.icon ?: "")
        tvStatusTitle.text = value.message?.title ?: ""
        tvStatusMessage.text = value.message?.description ?: ""
        contentMessage.visibility = View.VISIBLE
    }

    private fun setIpvInactive(value: IpvFaseamento) {
        ipvContentProgress.visibility = View.GONE

        setIcon(value.message?.icon ?: "")
        tvStatusTitle.text = value.message?.title ?: ""
        tvStatusMessage.text = value.message?.description ?: ""
        contentMessage.visibility = View.VISIBLE
    }

    private fun setIcon(icon: String) {
        when (icon) {
            IconEnums.ICON_CLAP.iconHexa -> {
                lottieAnimation.setAnimation(R.raw.clap)
                lottieAnimation.visibility = View.VISIBLE
                iconStatus.visibility = View.GONE
            }
            IconEnums.ICON_ATENTION.iconHexa -> {
                lottieAnimation.setAnimation(R.raw.attention)
                lottieAnimation.visibility = View.VISIBLE
                iconStatus.visibility = View.GONE
            }
            else -> {
                lottieAnimation.visibility = View.GONE
                iconStatus.visibility = View.VISIBLE
                iconStatus.setIcon(icon)
            }
        }
    }
}
