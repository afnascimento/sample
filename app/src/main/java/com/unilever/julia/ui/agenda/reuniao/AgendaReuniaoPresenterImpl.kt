package com.unilever.julia.ui.agenda.reuniao

import com.unilever.julia.R
import com.unilever.julia.data.model.*
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.ui.base.IOnSubscribe
import com.unilever.julia.utils.EmailValidator
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils
import java.util.*
import javax.inject.Inject

class AgendaReuniaoPresenterImpl<V : AgendaReuniaoView, I : AgendaReuniaoInteractor>
@Inject
constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), AgendaReuniaoPresenter<V, I> {

    override fun enviarExclusaoAgenda(idReuniao: String, sessionCode: String) {
        enviarExclusaoAgenda(idReuniao, sessionCode, object : IOnSubscribe<String> {
            override fun doOnSubscribe() {
                getView().showProgressDialog()
            }

            override fun onNext(value: String) {
                getView().agendaExcluidaSucesso(value)
            }

            override fun onComplete() {
                getView().hideProgressDialog()
            }

            override fun onError(e: Throwable) {
                getView().hideProgressDialog()
                getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog -> dialog.dismiss() })
            }
        })
    }

    override fun enviarGravacaoAgenda(idReuniao: String?, tituloReuniao : String,
                             dtInicio : String, dtFim : String,
                             hrInicio : String, hrFim : String, local: String,
                             participantes: MutableList<EmailParticipante>?, sessionCode: String) {

        var requiredValid = true

        if (StringUtils.isNotBlank(tituloReuniao)) {
            getView().setErrorTituloReuniao(null)
        } else {
            getView().setErrorTituloReuniao(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNotBlank(local)) {
            getView().setErrorLocal(null)
        } else {
            getView().setErrorLocal(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNotBlank(dtInicio)) {
            getView().setErrorDataInicio(null)
        } else {
            getView().setErrorDataInicio(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNotBlank(dtFim)) {
            getView().setErrorDataFim(null)
        } else {
            getView().setErrorDataFim(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNotBlank(hrInicio)) {
            getView().setErrorHorarioInicio(null)
        } else {
            getView().setErrorHorarioInicio(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNotBlank(hrFim)) {
            getView().setErrorHorarioFim(null)
        } else {
            getView().setErrorHorarioFim(getView().getContext().getString(R.string.campo_obrigatorio))
            requiredValid = false
        }

        if (StringUtils.isNoneBlank(dtInicio, dtFim, hrInicio, hrFim)) {
            val initDate = Utils.toDate("$dtInicio $hrInicio", "dd/MM/yyyy HH:mm")
            val endDate = Utils.toDate("$dtFim $hrFim", "dd/MM/yyyy HH:mm")

            if (initDate != null && endDate != null) {
                val initWithoutTime = Utils.removeTime(initDate)
                val endWithoutTime = Utils.removeTime(endDate)

                if (initWithoutTime.after(endWithoutTime)) {
                    getView().setErrorDataInicio("data maior que data fim")
                    getView().setErrorDataFim("data menor que data inicio")

                    getView().setErrorHorarioInicio(null)
                    getView().setErrorHorarioFim(null)

                    requiredValid = false
                } else if (initDate == endDate) {
                    getView().setErrorDataInicio(null)
                    getView().setErrorDataFim(null)

                    getView().setErrorHorarioInicio("hora igual a hora fim")
                    getView().setErrorHorarioFim("hora igual a hora inicio")

                    requiredValid = false
                } else if (initDate.after(endDate)) {
                    getView().setErrorDataInicio(null)
                    getView().setErrorDataFim(null)

                    getView().setErrorHorarioInicio("hora maior que a hora fim")
                    getView().setErrorHorarioFim("hora menor que a hora inicio")

                    requiredValid = false
                } else {
                    getView().setErrorDataInicio(null)
                    getView().setErrorDataFim(null)
                    getView().setErrorHorarioInicio(null)
                    getView().setErrorHorarioFim(null)
                }
            }
        }

        if (!participantes.isNullOrEmpty()) {
            for (part in participantes) {
                if (StringUtils.isBlank(part.email)) {
                    requiredValid = false
                    getView().setErrorParticipante(part.index, getView().getContext().getString(R.string.campo_obrigatorio))
                } else if (!EmailValidator.validate(part.email)) {
                    requiredValid = false
                    getView().setErrorParticipante(part.index, getView().getContext().getString(R.string.email_invalido))
                } else {
                    getView().setErrorParticipante(part.index, null)
                }
            }
        }

        if (!requiredValid) {
            return
        }

        val send = AgendaOutlookGravacao()
        send.code = "22_AGENDA_OUTLOOK_ADICIONAR_CONFIRMAR"

        val context = AgendaOutlookContext()
        context.id = idReuniao
        context.assunto = tituloReuniao
        context.dataInicio = dtInicio
        context.horaInicio = hrInicio
        context.dataFim = dtFim
        context.horaFim = hrFim
        context.local = local
        context.timeZone = TimeZone.getDefault().id

        val lista = arrayListOf<AgendaOutlookParticipante>()
        if (!participantes.isNullOrEmpty()) {
            for (part in participantes) {
                lista.add(AgendaOutlookParticipante(part.email))
            }
        }
        context.participantes = lista

        send.context = context

        getView().addDisposable(
            getInteractor().enviarGravacaoAgenda(sessionCode, send)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<AgendaOutlookSucesso>() {
                    override fun onNext(agenda: AgendaOutlookSucesso) {
                        getView().agendaGravadaSucesso(agenda)
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                        getView().onErrorHandlerDialog(e,
                            MaterialDialog.OnClickListener { dialog ->
                                dialog.dismiss()
                            })
                    }
                })
        )
    }

    override fun onClickButtonCancelar(owner: Boolean) {
        if (owner) {
            getView().onExcluirDialog()
        } else {
            getView().onCancelar()
        }
    }
}