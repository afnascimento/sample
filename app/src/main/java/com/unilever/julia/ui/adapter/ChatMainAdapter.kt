package com.unilever.julia.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.*
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.components.boletim.BoletimCardChat
import com.unilever.julia.components.boletim.BoletimCardProgress
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.innovations.JuliaChatInnovationHorizontal
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.components.model.InnovationCardHorizontalModel
import com.unilever.julia.components.model.InnovationCardVerticalModel
import com.unilever.julia.data.model.*
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.data.model.ipv.IpvCardChat
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.ui.component.*
import com.unilever.julia.utils.Utils

class ChatMainAdapter(private var mListener: Listener,
                      private var mListenerShare: JuliaStatusChat.Listener)
    : RecyclerView.Adapter<ChatMainAdapter.AdapterViewHolder>() {

    private var mDataSet : MutableList<IComponentsModel> = arrayListOf()

    fun addItem(item: IComponentsModel) {
        if (mDataSet.isEmpty()) {
            mDataSet.add(item)
        } else {
            mDataSet.add(0, item)
        }
        val position = mDataSet.indexOf(item)
        notifyItemInserted(position)
    }

    interface Listener {

        fun onButtonV2ClickListener(model: ButtonClickListenerModel)

        fun onJuliaButtonClickListener(text: String, url: String, intencao: String)

        fun onIpvDetailClick(model: IpvCardChat)

        fun showBottomInputText()

        fun onButtonDisambiguationClickListener(intentButton: ButtonClickListenerModel, closeButton: ButtonClickListenerModel)

        fun onCloseFlowInComponent(closeButton: ButtonClickListenerModel, showCloseMessage : Boolean)

        fun onChitChatClickListener(text: String, url: String, intencao: String)

        fun onInovacaoClickListener(item: InnovationCardVerticalModel.Item)

        fun onInovacaoClickListener(item: InnovationCardHorizontalModel.Item)

        fun onStatusPedidoClickListener(item: StatusPedidoModel.Item)

        fun onAgendaClickListener(item: AgendaModel.Item)

        fun onAgendaDetalheEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener)

        fun onAgendaCancelarEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener)

        fun onAgendaConcluirEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener)

        fun onAgendaExcluirEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener)

        fun onAdicionarAgendaClickListener(item: AdicionarAgendaModel.Item)

        fun onPedidosClienteClickListener(item: PedidosClienteModel)

        fun onReuniaoOutlookClickListener(item: ReuniaoOutlookModel)

        fun onEdicaoAgendaOutlook(agenda: AgendaOutlookSucesso)

        fun onDislikeFeedback()

        fun onFeedbackOptionClick(text: String, intencao: String)

        fun onStatusCarteiraClickListener(intencao: String, territory: String)

        fun onBoletimFiltros(card: BoletimCardChat,
                             item: BoletimChatModel,
                             attendanceFilter: AttendanceFilter?,
                             filters: BoletimFiltros?)
    }

    private fun beforeCalledListener() {
        if (itemCount >= 2) {
            val position = 0
            val item = getItem(position)
            if (item is ButtonDisambiguationModel) {
                item.events.showButtons = false
                notifyItemChanged(position)
                mListener.showBottomInputText()
            }
        }
    }

    private val mListenerBefore = object : Listener {

        override fun onIpvDetailClick(model: IpvCardChat) {
            beforeCalledListener()
            mListener.onIpvDetailClick(model)
        }

        override fun showBottomInputText() {
        }

        override fun onCloseFlowInComponent(closeButton: ButtonClickListenerModel, showCloseMessage : Boolean) {
            beforeCalledListener()
            mListener.onCloseFlowInComponent(closeButton, showCloseMessage)
        }

        override fun onButtonV2ClickListener(model: ButtonClickListenerModel) {
            beforeCalledListener()
            mListener.onButtonV2ClickListener(model)
        }

        override fun onJuliaButtonClickListener(text: String, url: String, intencao: String) {
            beforeCalledListener()
            mListener.onJuliaButtonClickListener(text, url, intencao)
        }

        override fun onButtonDisambiguationClickListener(intentButton: ButtonClickListenerModel,
                                                         closeButton: ButtonClickListenerModel
        ) {
            mListener.onButtonDisambiguationClickListener(intentButton, closeButton)
        }

        override fun onChitChatClickListener(text: String, url: String, intencao: String) {
            beforeCalledListener()
            mListener.onChitChatClickListener(text, url, intencao)
        }

        override fun onInovacaoClickListener(item: InnovationCardVerticalModel.Item) {
            beforeCalledListener()
            mListener.onInovacaoClickListener(item)
        }

        override fun onInovacaoClickListener(item: InnovationCardHorizontalModel.Item) {
            beforeCalledListener()
            mListener.onInovacaoClickListener(item)
        }

        override fun onStatusPedidoClickListener(item: StatusPedidoModel.Item) {
            beforeCalledListener()
            mListener.onStatusPedidoClickListener(item)
        }

        override fun onAgendaClickListener(item: AgendaModel.Item) {
            beforeCalledListener()
            mListener.onAgendaClickListener(item)
        }

        override fun onAgendaDetalheEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener) {
            beforeCalledListener()
            mListener.onAgendaDetalheEvento(item, removeListener)
        }

        override fun onAgendaCancelarEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener) {
            beforeCalledListener()
            mListener.onAgendaCancelarEvento(item, removeListener)
        }

        override fun onAgendaConcluirEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener) {
            beforeCalledListener()
            mListener.onAgendaConcluirEvento(item, removeListener)
        }

        override fun onAgendaExcluirEvento(item: AgendaModel.Item, removeListener: RemoveItemAgendaListener) {
            beforeCalledListener()
            mListener.onAgendaExcluirEvento(item, removeListener)
        }

        override fun onAdicionarAgendaClickListener(item: AdicionarAgendaModel.Item) {
            beforeCalledListener()
            mListener.onAdicionarAgendaClickListener(item)
        }

        override fun onPedidosClienteClickListener(item: PedidosClienteModel) {
            beforeCalledListener()
            mListener.onPedidosClienteClickListener(item)
        }

        override fun onReuniaoOutlookClickListener(item: ReuniaoOutlookModel) {
            beforeCalledListener()
            mListener.onReuniaoOutlookClickListener(item)
        }

        override fun onEdicaoAgendaOutlook(agenda: AgendaOutlookSucesso) {
            beforeCalledListener()
            mListener.onEdicaoAgendaOutlook(agenda)
        }

        override fun onDislikeFeedback() {
            beforeCalledListener()
            mListener.onDislikeFeedback()
        }

        override fun onFeedbackOptionClick(text: String, intencao: String) {
            beforeCalledListener()
            mListener.onFeedbackOptionClick(text, intencao)
        }

        override fun onStatusCarteiraClickListener(intencao: String, territory: String) {
            beforeCalledListener()
            mListener.onStatusCarteiraClickListener(intencao, territory)
        }

        override fun onBoletimFiltros(card: BoletimCardChat,
                                      item: BoletimChatModel,
                                      attendanceFilter: AttendanceFilter?,
                                      filters: BoletimFiltros?) {
            beforeCalledListener()
            mListener.onBoletimFiltros(card, item, attendanceFilter, filters)
        }
    }

    private var mJuliaLoadModel = JuliaLoadModel()

    private class JuliaLoadModel : IComponentsModel {

        override fun getType(): ComponentsType {
            return ComponentsType.JULIA_LOAD
        }
    }

    fun showJuliaLoad() {
        if (!mDataSet.contains(mJuliaLoadModel)) {
            if (mDataSet.isEmpty()) {
                mDataSet.add(mJuliaLoadModel)
            } else {
                mDataSet.add(0, mJuliaLoadModel)
            }
            //notifyItemChanged(0)
            notifyItemInserted(0)
        }
    }

    fun hideJuliaLoad() {
        if (mDataSet.contains(mJuliaLoadModel)) {
            val position = mDataSet.indexOf(mJuliaLoadModel)
            mDataSet.remove(mJuliaLoadModel)
            notifyItemRemoved(position)
            //notifyItemRangeChanged(position, mDataSet.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val type = ComponentsType.values()[viewType]
        when (type) {
            ComponentsType.BUTTON_HORIZONTAL -> return JuliaButtonHorizontalHolder(
                JuliaButtonsHorizontal(
                    parent.context
                )
            )

            ComponentsType.CHAT_MESSAGE_INICIO -> return ChatMessageInicioHolder(JuliaHelloMessage(parent.context))
            ComponentsType.BUTTON -> return JuliaButtonHolder(JuliaButtonComponent(parent.context))
            ComponentsType.CHIT_CHAT -> return ChitChatHolder(JuliaButtonComponent(parent.context))
            ComponentsType.JULIA_ANSWER -> return JuliaAnswerHolder(
                JuliaAnswerComponent(
                    parent.context
                )
            )
            ComponentsType.USER_CHAT -> return UserChatHolder(JuliaUserChat(parent.context))
            ComponentsType.INNOVATION_CARD_VERTICAL -> return InnovationCardVerticalHolder(
                JuliaInnovationChatVertical(
                    parent.context
                )
            )
            ComponentsType.INNOVATION_CARD_HORIZONTAL -> return InovacaoHorizontalHolder(
                JuliaChatInnovationHorizontal(
                    parent.context
                )
            )
            ComponentsType.STATUS_PEDIDO -> return StatusPedidoHolder(JuliaStatusChat(parent.context), mListenerShare)
            ComponentsType.AGENDA -> return AgendaHolder(JuliaAgendaSwipeChat(parent.context))
            ComponentsType.ADICIONAR_AGENDA -> return AdicionarAgendaHolder(JuliaButtonComponent(parent.context))
            ComponentsType.PEDIDO_CLIENTE_MESSAGE -> return PedidoClienteHolder(
                JuliaAnswerComponent(
                    parent.context
                )
            )
            ComponentsType.REUNIAO_OUTLOOK -> return ReuniaoOutlookHolder(
                JuliaAnswerComponent(
                    parent.context
                )
            )
            ComponentsType.CARD_AGENDA_EDICAO -> return CardAgendaEdicaokHolder(JuliaCardAgendaEdicaoChat(parent.context))
            ComponentsType.FEEDBACK_OPTIONS -> return FeedbackOptionsHolder(FeedbackSendOptions(parent.context))
            ComponentsType.BUTTON_DISAMBIGUATION -> return JuliaButtonsDisambiguationHolder(JuliaButtonsDisambiguation(parent.context))
            ComponentsType.STATUS_PEDIDO_CARTEIRA -> return StatusPedidoCarteiraHolder(JuliaStatusPedidoCarteira(parent.context))
            ComponentsType.IPV -> return IpvHolder(LayoutInflater.from(parent.context).inflate(R.layout.ipv_chat_layout, parent, false))
            ComponentsType.BOLETIM_CARD_CHAT -> return BoletimHolder(BoletimCardChat(parent.context))
            else -> return JuliaLoadHolder(JuliaSpeechLoad(parent.context))
        }
    }

    override fun onBindViewHolder(holderAdapter: AdapterViewHolder, position: Int) {
        if (holderAdapter is AgendaHolder) {
            val model : AgendaModel = mDataSet[position] as AgendaModel

            val component : JuliaAgendaSwipeChat = holderAdapter.itemView as JuliaAgendaSwipeChat
            component.setText(model.text)
            component.addItems(model.items, object : IJuliaAgendaSwipeCardListener {
                override fun onDetalheEvento(item: AgendaModel.Item) {
                    mListenerBefore.onAgendaDetalheEvento(item, object : RemoveItemAgendaListener {
                        override fun onFinish() {
                            removeItemAgenda(model, item)
                        }
                    })
                }

                override fun onCancelarEvento(item: AgendaModel.Item) {
                    mListenerBefore.onAgendaCancelarEvento(item, object : RemoveItemAgendaListener {
                        override fun onFinish() {
                            removeItemAgenda(model, item)
                        }
                    })
                }

                override fun onConcluirEvento(item: AgendaModel.Item) {
                    mListenerBefore.onAgendaConcluirEvento(item, object : RemoveItemAgendaListener {
                        override fun onFinish() {
                            removeItemAgenda(model, item)
                        }
                    })
                }

                override fun onExcluirEvento(item: AgendaModel.Item) {
                    mListenerBefore.onAgendaExcluirEvento(item, object : RemoveItemAgendaListener {
                        override fun onFinish() {
                            removeItemAgenda(model, item)
                        }
                    })
                }
            })
        } else {
            holderAdapter.bind(position, getItem(position), mListenerBefore)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        val item : IComponentsModel = getItem(position)
        val type : ComponentsType = item.getType()
        return type.ordinal
    }

    private fun getItem(position: Int): IComponentsModel {
        return mDataSet[position]
    }

    interface RemoveItemAgendaListener {
        fun onFinish()
    }

    fun removeItemAgenda(model : AgendaModel, item : AgendaModel.Item) {
        val indexModel = mDataSet.indexOf(model)
        model.items.remove(item)
        mDataSet[indexModel] = model
        notifyDataSetChanged()
    }

    abstract class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener)
    }

    class IpvHolder(itemView: View) : AdapterViewHolder(itemView) {

        var juliaAgendaAnswer : JuliaAnswerComponent = itemView.findViewById(R.id.juliaAgendaAnswer)

        var contentIpvCard : View = itemView.findViewById(R.id.contentIpvCard)

        var tvTitle : TextView = itemView.findViewById(R.id.tvTitle)

        var tvScore : TextView = itemView.findViewById(R.id.tvScore)

        var tvTarget : TextView = itemView.findViewById(R.id.tvTarget)

        var contentItems : LinearLayout = itemView.findViewById(R.id.contentItems)

        var button : ButtonWrapContent = itemView.findViewById(R.id.btnIpvDetail)

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val model : IpvCardChat = chatMainModel as IpvCardChat

            juliaAgendaAnswer.setText(model.text)

            if (!model.visibleCard) {
                contentIpvCard.visibility = View.GONE
            } else {
                contentIpvCard.visibility = View.VISIBLE

                tvTitle.text = itemView.context.getString(R.string.ipv_title_card, model.nameIpv)

                tvScore.text = Utils.getTextPercent(model.ipv?.score?.percentage ?: 0.0)
                tvScore.setTextColor(Utils.getColorByHexa(model.ipv?.score?.colorCode ?: ""))

                tvTarget.text = Utils.getTextPercent(model.ipv?.target?.percentage ?: 0.0)

                contentItems.removeAllViewsInLayout()

                val items : List<IpvItem> = model.ipv?.getItemsSorted() ?: emptyList()
                for (item in items) {
                    contentItems.addView(getViewItem(item, contentItems))
                }
            }

            button.setOnClickListener {
                listener.onIpvDetailClick(model)
            }
        }

        private fun getViewItem(item : IpvItem, parent: ViewGroup): View {
            val view = LayoutInflater.from(itemView.context).inflate(R.layout.ipv_item_layout, parent, false)
            Utils.setColorDrawable(item.colorCode ?: "", view.findViewById<View>(R.id.viewColor).background)
            view.findViewById<TextView>(R.id.tvPercentage).text = Utils.getTextPercent(item.percentage ?: 0.0)
            view.findViewById<TextView>(R.id.tvDescription).text = item.type ?: ""
            return view
        }
    }

    class JuliaLoadHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
        }
    }

    class JuliaAnswerHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : JuliaAnswerModel = chatMainModel as JuliaAnswerModel

            val component : JuliaAnswerComponent = itemView as JuliaAnswerComponent
            component.setText(it.text)
            if (it.isCopyTextOnClick) {
                component.enableCopyText()
            }
        }
    }

    class UserChatHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : UserChatModel = chatMainModel as UserChatModel

            val component : JuliaUserChat = itemView as JuliaUserChat
            component.setText(it.text)
        }
    }

    class ChatMessageInicioHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ChatMessageInicioModel = chatMainModel as ChatMessageInicioModel

            val component : JuliaHelloMessage = itemView as JuliaHelloMessage
            component.setUsername(it.username)
            component.setBanner(it.banner, it.visible)
        }
    }

    class JuliaButtonHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ButtonModel = chatMainModel as ButtonModel

            val component : JuliaButtonComponent = itemView as JuliaButtonComponent
            component.setText(it.text)
            component.addButtonItems(it.items, object : ButtonComponent.Listener {
                override fun onButtonClickListener(text: String, url: String, intencao: String) {
                    listener.onJuliaButtonClickListener(text, url, intencao)
                }
            })
        }
    }

    class JuliaButtonHorizontalHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ButtonHorizontalModel = chatMainModel as ButtonHorizontalModel

            val component : JuliaButtonsHorizontal = itemView as JuliaButtonsHorizontal
            component.setText(it.text)
            component.addButtonItems(it.items, object : ButtonHorizontal.Listener {
                override fun onButtonClickListener(model: ButtonClickListenerModel) {
                    listener.onButtonV2ClickListener(model)
                }
            })
        }
    }

    class JuliaButtonsDisambiguationHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ButtonDisambiguationModel = chatMainModel as ButtonDisambiguationModel

            val component : JuliaButtonsDisambiguation = itemView as JuliaButtonsDisambiguation
            component.setText(it.text)
            component.setTextUser(it.events.textCloseButton)

            if (!it.events.showButtons) {
                component.hideButtons()
                return
            }

            component.setCloseButton(it.closeButton)
            component.addButtonItems(it.items)
            component.setListener(object : JuliaButtonsDisambiguation.Listener {

                override fun onCloseButtonClick(closeButton: ButtonClickListenerModel) {
                    it.showCloseButton(closeButton.text)
                    component.setTextUser(closeButton.text)
                    component.hideButtons()
                    listener.onCloseFlowInComponent(closeButton, false)
                }

                override fun onIntentButtonClick(intentButton: ButtonClickListenerModel,
                                                 closeButton: ButtonClickListenerModel
                ) {
                    it.showCloseButton(intentButton.text)
                    component.setTextUser(intentButton.text)
                    component.hideButtons()
                    listener.onButtonDisambiguationClickListener(intentButton, closeButton)
                }
            })
            component.showButtons()
        }
    }

    class ChitChatHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ChitChatModel = chatMainModel as ChitChatModel

            val component : JuliaButtonComponent = itemView as JuliaButtonComponent
            component.setText(it.text)
            component.addChitChatItems(it.items, object : ButtonComponent.Listener {
                override fun onButtonClickListener(text: String, url: String, intencao: String) {
                    listener.onChitChatClickListener(text, url, intencao)
                }
            })
        }
    }

    class InnovationCardVerticalHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val model : InnovationCardVerticalModel = chatMainModel as InnovationCardVerticalModel

            val component : JuliaInnovationChatVertical = itemView as JuliaInnovationChatVertical
            component.setText(model.text)
            component.setItems(model.items, object : ButtonInnovationVertical.Listener<InnovationCardVerticalModel.Item> {
                override fun onButtonDetailClick(item: InnovationCardVerticalModel.Item) {
                    listener.onInovacaoClickListener(item)
                }
            })
        }
    }

    class InovacaoHorizontalHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val model : InnovationCardHorizontalModel = chatMainModel as InnovationCardHorizontalModel

            val component : JuliaChatInnovationHorizontal = itemView as JuliaChatInnovationHorizontal
            component.setText(model.text)
            component.addButtonItems(model.items, object : ButtonInnovationHorizontal.Listener<InnovationCardHorizontalModel.Item> {
                override fun onButtonClickListener(item: InnovationCardHorizontalModel.Item) {
                    listener.onInovacaoClickListener(item)
                }
            })
        }
    }

    class StatusPedidoHolder(itemView: View, val listenerShare: JuliaStatusChat.Listener) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : StatusPedidoModel = chatMainModel as StatusPedidoModel

            val component : JuliaStatusChat = itemView as JuliaStatusChat
            component.setText(it.text)
            component.setListener(listenerShare)
            component.addItems(it.items, object : JuliaStatusCard.Listener {
                override fun onButtonDetailClick(item: StatusPedidoModel.Item) {
                    listener.onStatusPedidoClickListener(item)
                }
            })
            component.setFeedbackButtonsLikeListener(object : FeedbackButtonsLike.Listener {
                override fun onDislike() {
                    listener.onDislikeFeedback()
                }
            })
            component.refreshButtons()
            listenerShare.setCardComponent(component)
        }
    }

    class StatusPedidoCarteiraHolder(itemView: View) : AdapterViewHolder(itemView) {

        private fun itemsProgress(percent: StatusPedidoCarteiraModel.ValuePercent?): MutableList<ProgressCard.Item> {
            val list : MutableList<ProgressCard.Item> = arrayListOf()

            val fact = percent?.fact
            if (fact != null) {
                list.add(ProgressCard.Item(fact.value ?: 0.0, fact.color ?: ""))
            }

            val pend = percent?.pend
            if (pend != null) {
                list.add(ProgressCard.Item(pend.value ?: 0.0, pend.color ?: ""))
            }

            val anul = percent?.anul
            if (anul != null) {
                list.add(ProgressCard.Item(anul.value ?: 0.0, anul.color ?: ""))
            }

            return list
        }

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val model : StatusPedidoCarteiraModel = chatMainModel as StatusPedidoCarteiraModel

            val component : JuliaStatusPedidoCarteira = itemView as JuliaStatusPedidoCarteira
            component.setText(model.text)

            if (model.context == null) {
                component.hideCard()
            } else {
                component.setListener(object : JuliaStatusPedidoCarteira.Listener {
                    override fun onNextClick(intencao: String, territory: String) {
                        listener.onStatusCarteiraClickListener(intencao, territory)
                    }
                })

                component.setItemNext(model.context?.intencao ?: "", model.context?.territory ?: "")

                component.setTerritorio(model.context?.getTextTerritorio() ?: "")

                component.setQtdeClientes(model.context?.qtyCustomer ?: 0)

                component.setQtdePedidos(model.context?.qtyOrders ?: 0)

                component.setValorTotalPedido(model.context?.valueOrders?.total ?: 0.0)

                component.setQtdeCaixas(model.context?.boxOrders?.total?.toInt() ?: 0)

                component.addProgressValueOrders(itemsProgress(model.context?.valueOrders))

                component.addProgressBoxOrders(itemsProgress(model.context?.boxOrders))

                component.showCard()
            }
        }
    }

    class FeedbackOptionsHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : FeedbackOptionsModel = chatMainModel as FeedbackOptionsModel

            val component : FeedbackSendOptions = itemView as FeedbackSendOptions
            component.setText(it.text)
            component.addOptions(it.items)
            component.setListener(object : FeedbackSendOptions.Listener {
                override fun onFeedbackOptionClick(text: String, intencao: String) {
                    listener.onFeedbackOptionClick(text, intencao)
                }
            })
        }
    }

    class PedidoClienteHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : PedidosClienteModel = chatMainModel as PedidosClienteModel

            val component : JuliaAnswerComponent = itemView as JuliaAnswerComponent
            component.setText(it.text)
            component.setVisibleContentClick(true)
            component.setListener(object : JuliaAnswerComponent.Listener {
                override fun onClick() {
                    listener.onPedidosClienteClickListener(it)
                }
            })
        }
    }

    class BoletimHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : BoletimChatModel = chatMainModel as BoletimChatModel

            val component : BoletimCardChat = itemView as BoletimCardChat
            component.setText(it.text)
            component.initBind(it.card)
            component.setListener(object : BoletimCardProgress.Listener {
                override fun onLeftClick(attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?) {
                    listener.onBoletimFiltros(component, it, attendanceFilter, filters)
                }
            })
        }
    }

    class ReuniaoOutlookHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : ReuniaoOutlookModel = chatMainModel as ReuniaoOutlookModel

            val component : JuliaAnswerComponent = itemView as JuliaAnswerComponent
            component.setText(it.text)
            component.setVisibleContentClick(true)
            component.setTextClick(itemView.context.getString(R.string.adicionar_evento_agenda))
            component.setListener(object : JuliaAnswerComponent.Listener {
                override fun onClick() {
                    listener.onReuniaoOutlookClickListener(it)
                }
            })
        }
    }

    class CardAgendaEdicaokHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : AgendaOutlookSucesso = chatMainModel as AgendaOutlookSucesso

            val component : JuliaCardAgendaEdicaoChat = itemView as JuliaCardAgendaEdicaoChat
            component.setAgendaOutlook(it)
            component.setListener(object : JuliaCardAgendaEdicaoChat.Listener {
                override fun onEdit(agenda: AgendaOutlookSucesso) {
                    listener.onEdicaoAgendaOutlook(agenda)
                }
            })
        }
    }

    class AgendaHolder(itemView: View) : AdapterViewHolder(itemView) {
        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {

        }
    }

    class AdicionarAgendaHolder(itemView: View) : AdapterViewHolder(itemView) {

        override fun bind(position: Int, chatMainModel: IComponentsModel, listener: Listener) {
            val it : AdicionarAgendaModel = chatMainModel as AdicionarAgendaModel

            val component : JuliaButtonComponent = itemView as JuliaButtonComponent
            component.setText(it.text)
            component.addAdicionarAgendaItems(it.items, object : ButtonComponent.AdicionarAgendaListener {
                override fun onButtonClickListener(model: AdicionarAgendaModel.Item) {
                    listener.onAdicionarAgendaClickListener(model)
                }
            })
        }
    }
}