package com.unilever.julia.ui.inovacao.detailV2.fragment.novosSkus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.RecyclerViewExpandableHeight
import com.unilever.julia.data.model.inovacaoDetalhe.Produto
import com.unilever.julia.ui.inovacao.detailV2.model.InnovationFragmentData
import com.unilever.julia.ui.inovacao.detailV2.adapter.ProductsRecyclerAdapter

class NovosSkusFragment : Fragment() {

    private lateinit var mData: InnovationFragmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mData = (context as InnovationFragmentData)
    }

    private lateinit var tvTitle : TextView //by bind<TextView>(R.id.tvTitle)

    private lateinit var recyclerView : RecyclerViewExpandableHeight //by bind<RecyclerViewExpandableHeight>(R.id.recyclerView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.innovation_detail_novos_skus, container, false)

        tvTitle = view.findViewById(R.id.tvTitle)
        recyclerView = view.findViewById(R.id.recyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val novosSkus = mData.getNovosSkus()
        tvTitle.text = novosSkus.name ?: ""

        val adapter = ProductsRecyclerAdapter(this, mData.getDrawableBrand(), true)
        adapter.addItems(novosSkus.produtos)
        adapter.setListener(object : ProductsRecyclerAdapter.Listener {
            override fun onClickItem(position: Int, products: MutableList<Produto>) {
                mData.onClickProductRedirectGallery(position, products)
            }
        })

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //layoutManager.isAutoMeasureEnabled = true
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(false)
        //recyclerView.setItemViewCacheSize(20)
    }
}
