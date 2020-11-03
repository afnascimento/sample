package com.unilever.julia.ui.ipv.unitaryGroup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unilever.julia.R
import com.unilever.julia.components.IpvRecylerFilter
import com.unilever.julia.data.model.goods.IpvGoodsUnitary

class UnitaryFragment : Fragment(), IpvRecylerFilter.Adapter.Listener<IpvGoodsUnitary> {

    companion object {

        private const val DATA = "DATA"

        fun newInstance(data: ArrayList<IpvGoodsUnitary>): UnitaryFragment {
            val fragment = UnitaryFragment()

            val bundle = Bundle()
            bundle.putParcelableArrayList(DATA, data)
            fragment.arguments = bundle

            return fragment
        }
    }

    private var mData : ArrayList<IpvGoodsUnitary> = arrayListOf()

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle : Bundle = arguments ?: Bundle()
        if (bundle.containsKey(DATA)) {
            mData = bundle.getParcelableArrayList(DATA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.group_unitary_fragment, container, false)
    }

    private lateinit var ipvHeaderClient : IpvRecylerFilter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ipvHeaderClient = view.findViewById(R.id.ipvHeaderClient)
        ipvHeaderClient.setHint(getString(R.string.buscar_produto))
        ipvHeaderClient.addItems(mData, this)
    }

    override fun onClick(item: IpvGoodsUnitary) {
        
    }
}