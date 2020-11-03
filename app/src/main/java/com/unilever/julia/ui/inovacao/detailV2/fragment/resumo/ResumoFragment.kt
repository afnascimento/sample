package com.unilever.julia.ui.inovacao.detailV2.fragment.resumo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.unilever.julia.R
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.components.innovations.InnovationBeneficio
import com.unilever.julia.components.innovations.InnovationDescription
import com.unilever.julia.components.innovations.InnovationTradestory
import com.unilever.julia.components.innovations.InnovationsMidias
import com.unilever.julia.ui.inovacao.detailV2.model.InnovationFragmentData

class ResumoFragment : Fragment(),
    ResumoFragmentView {

    private lateinit var mData: InnovationFragmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mData = (context as InnovationFragmentData)
    }

    private lateinit var iconData : JuliaTextViewIcon
    private lateinit var iconChannel : JuliaTextViewIcon
    private lateinit var tvData : TextView
    private lateinit var tvChannel : TextView

    private lateinit var componentTradestory : InnovationTradestory
    private lateinit var contentMidias : InnovationsMidias
    private lateinit var contentBeneficio : InnovationBeneficio
    private lateinit var contentPublicoAlvo : InnovationDescription

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.innovation_detail_resumo, container, false)

        iconData = view.findViewById(R.id.tvIconLeft)
        iconChannel = view.findViewById(R.id.tvRightIcon)

        tvData = view.findViewById(R.id.tvLeftDescription)
        tvChannel = view.findViewById(R.id.tvRightValue)

        componentTradestory = view.findViewById(R.id.contentTradestory)
        componentTradestory.setListener(mData)

        contentMidias = view.findViewById(R.id.contentMidias)
        contentMidias.setListener(mData)

        contentBeneficio = view.findViewById(R.id.contentBeneficio)
        contentPublicoAlvo = view.findViewById(R.id.contentPublicoAlvo)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateGeral()
        updateTradeStory()
        updateMidias()
        updateBeneficio()
        updatePublico()
    }

    private fun updateGeral() {
        val resumo = mData.getResumo()
        iconData.setIcon(resumo.iconData ?: "", resumo.iconColor ?: "")
        iconChannel.setIcon(resumo.iconCanal ?: "", resumo.iconColor ?: "")
        tvData.text = resumo.getDataGeral()
        tvChannel.text = resumo.canal ?: ""
    }

    override fun updateTradeStory() {
        val tradeStory = mData.getResumo().tradeStory
        if (tradeStory == null) {
            componentTradestory.visibility = View.GONE
        } else {
            componentTradestory.setValues(
                tradeStory.title ?: "",
                tradeStory.avaliacao ?: 0,
                tradeStory.count ?: 0,
                tradeStory.url ?: "",
                tradeStory.novo ?: false)
        }
    }

    private fun updateMidias() {
        val midias = mData.getResumo().midias
        if (midias == null || midias.itens.isNullOrEmpty()) {
            contentMidias.visibility = View.GONE
        } else {
            contentMidias.visibility = View.VISIBLE
            val itens = arrayListOf<InnovationsMidias.Item>()
            for (it in midias.itens!!) {
                itens.add(InnovationsMidias.Item(it.title ?: "", it.description ?: "", it.url ?: ""))
            }
            contentMidias.setTitle(midias.title ?: "")
            contentMidias.addMidias(itens)
        }
    }

    private fun updateBeneficio() {
        val beneficio = mData.getResumo().beneficioEsperado
        if (beneficio == null) {
            contentBeneficio.visibility = View.GONE
        } else {
            contentBeneficio.setTitle(beneficio.title ?: "")
            contentBeneficio.setDescription(
                beneficio.descriptionCustomer ?: "",
                beneficio.descriptionShopKeeper ?: "")
        }
    }

    private fun updatePublico() {
        val publicoAlvo = mData.getResumo().publicoAlvo
        if (publicoAlvo == null || !publicoAlvo.isValid()) {
            contentPublicoAlvo.visibility = View.GONE
        } else {
            contentPublicoAlvo.setTitle(publicoAlvo.title ?: "")
            contentPublicoAlvo.setDescription(publicoAlvo.description ?: "")
        }
    }
}
