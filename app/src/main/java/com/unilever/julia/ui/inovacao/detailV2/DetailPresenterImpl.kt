package com.unilever.julia.ui.inovacao.detailV2

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Pair
import androidx.core.content.ContextCompat
import com.unilever.julia.R
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.data.model.inovacaoDetalhe.*
import com.unilever.julia.glide.GlideApp
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import com.unilever.julia.ui.inovacao.detailV2.model.PairFragment
import com.unilever.julia.utils.MaterialDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class DetailPresenterImpl<V : DetailView, I : DetailInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), DetailPresenter<V, I> {

    private var mData : InovacaoDetalhe? = null

    private var mDrawable : Drawable? = null

    override fun getResumo(): Resumo {
        return mData?.resumo ?: Resumo()
    }

    override fun getExecucao(): Execucao {
        return mData?.execucao ?: Execucao()
    }

    override fun getVisibilidade(): Visibilidade {
        return mData?.visibilidade ?: Visibilidade()
    }

    override fun getNovosSkus(): NovosSkus {
        return mData?.novosSkus ?: NovosSkus()
    }

    override fun getSkusDeslistados(): SkusDeslistados {
        return mData?.skusDeslistados ?: SkusDeslistados()
    }

    override fun getDrawableBrand(): Drawable {
        return mDrawable!!
    }

    override fun putDownloadTradestory(model: InnovationProjectsModel.Item, urlDownload : String) {
        getView().addDisposable(
            getInteractor().putDownloadTradestory(model.sessionCode, model.nextIntent, model.identifier ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<InovacaoDetalhe>() {
                    override fun onNext(value: InovacaoDetalhe) {
                        mData = value
                        getView().resumeFragmentActual()
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                        getView().startDownload(urlDownload)
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                        getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog ->
                            dialog.dismiss()
                        })
                    }
                })
        )
    }

    override fun updateTradestorySuccess(tradestory: Resumo.TradeStory) {
        getView().addDisposable(
            Observable.fromCallable {
                mData?.resumo?.tradeStory = tradestory
                return@fromCallable true
            }.delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        getView().resumeFragmentActual()
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                        getView().showToast(R.string.tradestory_evaluation_success)
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                    }
                })
        )
    }

    override fun getDetail(context: Context, model: InnovationProjectsModel.Item) {
        val imageDefaultProduct = model.image ?: ""

        getView().setTitleToolbar(model.title ?: "")
        getView().setImageCardLogo(imageDefaultProduct)

        val future1 = Observable.fromFuture(
            GlideApp
                .with(context)
                .asDrawable()
                .load(imageDefaultProduct)
                .submit()
        ).onErrorResumeNext(Function<Throwable, Observable<Drawable>> {
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_image_error)
            return@Function Observable.just(drawable)
        })

        val future2 = getInteractor().getDetail(model.sessionCode, model.nextIntent, model.identifier ?: "")

        getView().addDisposable(
            Observable.zip(future1, future2, object : BiFunction<Drawable, InovacaoDetalhe, Pair<Drawable, InovacaoDetalhe>> {
                override fun apply(t1: Drawable, t2: InovacaoDetalhe): Pair<Drawable, InovacaoDetalhe> {
                    return Pair(t1, t2)
                }
            }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Pair<Drawable, InovacaoDetalhe>>() {
                    override fun onNext(value: Pair<Drawable, InovacaoDetalhe>) {
                        mDrawable = value.first
                        mData = value.second

                        getView().setImageBanner(mData?.geral?.image ?: "", R.drawable.videos_thumbnail)
                        getView().setTitle(mData?.geral?.title ?: "")

                        getView().initTabs(
                            PairFragment.Builder()
                                .addItem(mData?.resumo?.name, PairFragment.Type.Resumo)
                                .addItem(mData?.execucao?.name, mData?.execucao?.isValid() ?: true, PairFragment.Type.Execucao)
                                .addItem(mData?.novosSkus?.name, mData?.novosSkus?.isValid() ?: true, PairFragment.Type.NovosSkus)
                                .addItem(mData?.skusDeslistados?.name, mData?.skusDeslistados?.isValid() ?: true, PairFragment.Type.SkusDeslistados)
                                .addItem(mData?.visibilidade?.name, mData?.visibilidade?.isValid() ?: true, PairFragment.Type.Visibilidade)
                                .create()
                        )
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            getDetail(context, model)
                        })
                    }
                })
        )
    }
}