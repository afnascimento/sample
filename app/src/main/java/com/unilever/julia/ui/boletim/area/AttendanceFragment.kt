package com.unilever.julia.ui.boletim.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.unilever.julia.R
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.boletim.AttendanceRecyclerFilter

class AttendanceFragment : Fragment() {

    private var mType : AttendanceModel.Type? = null

    companion object {

        private const val TYPE = "TYPE"

        fun newInstance(type: AttendanceModel.Type): AttendanceFragment {
            val fragment = AttendanceFragment()

            val bundleArgs = Bundle()
            bundleArgs.putInt(TYPE, type.ordinal)
            fragment.arguments = bundleArgs

            return fragment
        }
    }

    lateinit var mPresenter: AttendancePresenter<AttendanceView, AttendanceInteractor>
    lateinit var attendanceRecyclerFilter : AttendanceRecyclerFilter
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(TYPE)) {
            val ordinal = arguments!!.getInt(TYPE)
            mType = AttendanceModel.Type.values()[ordinal]
        }
        mPresenter = (activity as AttendanceActivity).mPresenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.boletim_area_atend_fragment, container, false)
        attendanceRecyclerFilter = view.findViewById(R.id.attendanceRecyclerFilter)
        button = view.findViewById(R.id.btnAplicar)
        button.setOnClickListener {
            mPresenter.applyAttendance()
        }
        return view
    }

    private fun isInitialised(): Boolean {
     return ::mPresenter.isInitialized
             && ::attendanceRecyclerFilter.isInitialized
             && ::button.isInitialized
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isInitialised()) {
            val type : AttendanceModel.Type = mType ?: return
            val data = mPresenter.getData()
            var hint = ""
            val items = when (type) {
                AttendanceModel.Type.territory -> {
                    hint = getString(R.string.buscar_territorio)
                    data?.territory ?: emptyList()
                }
                AttendanceModel.Type.district -> {
                    hint = getString(R.string.buscar_distrito)
                    data?.district ?: emptyList()
                }
                AttendanceModel.Type.subsidiary -> {
                    hint = getString(R.string.buscar_filial)
                    data?.subsidiary ?: emptyList()
                }
                AttendanceModel.Type.hitRegional -> {
                    hint = getString(R.string.buscar_regional)
                    data?.hitRegional ?: emptyList()
                }
            }

            // se for filial e selected != null && selected.type == territory
            // selected contains in list selected <- null
            // resolve o problema de deixar selecionado o meu territorio e uma filial com o mesmo nome
            /*var withSelected = true
            val selected = mPresenter.getSelected()
            if (type == AttendanceModel.Type.subsidiary
                && selected.type == AttendanceModel.Type.territory) {
                if (items.contains(selected)) {
                    withSelected = false
                }
            }*/

            attendanceRecyclerFilter.addListener(mPresenter)
            attendanceRecyclerFilter.setHint(hint)
            attendanceRecyclerFilter.addItems(mPresenter.getSelected(), items)
        }
    }

    fun updateSelected(selected : AttendanceModel) {
        if (isInitialised()) {
            attendanceRecyclerFilter.updateSelected(selected)
        }
    }
}