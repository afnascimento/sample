package com.unilever.julia.ui.inovacao.detailV2.fragment.execucao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unilever.julia.R
import com.unilever.julia.components.innovations.InnovationDescription
import com.unilever.julia.ui.inovacao.detailV2.component.InnovationGridProducts
import com.unilever.julia.data.model.inovacaoDetalhe.Produto
import com.unilever.julia.ui.inovacao.detailV2.model.InnovationFragmentData

class ExecucaoFragment : Fragment() {

    private lateinit var mData: InnovationFragmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mData = (context as InnovationFragmentData)
    }

    private lateinit var innovationDescription : InnovationDescription //by bind<InnovationDescription>(R.id.innovationDescription)

    private lateinit var gridProducts : InnovationGridProducts<Produto> //by bind<InnovationGridProducts>(R.id.gridProducts)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.innovation_description_and_grid, container, false)

        innovationDescription = view.findViewById(R.id.innovationDescription)
        gridProducts = view.findViewById(R.id.gridProducts)
        gridProducts.setListener(object : InnovationGridProducts.Listener<Produto> {
            override fun onClickItem(position: Int, products: List<Produto>) {
                mData.onClickProductRedirectGallery(position, products)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateRegrasDeOuro()
        updateGridPlanograma()
    }

    private fun updateRegrasDeOuro() {
        val regrasDeOuro = mData.getExecucao().regrasDeOuro
        if (regrasDeOuro == null || !regrasDeOuro.isValid()) {
            innovationDescription.visibility = View.GONE
        } else {
            innovationDescription.setTitle(regrasDeOuro.title ?: "")
            innovationDescription.setDescription(regrasDeOuro.description ?: "")
        }
    }

    private fun updateGridPlanograma() {
        val planograma = mData.getExecucao().planograma
        if (planograma == null || planograma.produtos.isNullOrEmpty()) {
            gridProducts.visibility = View.GONE
        } else {
            gridProducts.visibility = View.VISIBLE
            gridProducts.setTitle(planograma.title ?: "")
            gridProducts.addItems(planograma.produtos!!)
        }
    }
}
